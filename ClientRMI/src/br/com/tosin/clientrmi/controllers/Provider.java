package br.com.tosin.clientrmi.controllers;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.models.Book;

public class Provider extends UnicastRemoteObject implements ClientInterface{

	private Controller controller;
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	public Provider(Controller controller) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.controller = controller;
	}

	@Override
	public void teste(String test) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("o Cliente recebeu: " + test);
	}

	@Override
	public void notifyBookAvaliable(int bookId) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listBooks(List<Book> books) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Client: get books is request");
		System.out.println("Size books: " + (books == null ? "nulo" : books.size()));
		controller.getBooks(books);
//		Controller.loan(books.get(1));
	}

	@Override
	public void message(String msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Servidor: " + msg);
	}

}
