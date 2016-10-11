package br.com.tosin.models;

import java.io.Serializable;

public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private String author;
	private String about;
	public boolean available;
	public long timeDevolution;

	public Book(long id, String title, String author, String about, boolean available, long timeDevolution) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.about = about;
		this.available = available;
		this.timeDevolution = timeDevolution;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getAbout() {
		return about;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public long getTimeDevolution() {
		return timeDevolution;
	}

}
