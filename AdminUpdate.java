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
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class AdminUpdate extends JFrame implements ActionListener, WindowListener {

	private JPanel LogincontentPanel, rightpanel, Leftpanel;
	private JLabel bedrockLogoPanelLeft;
	private JLabel userName;
	private JTextField adminUserName;
	private JLabel passwordLable;
	private JButton btnClear, updateButton;
	private JLabel weDeliverFooterLabel;
	private Statement sqlStat;
	private Connection con;
	private JPasswordField adminPassword;

	// Launch the application.

	public static void main(String[] args) {

		AdminUpdate frame = new AdminUpdate();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public AdminUpdate() {

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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 631, 435);
		LogincontentPanel = new JPanel();
		LogincontentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(LogincontentPanel);
		LogincontentPanel.setLayout(null);

		Leftpanel = new JPanel();
		Leftpanel.setBackground(new Color(255, 128, 0));
		Leftpanel.setBounds(0, 0, 188, 398);
		LogincontentPanel.add(Leftpanel);
		Leftpanel.setLayout(null);

		bedrockLogoPanelLeft = new JLabel("");
		bedrockLogoPanelLeft.setHorizontalAlignment(SwingConstants.CENTER);
		bedrockLogoPanelLeft.setIcon(
				new ImageIcon(AdminUpdate.class.getResource("/supermarket/images/bedrockmart_logo100x70.png")));
		bedrockLogoPanelLeft.setBounds(0, 162, 178, 152);
		Leftpanel.add(bedrockLogoPanelLeft);

		rightpanel = new JPanel();
		rightpanel.setBounds(184, 0, 433, 398);
		LogincontentPanel.add(rightpanel);
		rightpanel.setLayout(null);
		rightpanel.setBackground(new Color(255, 255, 255));

		JLabel adminUpdate = new JLabel("ADMIN UPDATE");
		adminUpdate.setBounds(10, 10, 413, 33);
		rightpanel.add(adminUpdate);
		adminUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		adminUpdate.setForeground(new Color(255, 128, 0));
		adminUpdate.setHorizontalAlignment(SwingConstants.CENTER);

		userName = new JLabel("User Name ");
		userName.setHorizontalAlignment(SwingConstants.RIGHT);
		userName.setForeground(new Color(255, 128, 0));
		userName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userName.setBounds(48, 80, 88, 20);
		rightpanel.add(userName);

		adminUserName = new JTextField();
		adminUserName.setEditable(false);
		adminUserName.setText("Admin");
		adminUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		adminUserName.setColumns(10);
		adminUserName.setBounds(146, 76, 204, 33);
		rightpanel.add(adminUserName);

		passwordLable = new JLabel("Password");
		passwordLable.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLable.setForeground(new Color(255, 128, 0));
		passwordLable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordLable.setBounds(28, 134, 108, 20);
		rightpanel.add(passwordLable);

		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		updateButton.setBounds(146, 187, 99, 33);
		rightpanel.add(updateButton);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(this);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnClear.setBounds(255, 187, 95, 33);
		rightpanel.add(btnClear);

		weDeliverFooterLabel = new JLabel(".. we deliver!");
		weDeliverFooterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		weDeliverFooterLabel.setForeground(new Color(255, 128, 0));
		weDeliverFooterLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		weDeliverFooterLabel.setBounds(71, 334, 313, 33);
		rightpanel.add(weDeliverFooterLabel);

		adminPassword = new JPasswordField();
		adminPassword.setBounds(146, 130, 204, 33);
		rightpanel.add(adminPassword);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String updateQuery = String.format("UPDATE admintable SET admin_password='%S' WHERE ID='%s'",
				adminPassword.getPassword().toString(), 1);

		if (e.getSource() == btnClear) {
			adminPassword.setText("");
		} else if (e.getSource() == updateButton) {
			try {
				sqlStat.executeUpdate(updateQuery);
				JOptionPane.showMessageDialog(rightpanel, "Password Updated");
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage());
				
			}
		}

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
