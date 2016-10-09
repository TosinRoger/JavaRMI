package br.com.tosin.serverrmi.ui;

import javax.swing.JFrame;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GUI() {
		super("Servidor RMI");
		   setSize(400,400);
		   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   setLocationRelativeTo(null);
		   setVisible(true);

	}
	
}
