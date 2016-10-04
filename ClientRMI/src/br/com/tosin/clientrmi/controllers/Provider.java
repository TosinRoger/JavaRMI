package br.com.tosin.clientrmi.controllers;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.com.tosin.javarmi.interfaces.ClientInterface;

public class Provider extends UnicastRemoteObject implements ClientInterface{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	protected Provider() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void teste(String test) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("o Cliente recebeu: " + test);
	}

	@Override
	public void notifyBookAvaliable(int bookId) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
