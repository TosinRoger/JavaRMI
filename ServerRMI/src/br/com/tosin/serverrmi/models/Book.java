package br.com.tosin.serverrmi.models;

import java.util.*;

public class Book {

	private long id;
	private String title;
	private String author;
	private boolean available;
	private String abount;
	private Calendar loan;

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public boolean isAvailable() {
		return available;
	}

	
	public String getAbount() {
		return abount;
	}

	public Calendar getLoan() {
		return loan;
	}

}
