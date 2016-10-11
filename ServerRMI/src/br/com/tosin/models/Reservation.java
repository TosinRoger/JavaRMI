package br.com.tosin.models;

import br.com.tosin.javarmi.interfaces.ClientInterface;

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
