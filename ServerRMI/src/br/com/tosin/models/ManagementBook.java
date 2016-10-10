package br.com.tosin.models;

import java.util.Calendar;

import br.com.tosin.javarmi.interfaces.ClientInterface;

public class ManagementBook extends Book {

	public ManagementBook(long id, String title, String author, String abount) {
		super(id, title, author, abount);
		// TODO Auto-generated constructor stub
		available = true;
		loan = 0;
		if(id == 5) {
			available = false;
			loan = Calendar.getInstance().getTimeInMillis();
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private long loan;
	private ClientInterface client;

	public Calendar getLoan() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(loan);
		return calendar;
	}
	
	
	public Book getBook() {
		Book b = new Book(getId(), getTitle(), getAuthor(), getAbout());
		return b;
	}
	
	public void setLoan() {
		this.available = false;
		this.loan = Calendar.getInstance().getTimeInMillis();
	}

	public ClientInterface getClient() {
		return client;
	}

	public void setClient(ClientInterface client) {
		this.client = client;
	}

	
}
