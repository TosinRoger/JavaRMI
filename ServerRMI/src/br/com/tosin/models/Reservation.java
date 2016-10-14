package br.com.tosin.models;

import br.com.tosin.javarmi.interfaces.ClientInterface;

/**
 * Classe Item da lista de reservas. Ela associa o cliente ao livro
 * @author tosin
 *
 */
public class Reservation {

	private ClientInterface client;
	private Book book;

	public Reservation(ClientInterface client, Book book) {
		super();
		this.client = client;
		this.book = book;
	}

	public ClientInterface getClient() {
		return client;
	}

	public Book getBook() {
		return book;
	}

}
