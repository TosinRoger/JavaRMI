package br.com.tosin.clientrmi.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.tosin.clientrmi.controllers.Controller;
import br.com.tosin.models.Book;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class MainCliente {

	private JFrame frame;
	private DefaultTableModel model;
	private DefaultTableModel modelMyBook;
	private Controller controller;
	private JTable tableAllBooks;
	private JLabel lblNewLabel;
	private JPanel panelWest;
	private JButton btnEmprestar;
	private JButton btnReservar;
	private JLabel lblNewLabel_1;
	private JPanel panel_1;
	private JLabel notificacao;
	private JPanel panel_2;
	
	java.util.List<Book> books;
	java.util.List<Book> myBooks;
	private JButton btnDevolver;
	private JPanel panel_3;
	private JScrollPane scrollPane_allBooks;
	private JPanel panel_Center;
	private JLabel lblTodosOsLivros;
	private JLabel lblNewLabel_2;
	private JPanel panel_4;
	private JPanel panel_5;
	private JScrollPane scrollPane_myBooks;
	private JPanel panel;
	private JPanel panel_6;
	private JTable tableMyBooks;
	private JButton btnRenovar;

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
		frame = new JFrame("Client");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("Gerencia cliente");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel);
		
		
		
		panelWest = new JPanel();
		frame.getContentPane().add(panelWest, BorderLayout.WEST);
		
		JButton btnGetBooks = new JButton("Busca livros");
		btnGetBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.teste();
				controller.requestListBook();
			}
		});
		panelWest.setLayout(new GridLayout(5, 1, 0, 0));
		panelWest.add(btnGetBooks);
		
		btnEmprestar = new JButton("Emprestar");
		btnEmprestar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = tableAllBooks.getSelectedRow();
				if (books == null || selected < 0) {
					showNotification("Nenhum livro selecionado");
				}
				else {
					Book book = books.get(selected);
					controller.loan(book);
				}
			}
		});
		panelWest.add(btnEmprestar);
		
		btnReservar = new JButton("Reservar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = tableAllBooks.getSelectedRow();
				if (books == null || selected < 0) {
					showNotification("Nenhum livro selecionado");
				}
				else {
					Book book = books.get(selected);
					controller.reservation(book);
				}
			}
		});
		
		btnDevolver = new JButton("Devolver");
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = tableMyBooks.getSelectedRow();
				if (books == null || selected < 0) {
					showNotification("Nenhum livro selecionado");
				}
				else {
					Book book = books.get(selected);
					controller.devolution(book);
				}
			}
		});
		panelWest.add(btnDevolver);
		
		btnRenovar = new JButton("Renovar");
		btnRenovar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = tableMyBooks.getSelectedRow();
				if (books == null || selected < 0) {
					showNotification("Nenhum livro selecionado");
				}
				else {
					Book book = books.get(selected);
					controller.renovation(book);
				}
			}
		});
		panelWest.add(btnRenovar);
		panelWest.add(btnReservar);
		

		model = new DefaultTableModel();
		
		model.addColumn("Autor");
		model.addColumn("Titulo");
		model.addColumn("Disponivel");
		
		panel_Center = new JPanel();
		frame.getContentPane().add(panel_Center, BorderLayout.CENTER);
		panel_Center.setLayout(new GridLayout(2, 1, 0, 0));
		
		panel = new JPanel();
		panel_Center.add(panel);
		panel.setLayout(null);
		
		lblTodosOsLivros = new JLabel("Todos os livros");
		lblTodosOsLivros.setBounds(0, 0, 446, 21);
		panel.add(lblTodosOsLivros);
		lblTodosOsLivros.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_4 = new JPanel();
		panel_4.setBounds(0, 49, 482, 123);
		panel.add(panel_4);
		panel_4.setLayout(null);
		tableAllBooks = new JTable(model);
		
		scrollPane_allBooks = new JScrollPane();
		scrollPane_allBooks.setBounds(22, 0, 446, 103);
		panel_4.add(scrollPane_allBooks);
		scrollPane_allBooks.setViewportView(tableAllBooks);
		
		panel_6 = new JPanel();
		panel_Center.add(panel_6);
		
		lblNewLabel_2 = new JLabel("Meus livros");
		lblNewLabel_2.setBounds(0, 0, 482, 20);
		
		panel_5 = new JPanel();
		panel_5.setBounds(7, 30, 465, 137);
		panel_5.setLayout(null);
		
		scrollPane_myBooks = new JScrollPane();
		scrollPane_myBooks.setBounds(4, 5, 455, 124);
		panel_5.add(scrollPane_myBooks);
		
		modelMyBook = new DefaultTableModel();
		
		modelMyBook.addColumn("Autor");
		modelMyBook.addColumn("Titulo");
		modelMyBook.addColumn("Data Dev");
		
		tableMyBooks = new JTable();
		tableMyBooks.setModel(modelMyBook);
		tableMyBooks.setBounds(0, 0, 1, 1);

		scrollPane_myBooks.setViewportView(tableMyBooks);
		
		panel_6.setLayout(null);
		panel_6.add(lblNewLabel_2);
		panel_6.add(panel_5);
		
		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(2, 1, 0, 0));
		
		lblNewLabel_1 = new JLabel("Notificacoes: ");
		panel_1.add(lblNewLabel_1);
		
		notificacao = new JLabel("Sem notificacoes");
		panel_1.add(notificacao);
		
	}
	
	/**
	 * Recebe uma lista de livro e mostra na tela
	 * @param books
	 */
	public void populateBooks(java.util.List<Book> books) {
		this.books = books;
		model.setNumRows(0);
		model.setRowCount(0);
		for (Book book : books) {
			String[] item = new String[4];
			item[0] = book.getAuthor();
			item[1] = book.getTitle();
			item[2] = book.isAvailable() ? "Sim" : "Não";
			model.addRow(item);
		}
		
	}
	
	public void populateMyBooks(List<Book> books) {
		this.myBooks = books;
		
		modelMyBook.setRowCount(0);
		
		if (this.myBooks != null) {
			for (Book book : books) {
				String[] item = new String[4];
				item[0] = book.getAuthor();
				item[1] = book.getTitle();
				item[2] = parseDate(book.getTimeDevolution());
				modelMyBook.addRow(item);
			}
		}
	}
	
	public void showNotification(String msg) {
		notificacao.setText(msg);
		controller.requestListBook();
	}
	
	public void notifyBookAvailable(Book book) {
		final JFrame parent = new JFrame();
		JOptionPane.showMessageDialog(parent, "O livro " + book.getTitle() + " ja esta dipoinivel!");	}
	
	/**
	 * Retorna string com a data em dd/MM/yyyy
	 * @param calendar
	 * @return
	 */
	private static String parseDate(long time) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		String date = dateFormat.format(calendar.getTime());
		return date;
	}
}
