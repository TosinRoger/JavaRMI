package br.com.tosin.javarmi.interfaces;

import java.rmi.*;

public interface ClientInterface extends Remote {
	
	void teste(String test) throws RemoteException;
	void notifyBookAvaliable(int bookId) throws RemoteException;

}
