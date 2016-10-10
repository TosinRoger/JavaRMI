package br.com.tosin.models;

import java.util.Calendar;

public class ManagementBook extends Book {

	public ManagementBook(long id, String title, String author, String abount) {
		super(id, title, author, abount);
		// TODO Auto-generated constructor stub
		setLoan();
	}
	
	private static final long serialVersionUID = 1L;
	private boolean available;
	private long loan;
	
	

	public boolean isAvailable() {
		return available;
	}

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
		this.available = true;
		this.loan = Calendar.getInstance().getTimeInMillis();
	}
}
