package supermarket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

public class Category extends JFrame implements ActionListener, MouseListener, WindowListener {

	private JPanel contentPane, panel;
	 JTextField categoryID;
	private JTextField categoryName;
	private JTextArea decriptionTextArea;
	private JTable categoryTable;
	private JButton addButton, clearButton, deleteButton, updateButton;
	private Statement sqlStat;
	private Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Category frame = new Category();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public Category() {
		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";

		try {
			con = DriverManager.getConnection(url, username, password);
			sqlStat = con.createStatement();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		setTitle("Bedrock Mart Retail POS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 542);
		addWindowListener(this);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(135, 0, 801, 505);
		contentPane.add(panel);
		panel.setLayout(null);

		categoryID = new JTextField();
		categoryID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		categoryID.setColumns(10);
		categoryID.setBounds(118, 59, 194, 33);
		panel.add(categoryID);

		JLabel categoryDLabel = new JLabel("Category ID");
		categoryDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		categoryDLabel.setForeground(new Color(255, 128, 0));
		categoryDLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		categoryDLabel.setBounds(20, 63, 88, 20);
		panel.add(categoryDLabel);

		JLabel sellerNameLabel = new JLabel("Name");
		sellerNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sellerNameLabel.setForeground(new Color(255, 128, 0));
		sellerNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sellerNameLabel.setBounds(0, 117, 108, 20);
		panel.add(sellerNameLabel);

		categoryName = new JTextField();
		categoryName.setColumns(10);
		categoryName.setBounds(118, 113, 194, 33);
		panel.add(categoryName);

		JLabel categoryDecription = new JLabel("Decription");
		categoryDecription.setHorizontalAlignment(SwingConstants.TRAILING);
		categoryDecription.setForeground(new Color(255, 128, 0));
		categoryDecription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		categoryDecription.setBounds(390, 63, 88, 20);
		panel.add(categoryDecription);

		JLabel manageProductLabel_1 = new JLabel("PRODUCT CATEGORIES");
		manageProductLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		manageProductLabel_1.setForeground(new Color(255, 128, 0));
		manageProductLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		manageProductLabel_1.setBounds(10, 10, 708, 33);
		panel.add(manageProductLabel_1);

		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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

		clearButton = new JButton("Clear");
		clearButton.setForeground(Color.WHITE);
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		clearButton.setFocusable(false);
		clearButton.setBackground(new Color(255, 128, 0));
		clearButton.setBounds(588, 228, 78, 33);
		clearButton.addActionListener(this);
		panel.add(clearButton);

		JScrollPane CategoryscrollPane = new JScrollPane();
		CategoryscrollPane.setName("header");
		CategoryscrollPane.setBounds(21, 323, 697, 152);
		panel.add(CategoryscrollPane);

		categoryTable = new JTable();
		categoryTable.setModel(new DefaultTableModel(new Object[][] { { "", "", "" }, },
				new String[] { "Catergory ID", "Name", "Decription" }));
		categoryTable.addMouseListener(this);
		CategoryscrollPane.setViewportView(categoryTable);

		JLabel CategoryListLabel = new JLabel("CATEGORY LIST");
		CategoryListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CategoryListLabel.setForeground(new Color(255, 128, 0));
		CategoryListLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		CategoryListLabel.setBounds(0, 280, 724, 33);
		panel.add(CategoryListLabel);

		decriptionTextArea = new JTextArea();
		decriptionTextArea.setBounds(488, 64, 194, 44);
		panel.add(decriptionTextArea);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel
				.setIcon(new ImageIcon(Category.class.getResource("/supermarket/images/bedrockmart_logo100x70.png")));
		lblNewLabel.setBounds(0, 0, 134, 213);
		contentPane.add(lblNewLabel);

		displayTable();
	}

	// Method for displaying DB content on JTable
	public void displayTable() {

		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";

		try {

			con = DriverManager.getConnection(url, username, password);
			sqlStat = con.createStatement();
			String result = "SELECT * FROM categories_tbl";
			ResultSet resultOutput = sqlStat.executeQuery(result);
			categoryTable.setModel(DbUtils.resultSetToTableModel(resultOutput));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String addQuery = String.format("INSERT INTO categories_tbl VALUES('%s', '%s', '%s')", categoryID.getText(),
				categoryName.getText(), decriptionTextArea.getText());
		String updateQuery = String.format(
				"UPDATE categories_tbl SET category_ID='%s', Name='%s', Decription='%s' WHERE category_ID='%s'",
				categoryID.getText(), categoryName.getText(), decriptionTextArea.getText(), categoryID.getText());
		String deleteQuery = String.format("DELETE FROM categories_tbl WHERE category_ID='%s'", categoryID.getText());

		if (e.getSource() == addButton) {
			if (categoryID.getText().isBlank() || categoryName.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Missing information");
				displayTable();
			} else {
				try {
					sqlStat.executeUpdate(addQuery);
					JOptionPane.showMessageDialog(null, categoryName.getText() + " added Successfully");
					displayTable();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		} else if (e.getSource() == updateButton) {
			if (categoryID.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Nothing selected");
				displayTable();
			} else {
				try {
					sqlStat.executeUpdate(updateQuery);
					JOptionPane.showMessageDialog(null, "Table updated");
					displayTable();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());

				}
			}

		} else if (e.getSource() == deleteButton) {
			if (categoryID.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Nothing selected");
				displayTable();
			} else {

				try {
					sqlStat.executeUpdate(deleteQuery);
					JOptionPane.showMessageDialog(null, "Category ID " + categoryID.getText() + " deleted succeffully");
					displayTable();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		} else if (e.getSource() == clearButton) {
			categoryID.setText("");
			categoryName.setText("");
			decriptionTextArea.setText("");
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DefaultTableModel dfTabModel = (DefaultTableModel) categoryTable.getModel();
		int tableRowIndex = categoryTable.getSelectedRow();
		categoryID.setText(dfTabModel.getValueAt(tableRowIndex, 0).toString());
		categoryName.setText(dfTabModel.getValueAt(tableRowIndex, 1).toString());
		decriptionTextArea.setText(dfTabModel.getValueAt(tableRowIndex, 2).toString());

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Not Implemented

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Not Implemented

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not Implemented

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not Implemented

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
