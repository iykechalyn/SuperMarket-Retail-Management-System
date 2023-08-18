package supermarket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

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
import com.jgoodies.forms.factories.DefaultComponentFactory;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class Products extends JFrame implements ActionListener, MouseListener, WindowListener {
	private ResultSet resultOutput;
	private Connection conn;
	private Statement sqlStatement;
	private JTextField productIDField, productNameField, productQuantityField, priceField;
	private JTable productList;
	JComboBox productCategory;
	JButton btnClear, addButton, updateButton, deleteButton;
	JLabel lblLogout;
	int n;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Products frame = new Products();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public Products() {
		setResizable(false);
		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";
		try {
			conn = DriverManager.getConnection(url, username, password);
			sqlStatement = conn.createStatement();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		setTitle("Bedrock Mart Retail POS");
		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1076, 595);
		addWindowListener(this);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 128, 0));
		panel.setBounds(0, 0, 1052, 558);
		getContentPane().add(panel);
		panel.setLayout(null);

		JPanel mainPane = new JPanel();
		mainPane.setLayout(null);
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setBackground(Color.WHITE);
		mainPane.setBounds(125, 0, 945, 558);
		panel.add(mainPane);

		JLabel manageProductLabel = new JLabel("MANAGE PRODUCTS");
		manageProductLabel.setHorizontalAlignment(SwingConstants.CENTER);
		manageProductLabel.setForeground(new Color(255, 128, 0));
		manageProductLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		manageProductLabel.setBounds(10, 0, 916, 33);
		mainPane.add(manageProductLabel);

		JLabel ProductIDLabel = new JLabel("Product ID");
		ProductIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ProductIDLabel.setForeground(new Color(255, 128, 0));
		ProductIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ProductIDLabel.setBounds(71, 57, 88, 20);
		mainPane.add(ProductIDLabel);

		productIDField = new JTextField();
		productIDField.setColumns(10);
		productIDField.setBounds(169, 53, 194, 33);
		mainPane.add(productIDField);

		JLabel ProductNameLable = new JLabel("Product Name");
		ProductNameLable.setHorizontalAlignment(SwingConstants.RIGHT);
		ProductNameLable.setForeground(new Color(255, 128, 0));
		ProductNameLable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ProductNameLable.setBounds(51, 111, 108, 20);
		mainPane.add(ProductNameLable);

		productNameField = new JTextField();
		productNameField.setColumns(10);
		productNameField.setBounds(169, 107, 194, 33);
		mainPane.add(productNameField);

		JLabel QuantityLabel = new JLabel("Quantity");
		QuantityLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		QuantityLabel.setForeground(new Color(255, 128, 0));
		QuantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		QuantityLabel.setBounds(503, 57, 88, 20);
		mainPane.add(QuantityLabel);

		productQuantityField = new JTextField();
		productQuantityField.setColumns(10);
		productQuantityField.setBounds(601, 53, 194, 33);
		mainPane.add(productQuantityField);

		JLabel PriceLabel = new JLabel("Price");
		PriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		PriceLabel.setForeground(new Color(255, 128, 0));
		PriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		PriceLabel.setBounds(493, 111, 88, 20);
		mainPane.add(PriceLabel);

		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(601, 107, 194, 33);
		mainPane.add(priceField);

		productCategory = new JComboBox();
		productCategory.setModel(new DefaultComboBoxModel(new String[] { "Beverages", "Babies" }));
		productCategory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		productCategory.setEditable(true);
		productCategory.setBounds(169, 158, 194, 33);
		mainPane.add(productCategory);

		JLabel ProductCategoryLabel = new JLabel("Category");
		ProductCategoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ProductCategoryLabel.setForeground(new Color(255, 128, 0));
		ProductCategoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ProductCategoryLabel.setBounds(51, 162, 108, 20);
		mainPane.add(ProductCategoryLabel);

		addButton = new JButton("Add");
		addButton.setForeground(Color.WHITE);
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addButton.setFocusable(false);
		addButton.setBackground(new Color(255, 128, 0));
		addButton.setBounds(229, 225, 78, 33);
		addButton.addActionListener(this);
		mainPane.add(addButton);

		updateButton = new JButton("Update");
		updateButton.setForeground(Color.WHITE);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		updateButton.setFocusable(false);
		updateButton.setBackground(new Color(255, 128, 0));
		updateButton.setBounds(383, 224, 78, 33);
		updateButton.addActionListener(this);
		mainPane.add(updateButton);

		deleteButton = new JButton("Delete");
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		deleteButton.setFocusable(false);
		deleteButton.setBackground(new Color(255, 128, 0));
		deleteButton.setBounds(547, 223, 78, 33);
		deleteButton.addActionListener(this);
		mainPane.add(deleteButton);

		btnClear = new JButton("Clear");
		btnClear.setForeground(Color.WHITE);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnClear.setFocusable(false);
		btnClear.setBackground(new Color(255, 128, 0));
		btnClear.setBounds(699, 222, 78, 33);
		btnClear.addActionListener(this);
		mainPane.add(btnClear);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setName("header");
		scrollPane.setBounds(99, 307, 738, 152);
		mainPane.add(scrollPane);

		productList = new JTable();

		String[] columnNames = new String[] { "PRODUCT ID", "PROD. NAME", "CATEGORY", "PROD. QTY ", "PROD. PRICE" };
		Object[][] tableData = new Object[][] {};

		productList.setModel(new DefaultTableModel(tableData, columnNames));

		productList.addMouseListener(this);
		scrollPane.setViewportView(productList);

		JLabel lblProducts = new JLabel("PRODUCTS LIST");
		lblProducts.setHorizontalAlignment(SwingConstants.CENTER);
		lblProducts.setForeground(new Color(255, 128, 0));
		lblProducts.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblProducts.setBounds(10, 280, 916, 33);
		mainPane.add(lblProducts);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel
				.setIcon(new ImageIcon(Products.class.getResource("/supermarket/images/bedrockmart_logo100x70.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 105, 161);
		panel.add(lblNewLabel);

		lblLogout = new JLabel("Logout");
		lblLogout.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogout.setForeground(new Color(255, 255, 255));
		lblLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLogout.setBounds(10, 501, 88, 20);
		panel.add(lblLogout);
		displayTable();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DefaultTableModel dfModel = (DefaultTableModel) productList.getModel();
		int tableRowIndex = productList.getSelectedRow();
		productIDField.setText(dfModel.getValueAt(tableRowIndex, 0).toString());
		productNameField.setText(dfModel.getValueAt(tableRowIndex, 1).toString());
		productCategory.setSelectedItem(dfModel.getValueAt(tableRowIndex, 2));
		productQuantityField.setText(dfModel.getValueAt(tableRowIndex, 3).toString());
		priceField.setText(dfModel.getValueAt(tableRowIndex, 4).toString());

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
	public void actionPerformed(ActionEvent e) {
		String addQuerry = String.format("INSERT INTO products_tbl VALUES('%s','%s','%s','%s','%s')",
				productIDField.getText(), productNameField.getText(), productCategory.getSelectedItem().toString(),
				productQuantityField.getText(), priceField.getText());
		String deleteQuerry = String.format("DELETE FROM products_tbl WHERE product_ID='%s'", productIDField.getText());

		String updateQuerry = String.format(
				"UPDATE products_tbl SET product_name= '%s', category='%s', quantity='%s', price='%s' WHERE product_ID='%s'",
				productNameField.getText(), productCategory.getSelectedItem().toString(),
				productQuantityField.getText(), priceField.getText(), productIDField.getText());
		if (e.getSource() == addButton) {
			if (productIDField.getText().isBlank() || productNameField.getText().isBlank()
					|| productCategory.getSelectedItem().toString().isBlank()
					|| productQuantityField.getText().isBlank() || priceField.getText().isBlank()) {
				JOptionPane.showMessageDialog(rootPane, "Missing information");
				displayTable();
			} else {

				try {
					sqlStatement.executeUpdate(addQuerry);
					JOptionPane.showMessageDialog(rootPane, "Record added succesfully");
					displayTable();
				} catch (SQLException e1) {

					JOptionPane.showMessageDialog(rootPane, e1.getMessage());
				}
			}

		} else if ((e.getSource() == updateButton)) {
			if (productIDField.getText().isBlank()) {
				JOptionPane.showMessageDialog(rootPane, "No item selected");
				displayTable();
			} else {

				try {
					sqlStatement.executeUpdate(updateQuerry);
					JOptionPane.showMessageDialog(rootPane, "Updated successfully");
					displayTable();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(rootPane, e1.getMessage());
				}
			}

		} else if (e.getSource() == deleteButton) {
			if (productIDField.getText().isBlank()) {
				JOptionPane.showMessageDialog(rootPane, "No item selected");
				displayTable();
			} else {
				try {
					sqlStatement.executeUpdate(deleteQuerry);
					JOptionPane.showMessageDialog(rootPane, "Deleted Successfully");
					displayTable();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(rootPane, e1.getMessage());
				}
			}

		} else if (e.getSource() == btnClear) {
			productIDField.setText("");
			productNameField.setText("");
			productCategory.setSelectedItem("");
			productQuantityField.setText("");
			priceField.setText("");
			displayTable();

		}

	}

	// Method for displaying DB content on JTable
	public void displayTable() {

		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";

		try {

			conn = DriverManager.getConnection(url, username, password);
			sqlStatement = conn.createStatement();
			String result = "SELECT * FROM products_tbl";
			ResultSet resultOutput = sqlStatement.executeQuery(result);
			productList.setModel(DbUtils.resultSetToTableModel(resultOutput));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// NOT IMPLEMETNED

	}

	@Override
	public void windowClosing(WindowEvent e) {
	 n = JOptionPane.showConfirmDialog(null, "Do you want to close this window?", "Confirmation",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if (n == JOptionPane.YES_OPTION) {
			dispose();
		}

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
