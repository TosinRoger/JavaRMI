package br.com.tosin.serverrmi.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tosin.models.ManagementBook;

public class Util {
	
	/**
	 * Retorna o timestamp do emprestimo
	 * @param book
	 * @return time > 0 esta atrasado, se time < 0 nao esta atrasado
	 */
	public static long bookDelayTime(ManagementBook book) {
		long delay = 0;
		Calendar current = Calendar.getInstance();
		
		delay = current.getTimeInMillis() - book.getLoan().getTimeInMillis();
		
		return delay;
	}
	
	/**
	 * Retorna string com a data da devolucao em dd/MM/yyyy
	 * @param calendar
	 * @return
	 */
	public static String parseDate(Calendar calendar) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		long dev = calendar.getTimeInMillis();
		dev += Constants.TIME_LOAN;
		calendar.setTimeInMillis(dev);
		String date = dateFormat.format(calendar.getTime());
		return date;
	}
	
	/**
	 * Verifica se o livro esta dentro de prazo de renovacao
	 * @param book
	 * @return
	 */
	public static boolean canRenovation(ManagementBook book) {
		Calendar calendar = Calendar.getInstance();
		return (calendar.getTimeInMillis() < book.getTimeDevolution()) ? true : false;
	}

	/**
	 * Carrega os dados de uma base local
	 * @return
	 * @throws IOException
	 */
	public static List<ManagementBook> loadBooks() {

		List<ManagementBook> books = null;
		books = staticBook();
		 
		return books;
	}

	/**
	 * Criar lista estatica de livros
	 * @return
	 */
	private static List<ManagementBook> staticBook() {
		List<ManagementBook> books = new ArrayList<>();
		books.add(new ManagementBook(1, "titulo 1", "autor 1", "Historinha 1", true, 0));
		books.add(new ManagementBook(2, "titulo 2", "autor 2", "Historinha 2", true, 0));
		books.add(new ManagementBook(3, "titulo 3", "autor 3", "Historinha 3", true, 0));
		books.add(new ManagementBook(4, "titulo 4", "autor 4", "Historinha 4", true, 0));
		books.add(new ManagementBook(5, "titulo 5", "autor 5", "Historinha 5", true, 0));
		
		return books;
	}
	
	/**
	 * Caso o usuario queira criar mais livros esse metodo deve ser chamado
	 * @param id
	 * @return
	 */
	public static ManagementBook createNewBook(int id){
		ManagementBook book = new ManagementBook(id, "titulo " + id, "autor " + id, "Historinha " + id, true, 0);
		return book;
	}
}
