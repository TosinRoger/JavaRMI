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

	public Book(long id, String title, String author, String about) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.about = about;
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

}
