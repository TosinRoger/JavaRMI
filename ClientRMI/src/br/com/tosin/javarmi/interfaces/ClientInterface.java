package br.com.tosin.javarmi.interfaces;

import java.rmi.*;
import java.util.List;

import br.com.tosin.models.Book;

public interface ClientInterface extends Remote {
	void teste(String test) throws RemoteException;
	void message(String msg) throws RemoteException;
	void notifyBookAvaliable(int bookId) throws RemoteException;
	void listBooks(List<Book> books) throws RemoteException;
}
