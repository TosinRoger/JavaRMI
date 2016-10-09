package br.com.tosin.serverrmi;

import java.awt.EventQueue;

import br.com.tosin.serverrmi.controller.Controller;
import br.com.tosin.serverrmi.ui.MainFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame mainFrame = new MainFrame();
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
