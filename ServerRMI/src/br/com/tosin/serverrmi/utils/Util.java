package br.com.tosin.serverrmi.utils;

import java.io.*;
import java.lang.reflect.Type;
import java.text.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import br.com.tosin.serverrmi.models.Book;

public class Util {

	private static final long LOAN_TIME = 0;
	
	/**
	 * Retorna o timestamp do emprestimo
	 * @param book
	 * @return time > 0 esta atrasado, se time < 0 nao esta atrasado
	 */
	public static long bookDelayTime(Book book) {
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
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		String date = dateFormat.format(calendar.getTime());
		return date;
	}
	
	public static List<Book> loadBooks() {
		Gson gson = new Gson();
		String path = "";
		try {
			path = openFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Type listType = new TypeToken<ArrayList<Book>>(){}.getType();

		List<Book> books = new Gson().fromJson(path, listType);
		 
		return books;
	}
	
	private static String openFile() throws IOException {
		String path = "src/database/base_book.json";
		String result = "";
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    result = sb.toString();
		} finally {
		    br.close();
		}
		result = result.replaceAll("\n\r", "");
		result = result.replaceAll("\n", "");
		return result;
	}
}
