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
	
	/**
	 * Metodo que inicia o controller
	 */
	public void execute() {
		registerConnection();

		try {
			servidor.test("Essa eh minha mensagem de teste", user);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Registar a conexao do JAVA RMI, sempre eh chamado no execute
	 */
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
	
	
	/**
	 * Metodo de teste de conexao
	 */
	public void teste() {
		try {
			servidor.test("Testando clique", user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que recebe a lista de livros da conexao e envia para ser mostrada na tela
	 * @param books lista de livros recebida do servidor
	 */
	public void getBooks(List<Book> books) {
		mainClient.populateBooks(books);
	}
	
	/**
	 * Requisita do servidor a lista de livros
	 */
	public void requestListBook() {
		try {
			servidor.getBooks(user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Requisita ao servidor um emprestimo de livro
	 * @param book livro que quer ser emprestado
	 */
	public void loan(Book book) {
		try {
			servidor.loan(user, book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Envia para o servidor o livro que quer devolver
	 * @param book livro a ser devolvido
	 */
	public void devolution(Book book ) {
		try {
			servidor.devolution(user, book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Envia ao servidor o livro que pretende reservar
	 * @param book livro a ser reservado
	 */
	public void reservation(Book book) {
		try {
			servidor.reservation(user, book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Notifica o servidor que quer renovar um livro
	 * @param book livro a ser renovado
	 */
	public void renovation(Book book) {
		try {
			servidor.renovation(user, book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Mostra na tela o livro que esta disponivel
	 * @param book
	 */
	public void notifyBook(Book book) {
		mainClient.notifyBookAvailable(book);
	}
	
	/**
	 * Mostra na tela o mensagem recebida do servidor
	 * @param msg
	 */
	public void showMessage(String msg) {
		mainClient.showNotification(msg);
	}
	
	/**
	 * Lista de livro que esse usuario emprestou
	 * @param books lista de livros emprestado
	 */
	public void myBooks(List<Book> books) {
		mainClient.populateMyBooks(books);
	}
}
