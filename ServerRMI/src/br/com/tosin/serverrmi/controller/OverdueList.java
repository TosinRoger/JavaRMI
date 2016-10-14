package br.com.tosin.serverrmi.controller;

import java.util.*;

import br.com.tosin.javarmi.interfaces.ClientInterface;
import br.com.tosin.models.Overdue;
import br.com.tosin.serverrmi.utils.Constants;

/**
 * Classe que gerencia a lista de clientes inadimplementes
 * @author tosin
 *
 */
public class OverdueList {


	private List<Overdue> overdues = new ArrayList<>();
	
	public void addClient(ClientInterface client) {
		overdues.remove(client);
		overdues.add(new Overdue(client, getPenalty()));
	}
	
	/**
	 * Verifica em toda a lista de tem algum cliente que nao esta inadimplente
	 */
	public void verifyAllClient() {
		
		for (Iterator<Overdue> iterator = overdues.iterator(); iterator.hasNext();) {
			Overdue item = iterator.next();
			if (item.getDelayTime() < currentTime())
				iterator.remove();
		}
		
	}
	
	/**
	 * Verifica se o usuario eh inadimplente
	 * @param client
	 * @return TRUE inadimplemente, FALSE sem debitos
	 */
	public boolean consultClient(ClientInterface client) {
		for (Iterator<Overdue> iterator = overdues.iterator(); iterator.hasNext();) {
			Overdue item = iterator.next();
			if (item.getClient().equals(client)) {
				if (item.getDelayTime() < currentTime()) {
					iterator.remove();
					return false;
				}
				else 
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param clientInterface
	 * @return timestamp de ate quando vale a inadimplencia
	 */
	public long timeOverdue(ClientInterface clientInterface) {
		for (Overdue overdue : overdues) {
			if (overdue.getClient().equals(clientInterface)) 
				return overdue.getDelayTime();
		}
		return 0;
	}
	
	/**
	 * 
	 * @return timestamp do tempo atual + penalidade
	 */
	private long getPenalty() {
		long current = currentTime();
		return current + Constants.PENALTY;
	}
	
	/**
	 * 
	 * @return timestamp do tempo atual
	 */
	private long currentTime() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}
}
