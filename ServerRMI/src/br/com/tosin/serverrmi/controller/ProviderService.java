package br.com.tosin.serverrmi.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.javarmi.interfaces.ServerInterface;
import br.com.tosin.models.Book;
import br.com.tosin.models.ManagementBook;

public class ProviderService extends UnicastRemoteObject implements ServerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProviderService() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Metodo para testar a conexao
	 */
	@Override
	public void test(String test, ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server received: " + test);
		clientInterface.teste("Eu, servidor, recebi seu teste");
	}

	/**
	 * Metodo chamado pelo cliente para retornar todos os livros do servidor. 
	 * Este metodo tambem envia todos os livros emprestados por esse cliente.
	 */
	@Override
	public void getBooks(ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: getBook is request");
		List<Book> books = Controller.getBooks();
		
		List<Book> myBook = new ArrayList<>();
		
		for(ManagementBook item : Controller.getBooksManagement())
			if (item.getClient() != null && item.getClient().equals(clientInterface))
				myBook.add(item.getBook());
		
		clientInterface.listBooks(books, myBook);
	}

	/**
	 * Metodo chamado pelo cliente para emprestar um livro
	 * @param Book é passado o livro que ele escolheu para o emprestimo
	 */
	@Override
	public void loan(ClientInterface clientInterface, Book book) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: loan is request");
		if (book == null ) {
			clientInterface.message("O livro nao existe");
		}
		else if(Controller.idOverdeu(clientInterface)) {
			clientInterface.message("Voce esta inadimplemente ate " + Controller.buildTextOverdue(clientInterface));
		}
		else if(!Controller.bookIsAvailable(book)) {
			clientInterface.message("Livro esta emprestado, voce podera entrar a lista de espera"); 
		}
		else {
			String done = Controller.loanBook(clientInterface, book);
			if(done.contains("Emprestado"))
				clientInterface.message("Livro emprestado");
			else if(done.equals("atingiu o limite"))
				clientInterface.message("Voce ja atingui o limite de emprestimo");
			else if(done.equals("inadimplente"))
				clientInterface.message("Voce esta inadimplemente ate " + Controller.buildTextOverdue(clientInterface));
			else 
				clientInterface.message(done);
		}
	}

	/**
	 * Metodo chamado pelo cliente para fazer a renovacao do livro
	 * @param Book é passado o livro que ele escolheu para  faze a renovacao
	 */
	@Override
	public void renovation(ClientInterface clientInterface, Book book) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: renovation is request");
		int response = Controller.renovation(clientInterface, book);
		if(1 == response) {
			clientInterface.message("Livro renovado");
		}
		else if (response == 2){
			clientInterface.message("O livro esta reservado");
		}
		else if (response == 3){
			clientInterface.message("Voce atingiu o limite de reservas");
		}
		else {
			clientInterface.message("Voce nao pode renovar o livro");
		}
	}

	/**
	 * Metodo chamado pelo cliente para fazer a devolucao
	 * @param Book é passado o livro que ele escolheu para faze a devolucao
	 */
	@Override
	public void devolution(ClientInterface clientInterface, Book book) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: devolution is request");
		if(Controller.devolution(book)) {
			clientInterface.message("Livro devolvido");
		}
		else {
			clientInterface.message("Não foi possivel devolver o livro");
		}
	}

	
	/**
	 * Metodo chamado pelo cliente para fazer a reserva de um livro
	 * @param Book é passado o livro que ele escolheu para ser reservado
	 */
	@Override
	public void reservation(ClientInterface clientInterface, Book book) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server: reservation is request");
		String msg = Controller.reservation(clientInterface, book);
		clientInterface.message(msg);
		
	}

}
