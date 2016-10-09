package br.com.tosin.serverrmi.controller;

import java.awt.EventQueue;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.serverrmi.models.Book;
import br.com.tosin.serverrmi.ui.GUI;
import br.com.tosin.serverrmi.ui.MainFrame;
import br.com.tosin.serverrmi.utils.Util;

public class Controller {


	private static int PORT = 5555;
	
	private static List<Book> books = new ArrayList<Book>();
	private static List<ClientInterface> users;
	private static Provider providerServer;
	
	private MainFrame mainFrame;
	
	
	public Controller(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	public void execute() {
		
		books = Util.loadBooks();
		
		mainFrame.populateBooks(books);
		registerConnection();
	}
	
	private void registerConnection() {
		try {
			Registry referenciaServicoNomes = LocateRegistry.createRegistry(PORT);
			providerServer = new Provider();
			referenciaServicoNomes.rebind("NomeAleatorio", providerServer);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	// ===============================================================================
	// GETTERS
	// ===============================================================================
	
	public static List<Book> getBooks() {
		return books;
	}

	public static Provider getProviderServer() {
		return providerServer;
	}

	public static List<ClientInterface> getUsers() {
		if (users == null)
			users = new ArrayList<ClientInterface>();
		return users;
	}
	
	
}
