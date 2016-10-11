package br.com.tosin.clientrmi.controllers;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Random;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.models.Book;

public class Provider extends UnicastRemoteObject implements ClientInterface{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	private Controller controller;
	public long id = new Random().nextLong();
	
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
	public void notifyBookAvaliable(Book book) throws RemoteException {
		// TODO Auto-generated method stub
		controller.notifyBook(book);
	}

	@Override
	public void message(String msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Servidor: " + msg);
		controller.showMessage(msg);
	}

	@Override
	public void listBooks(List<Book> books, List<Book> myBooks) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Client: get books is request");
		System.out.println("Size books: " + (books == null ? "nulo" : books.size()));
		controller.getBooks(books);
		
		controller.myBooks(myBooks);
	}

}
