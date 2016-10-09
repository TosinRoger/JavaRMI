package br.com.tosin.serverrmi.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.tosin.serverrmi.controller.Controller;
import br.com.tosin.serverrmi.models.Book;
import br.com.tosin.serverrmi.utils.Util;

import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.List;
import java.util.Iterator;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblListaDeLivros = new JLabel("Lista de livros");
		lblListaDeLivros.setBounds(12, 37, 115, 15);
		contentPane.add(lblListaDeLivros);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 91, 428, 164);
		contentPane.add(scrollPane);
		

		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("Autor");
		model.addColumn("Titulo");
		model.addColumn("Disponivel");
		model.addColumn("Data Dev");
		
		scrollPane.setViewportView(table);
		
		new Controller(this).execute();
		
	}
	
	/**
	 * Recebe uma lista de livro e mostra na tela
	 * @param books
	 */
	public void populateBooks(java.util.List<Book> books) {
		
		for (Book book : books) {
			String[] item = new String[4];
			item[0] = book.getAuthor();
			item[1] = book.getTitle();
			item[2] = book.isAvailable() ? "Sim" : "Não";
			if(book.isAvailable()) {
				item[3] = "-";
			}
			else {
				item[3] = Util.parseDate(book.getLoan());
			}
			model.addRow(item);
		}
		
		
	}
}
