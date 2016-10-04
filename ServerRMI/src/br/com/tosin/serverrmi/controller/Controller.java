package br.com.tosin.serverrmi.controller;

import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.serverrmi.models.Book;
import br.com.tosin.serverrmi.utils.Util;

public class Controller {


	private static int PORT = 5555;
	
	private static List<Book> books = new ArrayList<Book>();
	private static List<ClientInterface> users;
	private static Provider providerServer;
	
	public void execute() {
		books = Util.loadBooks();
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
