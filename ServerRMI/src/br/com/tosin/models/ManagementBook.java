package br.com.tosin.models;

import java.util.Calendar;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.serverrmi.utils.Constants;

/**
 * Classe que extend de {@link Book}, 
 * esta classe contem atributos que o servidor usa para gerenciar ou livros.
 * @author tosin
 *
 */
public class ManagementBook extends Book {


	private static final long serialVersionUID = 1L;
	
	private long loan;
	private boolean reserved;
	private ClientInterface client;
	private int numRenovation;
	
	public ManagementBook(long id, String title, String author, String abount, boolean available, long timeDevolution) {
		super(id, title, author, abount, available, timeDevolution);
		// TODO Auto-generated constructor stub
		this.available = true;
		this.loan = 0;
		this.client = null;
	}
	

	/**
	 * Retorna o Calendar com a data prevista de devolucao
	 * @return
	 */
	public Calendar getLoan() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(loan);
		return calendar;
	}
	
	
	/**
	 * Retorna o {@link Book} que sera enviado para o cliente, 
	 * sem os dados de controle usados pelo servidor.
	 * @return
	 */
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
		this.numRenovation++;
	}
	
	/**
	 * Ajusta as configuracoes como disponivel
	 */
	public void resetLoan() {
		this.available = true;
		this.loan = 0;
		this.timeDevolution = 0;
		this.client = null;
		this.numRenovation = 0;
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


	public int getNumRenovation() {
		return numRenovation;
	}
	
	

	
}
