package supermarket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;

import net.proteanit.sql.DbUtils;
import javax.swing.ImageIcon;

public class Cashiers extends JFrame implements ActionListener, MouseListener, WindowListener {

	private JPanel contentPane, panel;
	private JTextField cashierID;
	private JTextField name;
	private JPasswordField passwordField;
	private JTable cashierListTable;
	private JComboBox gender;
	private Statement sqlStatement;
	JButton addButton, deleteButton, updateButton, resetButton;
	ResultSet resultOutput;
	int Cashier_ID;
	String Name, Gender, PasswordField;
	Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Cashiers frame = new Cashiers();
		frame.setVisible(true);

	}

	// Frame
	public Cashiers() {

		setTitle("Bedrock Mart Retail POS");

		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";

		try {

			conn = DriverManager.getConnection(url, username, password);
			sqlStatement = conn.createStatement();
			System.out.println("Database connection successful");
			conn.close();
		} catch (Exception eSeller) {
			System.out.println("Error " + eSeller.getMessage());
		}

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 950, 542);
		addWindowListener(this);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(192, 0, 744, 505);
		contentPane.add(panel);
		panel.setLayout(null);

		cashierID = new JTextField();
		cashierID.setColumns(10);
		cashierID.setBounds(118, 59, 194, 33);
		panel.add(cashierID);

		JLabel Cashier_IDLabel = new JLabel("CashierID");
		Cashier_IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		Cashier_IDLabel.setForeground(new Color(255, 128, 0));
		Cashier_IDLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Cashier_IDLabel.setBounds(20, 63, 88, 20);
		panel.add(Cashier_IDLabel);

		JLabel sellerNameLabel = new JLabel("Name");
		sellerNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sellerNameLabel.setForeground(new Color(255, 128, 0));
		sellerNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sellerNameLabel.setBounds(0, 117, 108, 20);
		panel.add(sellerNameLabel);

		name = new JTextField();
		name.setColumns(10);
		name.setBounds(118, 113, 194, 33);
		panel.add(name);

		JLabel GenderLabel = new JLabel("Gender");
		GenderLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GenderLabel.setForeground(new Color(255, 128, 0));
		GenderLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GenderLabel.setBounds(380, 117, 88, 20);
		panel.add(GenderLabel);

		JLabel sellerPasswordLable = new JLabel("Password");
		sellerPasswordLable.setHorizontalAlignment(SwingConstants.TRAILING);
		sellerPasswordLable.setForeground(new Color(255, 128, 0));
		sellerPasswordLable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sellerPasswordLable.setBounds(390, 63, 88, 20);
		panel.add(sellerPasswordLable);

		JLabel manageProductLabel_1 = new JLabel("MANAGE CASHIERS");
		manageProductLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		manageProductLabel_1.setForeground(new Color(255, 128, 0));
		manageProductLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		manageProductLabel_1.setBounds(10, 10, 708, 33);
		panel.add(manageProductLabel_1);

		updateButton = new JButton("Update");
		updateButton.setForeground(Color.WHITE);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		updateButton.setFocusable(false);
		updateButton.setBackground(new Color(255, 128, 0));
		updateButton.setBounds(272, 230, 78, 33);
		updateButton.addActionListener(this);
		panel.add(updateButton);

		addButton = new JButton("Add");
		addButton.addActionListener(this);

		addButton.setForeground(Color.WHITE);
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addButton.setFocusable(false);
		addButton.setBackground(new Color(255, 128, 0));
		addButton.setBounds(118, 231, 78, 33);
		panel.add(addButton);

		deleteButton = new JButton("Delete");
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		deleteButton.setFocusable(false);
		deleteButton.setBackground(new Color(255, 128, 0));
		deleteButton.setBounds(436, 229, 78, 33);
		deleteButton.addActionListener(this);
		panel.add(deleteButton);

		resetButton = new JButton("Clear");
		resetButton.setForeground(Color.WHITE);
		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resetButton.setFocusable(false);
		resetButton.setBackground(new Color(255, 128, 0));
		resetButton.setBounds(588, 228, 78, 33);
		resetButton.addActionListener(this);
		panel.add(resetButton);

		JScrollPane displayTableScrollPane = new JScrollPane();
		displayTableScrollPane.setName("header");
		displayTableScrollPane.setBounds(21, 323, 697, 152);
		panel.add(displayTableScrollPane);

		cashierListTable = new JTable();
		cashierListTable.addMouseListener(this);
		cashierListTable.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
				new String[] { "Cashier_ID", "Name", "Gender", "Password" }));
		cashierListTable.getColumnModel().getColumn(0).setPreferredWidth(47);
		displayTableScrollPane.setViewportView(cashierListTable);

		JLabel CashierListLabel = new JLabel("CASHIERS LIST");
		CashierListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CashierListLabel.setForeground(new Color(255, 128, 0));
		CashierListLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		CashierListLabel.setBounds(0, 280, 724, 33);
		panel.add(CashierListLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(488, 59, 194, 33);
		panel.add(passwordField);

		gender = new JComboBox();
		gender.setModel(new DefaultComboBoxModel(new String[] { "", "Male", "Female" }));
		gender.setBounds(488, 113, 194, 33);
		panel.add(gender);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel
				.setIcon(new ImageIcon(Cashiers.class.getResource("/supermarket/images/bedrockmart_logo100x70.png")));
		lblNewLabel.setBounds(14, 10, 168, 252);
		contentPane.add(lblNewLabel);

		displayTable();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String addQuerry = String.format("INSERT INTO cashier_table VALUES('%s','%s','%s','%s')", cashierID.getText(),
				name.getText(), gender.getSelectedItem().toString(), passwordField.getPassword().toString());
		String deleteQuerry = String.format("DELETE FROM cashier_table WHERE Cashier_ID='%s'", cashierID.getText());

		String updateQuerry = String.format(
				"UPDATE cashier_table SET Name = '%s', Gender='%s', Password='%s' WHERE Cashier_ID='%s'",
				name.getText(), gender.getSelectedItem().toString(), passwordField.getPassword(), cashierID.getText());

		if (e.getSource() == addButton) {
			if (cashierID.getText().isEmpty() || name.getText().isEmpty()
					|| gender.getSelectedItem().toString().isEmpty()
					|| passwordField.getPassword().toString().isEmpty()) {
				JOptionPane.showMessageDialog(this, "There are missing data");
				displayTable();
			} else {
				try {
					sqlStatement.executeUpdate(addQuerry);
					JOptionPane.showMessageDialog(null, "Added successfully");
					displayTable();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());

				}
			}

		} else if (e.getSource() == updateButton) {
			if (cashierID.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Error: No Cashier ID seleted");

			} else {
				try {
					sqlStatement.executeUpdate(updateQuerry);
					JOptionPane.showMessageDialog(null, "Updated successfully");
					displayTable();
				} catch (SQLException e1) {

					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}

		} else if (e.getSource() == resetButton) {
			cashierID.setText("");
			name.setText("");
			gender.setSelectedItem("");
			passwordField.setText("");
			displayTable();

		} else if (e.getSource() == deleteButton) {
			if (cashierID.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Error: No Cashier ID seleted");

			}else {
			try {
				sqlStatement.executeUpdate(deleteQuerry);
				JOptionPane.showMessageDialog(null, "Deleted succesffully");
				displayTable();
			} catch (SQLException e1) {

				JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
			}

		}}
	}

	// Method for displaying DB content on JTable
	public void displayTable() {

		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";

		try {

			conn = DriverManager.getConnection(url, username, password);
			sqlStatement = conn.createStatement();
			String result = "SELECT * FROM cashier_table";
			ResultSet resultOutput = sqlStatement.executeQuery(result);
			cashierListTable.setModel(DbUtils.resultSetToTableModel(resultOutput));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DefaultTableModel dfModel = (DefaultTableModel) cashierListTable.getModel();
		int tableRowIndex = cashierListTable.getSelectedRow();
		cashierID.setText(dfModel.getValueAt(tableRowIndex, 0).toString());
		name.setText(dfModel.getValueAt(tableRowIndex, 1).toString());
		gender.setSelectedItem(dfModel.getValueAt(tableRowIndex, 2));
		passwordField.setText(dfModel.getValueAt(tableRowIndex, 3).toString());

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Not implemented

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Not implemented

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not implemented

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not implemented

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// Not implemented
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		 int n = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "Confirmation",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (n == JOptionPane.YES_OPTION) {
				dispose();
			}
		
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// Not implemented
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// Not implemented
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// Not implemented
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// Not implemented
		
	}
}
