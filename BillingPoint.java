package supermarket;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class BillingPoint extends JFrame implements ActionListener, MouseListener, WindowListener {

	private JPanel contentPane;
	private ResultSet resultOutput, resultOutput2;
	private Connection conn;
	private Statement sqlStatement;
	private JTable billingtable;
	JLabel lblTotal;
	private JTextField billID;
	private JTextField productQuantityField;
	private JTextField productName;
	private JComboBox<String> productCategory;
	private JButton btnRefresh, addToBill, clear, print;
	int uprice;
	int GrandTotal;
	int prodQty;
	int total;
	DefaultTableModel dfModel;

	public static void main(String[] args) {

		BillingPoint frame = new BillingPoint();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public BillingPoint() {
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		addWindowListener(this);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(148, 0, 1038, 663);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(444, 147, 554, 151);
		panel.add(scrollPane);

		billingtable = new JTable();
		String[] columnNames = { "ID", "Name", "Category", "Quantity", "Price" };
		Object[][] tableData = new Object[][] { null, null, null, null, null };
		billingtable.addMouseListener(this);
		billingtable.setModel(new DefaultTableModel(tableData, columnNames));
		scrollPane.setViewportView(billingtable);

		JLabel lblBillingPoint = new JLabel("BILLING POINT");
		lblBillingPoint.setHorizontalAlignment(SwingConstants.CENTER);
		lblBillingPoint.setForeground(new Color(255, 128, 0));
		lblBillingPoint.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBillingPoint.setBounds(197, 10, 424, 33);

		panel.add(lblBillingPoint);

		JLabel lblBillingId = new JLabel("Billing ID");
		lblBillingId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBillingId.setForeground(new Color(255, 128, 0));
		lblBillingId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBillingId.setBounds(24, 91, 88, 20);
		panel.add(lblBillingId);

		billID = new JTextField();
		billID.setColumns(10);
		billID.setBounds(122, 87, 194, 33);
		panel.add(billID);

		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuantity.setForeground(new Color(255, 128, 0));
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantity.setBounds(24, 209, 88, 20);
		panel.add(lblQuantity);

		productQuantityField = new JTextField();
		productQuantityField.setColumns(10);
		productQuantityField.setBounds(122, 205, 194, 33);
		panel.add(productQuantityField);

		JLabel lblPrice = new JLabel("Name");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setForeground(new Color(255, 128, 0));
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrice.setBounds(24, 151, 88, 20);
		panel.add(lblPrice);

		productName = new JTextField();
		productName.setColumns(10);
		productName.setBounds(122, 147, 194, 33);
		panel.add(productName);

		JLabel lblProductList = new JLabel("PRODUCT LIST");
		lblProductList.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductList.setForeground(new Color(255, 128, 0));
		lblProductList.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProductList.setBounds(526, 34, 424, 33);
		panel.add(lblProductList);

		productCategory = new JComboBox<String>();
		productCategory.setModel(new DefaultComboBoxModel(new String[] { "Beverages", "Babies", "Fashion" }));
		productCategory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		productCategory.setEditable(true);
		productCategory.setBounds(562, 87, 205, 40);
		productCategory.addActionListener(this);
		panel.add(productCategory);

		JLabel ProductCategoryLabel = new JLabel("Fill By");
		ProductCategoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		ProductCategoryLabel.setForeground(new Color(255, 128, 0));
		ProductCategoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ProductCategoryLabel.setBounds(444, 91, 108, 20);
		panel.add(ProductCategoryLabel);

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(this);
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRefresh.setFocusable(false);
		btnRefresh.setBackground(new Color(255, 128, 0));
		btnRefresh.setBounds(825, 86, 125, 40);
		panel.add(btnRefresh);

		addToBill = new JButton("Add To Bill");
		addToBill.addActionListener(this);
		addToBill.setForeground(Color.WHITE);
		addToBill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addToBill.setFocusable(false);
		addToBill.setBackground(new Color(255, 128, 0));
		addToBill.setBounds(56, 298, 144, 40);
		panel.add(addToBill);

		clear = new JButton("Clear");
		clear.addActionListener(this);
		clear.setForeground(Color.WHITE);
		clear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clear.setFocusable(false);
		clear.setBackground(new Color(255, 128, 0));
		clear.setBounds(232, 298, 84, 40);
		panel.add(clear);

		print = new JButton("Print");
		print.addActionListener(this);
		print.setForeground(Color.WHITE);
		print.setFont(new Font("Tahoma", Font.PLAIN, 14));
		print.setFocusable(false);
		print.setBackground(new Color(255, 128, 0));
		print.setBounds(700, 613, 96, 40);
		print.addActionListener(this);
		panel.add(print);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setToolTipText("");
		scrollPane_1.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane_1.setBounds(444, 340, 554, 199);
		panel.add(scrollPane_1);

		resultTable = new JTable();

		resultTable.setToolTipText("");
		resultTable.setEnabled(false);
		resultTable.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane_1.setViewportView(resultTable);
		resultTable.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null }, },
				new String[] { "NUM.", "NAME", "QUANTITY", "PRICE (NGN)", "AMOUNT(NGN)" }));

		lblTotal = new JLabel("TOTAL");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setForeground(new Color(255, 128, 0));
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotal.setBounds(667, 554, 88, 20);
		panel.add(lblTotal);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(
				new ImageIcon(BillingPoint.class.getResource("/supermarket/images/bedrockmart_logo100x70.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 128, 193);
		contentPane.add(lblNewLabel);
		resultTable.getColumnModel().getColumn(4).setPreferredWidth(104);
		displayTable();
		resultViewTable();
	}

	public void mouseClicked(MouseEvent e) {

		dfModel = (DefaultTableModel) billingtable.getModel();
		int tableRowIndex = billingtable.getSelectedRow();

//		productIDField.setText(dfModel.getValueAt(tableRowIndex, 0).toString());
		productName.setText(dfModel.getValueAt(tableRowIndex, 1).toString());
		productCategory.setSelectedItem(dfModel.getValueAt(tableRowIndex, 2));
//		productQuantityField.setText(dfModel.getValueAt(tableRowIndex, 3).toString());
		prodQty = Integer.valueOf(dfModel.getValueAt(tableRowIndex, 3).toString());
//		priceField.setText(dfModel.getValueAt(tableRowIndex, 4).toString());
		uprice = Integer.valueOf(dfModel.getValueAt(tableRowIndex, 4).toString());

//		
//		
//			lblTotal.setText("Total: "+GrandTotal);

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

	int counter = 0;
	private JTable resultTable;

	@Override
	public void actionPerformed(ActionEvent e) {
		total = uprice * Integer.valueOf(productQuantityField.getText());
		GrandTotal = GrandTotal + uprice;
//		String space = "                     ";
		String AddToBillQuery = String.format("INSERT INTO purchase VALUES('%s', '%s','%s','%s','%s')",
				billID.getText(), productName.getText(), productQuantityField.getText(), uprice, total);
		//String beverages = String.format("SELECT * FROM products_tbl WHERE products_tbl ='%s'", );
		if (e.getSource() == addToBill) {
			try {
				sqlStatement.executeUpdate(AddToBillQuery);

				lblTotal.setText("Total: " + GrandTotal);
				resultViewTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
				;
			}

//			if (productName.getText().isBlank() || productQuantityField.getText().isBlank()) {
//				JOptionPane.showMessageDialog(rootPane, "Missing information");
//			} else {
//				counter++;
//				
//				total = uprice * Integer.valueOf(productQuantityField.getText());
//				if (counter == 1) {
//					resultArea.setText(resultArea.getText()+ "***************************** BEDROCK MART ***************************** \n" +
//							"        NUM         PRODUCT         PRICE (NGN)         QUANTITY         TOTAL(NGN)\n          " 
//							+ counter + space+ productName.getText()+space+ uprice +space+productQuantityField.getText()+space+ total);
//				}else {
//					
//					resultArea.setText(resultArea.getText()+
//							"\n          " + counter + "               " 
//							+ productName.getText()+  "                 " + uprice + "                         " +productQuantityField.getText()
//							+"                       "+ total);
//					
//				}
//			}

		} else if (e.getSource() == clear) {
			productName.setText(null);
			productQuantityField.setText(null);

		} else if (e.getSource() == print) {
			try {
				resultTable.print();
			} catch (PrinterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == productCategory) {
			if (productCategory.getSelectedItem().toString() == "Beverages") {

			}
		}

		/*
		 * String addQuerry =
		 * String.format("INSERT INTO products_tbl VALUES('%s','%s','%s','%s','%s')",
		 * productIDField.getText(), productNameField.getText(),
		 * productCategory.getSelectedItem().toString(), productQuantityField.getText(),
		 * priceField.getText()); String deleteQuerry =
		 * String.format("DELETE FROM products_tbl WHERE product_ID='%s'",
		 * productIDField.getText());
		 * 
		 * String updateQuerry = String.format(
		 * "UPDATE products_tbl SET product_name= '%s', category='%s', quantity='%s', price='%s' WHERE product_ID='%s'"
		 * , productNameField.getText(), productCategory.getSelectedItem().toString(),
		 * productQuantityField.getText(), priceField.getText(),
		 * productIDField.getText());
		 */

	}

	// Method for displaying DB content on JTable

	public void displayTable() {

		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";

		try {

			conn = DriverManager.getConnection(url, username, password);
			sqlStatement = conn.createStatement();

			String result = String.format("SELECT * FROM products_tbl WHERE category='%s'",
					productCategory.getSelectedItem());
			ResultSet resultOutput = sqlStatement.executeQuery(result);
			billingtable.setModel(DbUtils.resultSetToTableModel(resultOutput));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	public void resultViewTable() {

		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";

		try {

			conn = DriverManager.getConnection(url, username, password);
			sqlStatement = conn.createStatement();

			String result2 = String.format("SELECT * FROM purchase WHERE billID='%s'", billID.getText());
			ResultSet resultOutput2 = sqlStatement.executeQuery(result2);
			resultTable.setModel(DbUtils.resultSetToTableModel(resultOutput2));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
