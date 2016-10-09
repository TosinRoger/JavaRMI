package br.com.tosin.serverrmi.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.javarmi.interfaces.ServerInterface;
import br.com.tosin.serverrmi.models.User;

public class Provider extends UnicastRemoteObject implements ServerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Provider() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerUser(User client, ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		Controller.getUsers().add(clientInterface);
		System.out.println("num clientes: " + Controller.getUsers().size());
	}

	@Override
	public void test(String test, ClientInterface clientInterface) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Server received: " + test);
		clientInterface.teste("Eu, servidor, recebi seu teste");
	}

}
