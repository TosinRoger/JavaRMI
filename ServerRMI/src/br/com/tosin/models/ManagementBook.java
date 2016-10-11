package br.com.tosin.models;

import java.util.Calendar;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.serverrmi.utils.Constants;

public class ManagementBook extends Book {

	public ManagementBook(long id, String title, String author, String abount, boolean available, long timeDevolution) {
		super(id, title, author, abount, available, timeDevolution);
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
	private boolean reserved;
	private ClientInterface client;

	public Calendar getLoan() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(loan);
		return calendar;
	}
	
	
	public Book getBook() {
		Book b = new Book(getId(), getTitle(), getAuthor(), getAbout(), isAvailable(), getTimeDevolution());
		return b;
	}
	
	/**
	 * Ajuste as configuracoes para emprestado
	 */
	public void setLoan() {
		this.available = false;
		this.loan = Calendar.getInstance().getTimeInMillis();
		this.timeDevolution = this.loan + Constants.TIME_LOAN;
		this.reserved = false;
	}
	
	/**
	 * Ajusta as configuracoes como disponivel
	 */
	public void resetLoan() {
		this.available = true;
		this.loan = 0;
		this.timeDevolution = 0;
		this.client = null;
	}
	
	public void setReserve() {
		this.reserved = true;
	}
	
	public void removeReserve() {
		this.reserved = false;
	}
	
	public boolean isReserved() {
		return this.reserved;
	}

	public ClientInterface getClient() {
		return client;
	}

	public void setClient(ClientInterface client) {
		this.client = client;
	}

	
}
