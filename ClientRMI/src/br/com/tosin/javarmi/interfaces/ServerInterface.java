package br.com.tosin.javarmi.interfaces;

import java.rmi.*;

import br.com.tosin.javarmi.clientrmi.models.User;


public interface ServerInterface extends Remote {
	
	void registerUser(User client, ClientInterface clientInterface) throws RemoteException;
	void test(String test, ClientInterface clientInterface) throws RemoteException;
}
