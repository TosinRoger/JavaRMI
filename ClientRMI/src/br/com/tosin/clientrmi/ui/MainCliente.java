package br.com.tosin.clientrmi.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.tosin.clientrmi.controllers.Controller;
import br.com.tosin.models.Book;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class MainCliente {

	private JFrame frame;
//	private JTable table;
	private DefaultTableModel model;
	private Controller controller;
	private JTable table;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainCliente window = new MainCliente();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainCliente() {
		initialize();
		controller = new Controller(this);
		controller.execute();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnGetBooks = new JButton("Get books");
		btnGetBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.teste();
				controller.requestListBook();
			}
		});
		frame.getContentPane().add(btnGetBooks, BorderLayout.WEST);

		model = new DefaultTableModel();
		
		table = new JTable(model);
		frame.getContentPane().add(table, BorderLayout.CENTER);
		
		lblNewLabel = new JLabel("New label");
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
//		table = new JTable(model);
		model.addColumn("Autor");
		model.addColumn("Titulo");
		model.addColumn("Disponivel");
		model.addColumn("Data Dev");
	}
	
	/**
	 * Recebe uma lista de livro e mostra na tela
	 * @param books
	 */
	public void populateBooks(java.util.List<Book> books) {
		model.setNumRows(1);
		for (Book book : books) {
			String[] item = new String[4];
			item[0] = book.getAuthor();
			item[1] = book.getTitle();
			model.addRow(item);
		}
		
		
	}

}
