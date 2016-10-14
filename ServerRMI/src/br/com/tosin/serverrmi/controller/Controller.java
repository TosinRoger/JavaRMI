package br.com.tosin.serverrmi.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.models.Book;
import br.com.tosin.models.ManagementBook;
import br.com.tosin.models.Reservation;
import br.com.tosin.serverrmi.ui.MainFrame;
import br.com.tosin.serverrmi.utils.Constants;
import br.com.tosin.serverrmi.utils.Util;

public class Controller {

	private static int PORT = 5555;

	private static List<ManagementBook> booksManagement = new ArrayList<>();
	private static List<Reservation> reservations = new ArrayList<>();
	private static ProviderService providerServer;
	private static OverdueList overdueList;

	private static MainFrame mainFrame;

	public Controller(MainFrame mainFrame) {
		super();
		Controller.mainFrame = mainFrame;
		Controller.overdueList = new OverdueList();
	}

	/**
	 * Metodo que inicia o controller
	 */
	public void execute() {

		booksManagement = Util.loadBooks();

		mainFrame.populateBooks(booksManagement);
		registerConnection();
	}

	/**
	 * Registar a conexao do JAVA RMI, sempre eh chamado no execute
	 */
	private void registerConnection() {
		try {
			Registry referenciaServicoNomes = LocateRegistry.createRegistry(PORT);
			providerServer = new ProviderService();
			referenciaServicoNomes.rebind("NomeAleatorio", providerServer);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ===============================================================================
	// CONSULT
	// ===============================================================================

	/**
	 * Verifica se o cliente esta da lista de inadimplentes
	 * 
	 * @param client
	 * @return TRUE Cliente inadimplente, FALSE sem prendencias
	 */
	public static boolean idOverdeu(ClientInterface client) {
		return overdueList.consultClient(client);
	}

	/**
	 * Verifica se o livro esta disponivel, ou seja, nao esta empresado e nao tem reservas.
	 * 
	 * @param book
	 * @return TRUE livro esta disponivel, FALSE o livro nao esta disponivel
	 */
	public static boolean bookIsAvailable(Book book) {
		for (ManagementBook item : getBooksManagement())
			if (book.getId() == item.getId())
				return item.isAvailable();
		return true;
	}
	
	/**
	 * Renova o livro caso seja possivel, 
	 * senao for adiciona o cliente na lista de usuario
	 * @param client
	 * @param book
	 * @return 0 Nao eh possivel renovar, 1 eh possivel renovar, 2 = ha uma reserva para o livro
	 * 3 o limite de renovacoes foi atingida.
	 * default 0
	 */
	public static int renovation(ClientInterface client, Book book) {
		
		//verifica se tem reserva
		if (hasReservation(book)){ 
			return 2;
		}

		for (ManagementBook item : getBooksManagement()) {
			if (book.getId() == item.getId() && item.getClient() != null && item.getClient().equals(client)) {
				if(item.getNumRenovation() > Constants.NUM_RENOVATION) {
					return 3;
				}
				else if (Util.canRenovation(item) ) {
					item.setLoan();
					mainFrame.populateBooks(getBooksManagement());
					return 1;
				}
				else {
					overdueList.addClient(client);
					mainFrame.populateBooks(getBooksManagement());
					return 0;
				}
			}
		}
		return 0;
	}
	
	/**
	 * Adiciona livro na lista do servidor, esse metodo eh chamada pelo botao da tela para adicionar item
	 */
	public static void addBook() {
		int id = getBooksManagement().size();
		getBooksManagement().add(Util.createNewBook(++id));
	}
	

	/**
	 * Metodo para emprestar um livro
	 * @param client
	 * @param book
	 * @return uma string com a mensagem do status do emprestivo
	 */
	public synchronized static String loanBook(ClientInterface client, Book book) {
		
		//verifica se tem reserva
		if (hasReservation(book))
			return "O livro possui uma reserva";
		int countBookClient = 0;
		
		for (ManagementBook item : getBooksManagement()) {
			if (item.getClient() != null && item.getClient().equals(client)) {
				countBookClient++;
				// aproveita para ver se tem livros vencidos
				
				if(!Util.canRenovation(item)) {
					overdueList.addClient(client);
				}
			}
		}
		
		if (countBookClient > Constants.NUM_RENOVATION)
			return "Voce ja atingiu o limite";
		
		if (overdueList.consultClient(client)) {
			
			return "inadimplente";
		}
		
		for (ManagementBook managementBook : getBooksManagement()) {
			if (managementBook.getId() == book.getId()) {
				managementBook.setLoan();
				managementBook.setClient(client);
				mainFrame.populateBooks(getBooksManagement());
				return "Emprestado";
			}
		}
		return "Nao foi possivel emprestar o livro";
	}
	

	/**
	 * Metodo de devolucao
	 * @param book
	 * @return TRUE livro foi devolvido, FALSE nao foi possivel devolver o livro
	 */
	public static boolean devolution (Book book) {
		for (ManagementBook managementBook : getBooksManagement()) {
			if (managementBook.getId() == book.getId()) {
				managementBook.resetLoan();
				mainFrame.populateBooks(getBooksManagement());
				notifyClient(book);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Encontro o livro que foi devolvido e verfica se tenha alguma reserva, se tiver notifica o cliente.
	 * @param book Livro que foi devolvido
	 */
	private static void notifyClient(Book book) {
		Iterator<Reservation> iterator = reservations.iterator();
		
		while(iterator.hasNext()) {
			Reservation item = iterator.next();
			
			if (item.getBook().getId() == book.getId()) {
				try {
					item.getClient().notifyBookAvaliable(book);
					item.getClient().message("O livro " + item.getBook().getTitle() + " esta disponivel");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iterator.remove();
				break;
			}
			
		}
		
	}

	/**
	 * Metodo que faz a reserva de um livro. Verifica se o livro ja esta na lista de reserva, 
	 * se o livro ja esta empresado senao adicionar o usuario da lista de reserva.
	 * @param client
	 * @param book
	 * @return Mensagem com o status da reserva, se foi possivel reservar ou nao
	 */
	public static String reservation(ClientInterface client, Book book) {
		
		for(Reservation item : reservations) {
			// livro ja esta reservado
			if (item.getBook().getId() == book.getId())
				return "Livro já esta reservado";
		}
		
		for (ManagementBook item : getBooksManagement()) {
			if (item.getBook().getId() == book.getId()) {
				if (item.getClient() == null) {
					return "O livro esta disponivel para ser empresatado";
				}
				else {
					item.setReserve();
					item.removeReserve();
					reservations.add(new Reservation(client, book));
					return "Voce esta na lista de espera. Você será avisado quando o livro estiver diponível";
				}
			}
			
		}
		
		
		
		return "Não foi possível reservar o livro";
	}
	
	/**
	 * Atualiza o tempo de penalidade do cliente, 
	 * com isso a penalidade sempre eh renovada ate que os livros sejam devolvidos.
	 * @param client
	 * @return Data em que a penalidade eh valida no formato dd/mm/yyyy HH:mm
	 */
	public static String buildTextOverdue(ClientInterface client) {
		long time = overdueList.timeOverdue(client);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		
		
		return Util.parseDate(calendar);
	}
	
	/**
	 * Verifica se um livro tem uma alguma reserva
	 * @param book
	 * @return TRUE livro tem reserva, FALSE o livro nao tem reserva
	 */
	private static boolean hasReservation(Book book) {
		for(Reservation item : reservations) {
			// livro ja esta reservado
			if (item.getBook().getId() == book.getId())
				return true;
		}
		return false;
	}

	// ===============================================================================
	// GETTERS
	// ===============================================================================

	/**
	 * List de livros com as informacoes basicas
	 * 
	 * @return
	 */
	public static List<Book> getBooks() {
		List<Book> books = new ArrayList<>();
		for (ManagementBook item : booksManagement) {
			books.add(item.getBook());
		}
		return books;
	}

	/**
	 * Lista de livros com as informacoes de controler do servidor
	 * 
	 * @return
	 */
	public static List<ManagementBook> getBooksManagement() {
		if (booksManagement == null)
			booksManagement = new ArrayList<>();
		return booksManagement;
	}

	

}
