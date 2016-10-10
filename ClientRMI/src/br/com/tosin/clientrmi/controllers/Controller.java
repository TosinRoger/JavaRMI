package br.com.tosin.clientrmi.controllers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import br.com.tosin.clientrmi.ui.MainCliente;
import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.javarmi.interfaces.ServerInterface;
import br.com.tosin.models.Book;

public class Controller {


	private static int PORT = 5555;
	private static ClientInterface user;
	private static ServerInterface servidor;
	
	private static MainCliente mainClient;
	
	public Controller(MainCliente mainClient) {
		Controller.mainClient = mainClient;
	}
	
	public void execute() {
		registerConnection();

		try {
			servidor.test("Essa eh minha mensagem de teste", user);
			
//			servidor.getBooks(user);

//			servidor.getBooks(user);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void registerConnection() {
		try {
			Registry referenciaServicoNomes = LocateRegistry.getRegistry("localhost", PORT);
			servidor = (ServerInterface) referenciaServicoNomes.lookup("NomeAleatorio");
			
			user = new Provider(this);
			
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// ==============================================================================
	// 
	// ==============================================================================
	
	
	public void teste() {
		try {
			servidor.test("Testando clique", user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getBooks(List<Book> books) {
		mainClient.populateBooks(books);
	}
	
	public void requestListBook() {
		try {
			servidor.getBooks(user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loan(Book book) {
		try {
			servidor.loan(user, book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showMessage(String msg) {
		mainClient.showNotification(msg);
	}
	
	public void myBooks(List<Book> books) {
		mainClient.populateMyBooks(books);
	}
}
