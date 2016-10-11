package br.com.tosin.serverrmi.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tosin.models.ManagementBook;

public class Util {

	private static final long LOAN_TIME = 300000;
	
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
	 * Retorna string com a data em dd/MM/yyyy
	 * @param calendar
	 * @return
	 */
	public static String parseDate(Calendar calendar) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		long dev = calendar.getTimeInMillis();
		dev += LOAN_TIME;
		calendar.setTimeInMillis(dev);
		String date = dateFormat.format(calendar.getTime());
		return date;
	}
	
	public static boolean canRenovation(ManagementBook book) {
		if (book.getLoan().getTimeInMillis() + LOAN_TIME <= Calendar.getInstance().getTimeInMillis()) 
			return true;
		else
			return false;
	}

	/**
	 * Carrega os dados de uma base local
	 * @return
	 * @throws IOException
	 */
	public static List<ManagementBook> loadBooks() {
//		String path = "";
//		try {
//			path = openFile();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		


//		Type typeFactory = new TypeToken<ArrayList<ManagementBook>>(){}.getType();
//		final Gson gson = new GsonBuilder().registerTypeAdapterFactory((TypeAdapterFactory) typeFactory).create();
		
		
		List<ManagementBook> books = null;
		books = staticBook();
//		books = gson.fromJson(path, typeFactory);
		 
		return books;
	}
	
//	private static String openFile() throws IOException {
//		String path = "src/database/base_book.json";
//		String result = "";
//		
//		BufferedReader br = new BufferedReader(new FileReader(path));
//		try {
//		    StringBuilder sb = new StringBuilder();
//		    String line = br.readLine();
//
//		    while (line != null) {
//		        sb.append(line);
//		        sb.append(System.lineSeparator());
//		        line = br.readLine();
//		    }
//		    result = sb.toString();
//		} finally {
//		    br.close();
//		}
//		result = result.replaceAll("\n\r", "");
//		result = result.replaceAll("\n", "");
//		return result;
//	}

	private static List<ManagementBook> staticBook() {
		List<ManagementBook> books = new ArrayList<>();
		books.add(new ManagementBook(1, "titulo 1", "autor 1", "Historinha 1", true));
		books.add(new ManagementBook(2, "titulo 2", "autor 2", "Historinha 2", true));
		books.add(new ManagementBook(3, "titulo 3", "autor 3", "Historinha 3", true));
		books.add(new ManagementBook(4, "titulo 4", "autor 4", "Historinha 4", true));
		books.add(new ManagementBook(5, "titulo 5", "autor 5", "Historinha 5", true));
		
		return books;
	}
	
	public static ManagementBook createNewBook(int id){
		ManagementBook book = new ManagementBook(id, "titulo " + id, "autor " + id, "Historinha " + id, true);
		return book;
	}
}
