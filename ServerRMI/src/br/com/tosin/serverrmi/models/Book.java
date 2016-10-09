package br.com.tosin.serverrmi.models;

import java.io.Serializable;
import java.util.*;

public class Book implements Serializable {

	private long id;
	private String title;
	private String author;
	private boolean available;
	private String abount;
	private long loan;

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
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(loan);
		return calendar;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result = "";
		result += "Autor: " + author;
		result += " Titulo: " + title;
		result += " Disponivel: " + (available ? "Sim" : "Não");
		return result;
	}

}
