package br.com.tosin.clientrmi.controllers;

import java.rmi.*;
import java.rmi.registry.*;

import br.com.tosin.javarmi.interfaces.*;

public class Controller {


	private static int PORT = 5555;
	private static ClientInterface user;
	ServerInterface servidor;
	
	public void execute() {
		registerConnection();

		try {
			servidor.test("Essa eh minha mensagem de teste", user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void registerConnection() {
		try {
			Registry referenciaServicoNomes = LocateRegistry.getRegistry("localhost", PORT);
			servidor = (ServerInterface) referenciaServicoNomes.lookup("NomeAleatorio");
			
			user = new Provider();
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
