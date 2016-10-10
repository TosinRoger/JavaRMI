package br.com.tosin.javarmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import br.com.tosin.models.Book;

public interface ServerInterface extends Remote {
	void test(String test, ClientInterface clientInterface) throws RemoteException;
	void getBooks(ClientInterface clientInterface) throws RemoteException;
	void loan(ClientInterface clientInterface, Book book) throws RemoteException;
	void renovation(ClientInterface clientInterface) throws RemoteException;
	void devolution(ClientInterface clientInterface, Book book) throws RemoteException;
	void reservation(ClientInterface clientInterface, Book book) throws RemoteException;
}
