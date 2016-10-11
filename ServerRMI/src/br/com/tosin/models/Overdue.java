package br.com.tosin.models;

import br.com.tosin.javarmi.interfaces.ClientInterface;

public class Overdue {
	
	private ClientInterface client;
	private long delayTime;
	
	
	public Overdue(ClientInterface client, long delayTime) {
		super();
		this.client = client;
		this.delayTime = delayTime;
	}


	public ClientInterface getClient() {
		return client;
	}


	public long getDelayTime() {
		return delayTime;
	}
	
	
}
