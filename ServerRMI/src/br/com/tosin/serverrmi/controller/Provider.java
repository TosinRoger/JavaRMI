package br.com.tosin.serverrmi.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.javarmi.interfaces.ServerInterface;
import br.com.tosin.models.Book;

public class Provider extends UnicastRemoteObject implements ServerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Provider() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void test(String test, ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server received: " + test);
		clientInterface.teste("Eu, servidor, recebi seu teste");
	}

	@Override
	public void getBooks(ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: getBook is request");
		List<Book> books = Controller.getBooks();
		clientInterface.listBooks(books);
	}

	@Override
	public void loan(ClientInterface clientInterface, Book book) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: loan is request");
		if(Controller.idOverdeu(clientInterface)) {
			clientInterface.message("Voce nao pode empressatar livros");
		}
		else if(Controller.bookIsAvailable(book)) {
			clientInterface.message("Livro esta emprestado, voce podera entrar a lista de espera"); 
		}
		else {
			
		}
	}

	@Override
	public void renovation(ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: renovation is request");
		
	}

	@Override
	public void devolution(ClientInterface clientInterface, Book book) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: renovation is request");
		
	}

	@Override
	public void reservation(ClientInterface clientInterface, Book book) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: renovation is request");
		
	}

}
