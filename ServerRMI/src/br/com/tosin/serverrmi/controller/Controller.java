package br.com.tosin.serverrmi.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.models.Book;
import br.com.tosin.models.ManagementBook;
import br.com.tosin.serverrmi.ui.MainFrame;
import br.com.tosin.serverrmi.utils.Util;

public class Controller {

	private static int PORT = 5555;

	private static List<ManagementBook> booksManagement = new ArrayList<ManagementBook>();
	private static List<ClientInterface> listLoan;
	private static ProviderService providerServer;
	private static OverdueList overdueList;

	private static MainFrame mainFrame;

	public Controller(MainFrame mainFrame) {
		super();
		Controller.mainFrame = mainFrame;
		Controller.overdueList = new OverdueList();
	}

	public void execute() {

		booksManagement = Util.loadBooks();

		mainFrame.populateBooks(booksManagement);
		registerConnection();
	}

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
	 * @return
	 */
	public static boolean idOverdeu(ClientInterface client) {
		return overdueList.consultClient(client);
	}

	/**
	 * Verifica se o livro esta disponivel
	 * 
	 * @param book
	 * @return
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
	 * @return
	 */
	public static boolean renovation(ClientInterface client, Book book) {

		for (ManagementBook item : getBooksManagement()) {
			if (book.getId() == item.getId() && item.getClient() != null && item.getClient().equals(client)) {
				if (Util.canRenovation(item)) {
					item.setLoan();
					mainFrame.populateBooks(getBooksManagement());
					return true;
				}
				else {
					//TODO por tosin [11 de out de 2016] tirar o livro da lista de emprestado e colocar o cliente como inadimplente
					overdueList.addClient(client);
					item.resetLoan();
					mainFrame.populateBooks(getBooksManagement());
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Adiciona livro na lista do servidor
	 */
	public static void addBook() {
		int id = getBooksManagement().size();
		getBooksManagement().add(Util.createNewBook(++id));
	}
	

	/**
	 * usuario empresta livro
	 * @param client
	 * @param book
	 */
	public static String loanBook(ClientInterface client, Book book) {
		getListLoan().add(client);
		//TODO por tosin [11 de out de 2016] verificar se usuario pode emrpesar livro

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
		
		if (countBookClient >= 3)
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
	
	public static String buildTextOverdue(ClientInterface client) {
		long time = overdueList.timeOverdue(client);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		
		
		return Util.parseDate(calendar);
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

	public static ProviderService getProviderServer() {
		return providerServer;
	}

	/**
	 * Lista de usuarios que emprestaram livros
	 * 
	 * @return
	 */
	private static List<ClientInterface> getListLoan() {
		if (listLoan == null)
			listLoan = new ArrayList<>();
		return listLoan;
	}
	
	public static boolean devolution (Book book) {
		for (ManagementBook managementBook : getBooksManagement()) {
			if (managementBook.getId() == book.getId()) {
				managementBook.resetLoan();
				mainFrame.populateBooks(getBooksManagement());
				return true;
			}
		}
		return false;
	}

}
