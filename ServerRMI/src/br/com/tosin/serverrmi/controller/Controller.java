package br.com.tosin.serverrmi.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
	private static List<ClientInterface> listOverdue;
	private static ProviderService providerServer;

	private static MainFrame mainFrame;

	public Controller(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
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
	 * @param client
	 * @return
	 */
	public static boolean idOverdeu(ClientInterface client) {
		if (getListOverdue() == null)
			return false;
		 for(ClientInterface item : getListOverdue())
			 if (item == client)
				 return true;
		 return false;
	}
	
	/**
	 * Verifica se o livro esta disponivel
	 * @param book
	 * @return
	 */
	public static boolean bookIsAvailable(Book book) {
		for (ManagementBook item : getBooksManagement()) 
			if(book.getId() == item.getId()) 
				return item.isAvailable();
		return true;
	}
	
	public static boolean loanBook(Book book) {
		for (ManagementBook item : getBooksManagement()) 
			if(book.getId() == item.getId()) {
				item.setLoan();
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

	/**
	 * Lista de usuarios inadimplentes
	 * 
	 * @return
	 */
	public static List<ClientInterface> getListOverdue() {
		if (listOverdue == null)
			listOverdue = new ArrayList<>();
		return listOverdue;
	}
	
	public static void loanBook (ClientInterface client, Book book) {
		getListLoan().add(client);
		
		for (ManagementBook managementBook : getBooksManagement()) {
			if(managementBook.getId() == book.getId()) {
				managementBook.setLoan();
				managementBook.setClient(client);
				mainFrame.populateBooks(getBooksManagement());
			}
		}
	}

}
