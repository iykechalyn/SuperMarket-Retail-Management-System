package supermarket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class SignUp extends JFrame implements ActionListener {

	private JPanel LogincontentPanel;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JComboBox<String> roles;
	private JButton resetButton, signUpButton;
	Connection conn;
	Statement sqlStatement;
	
	public static void main(String[] args) {
	
					SignUp frame = new SignUp();
					frame.setVisible(true);
		
	}

	public SignUp() {
		setTitle("Bedrock Mart POS");
		
		String url = "jdbc:mysql://localhost:3307/bedrockmart";
		String username = "root";
		String password = "root";

		try {

			conn = DriverManager.getConnection(url, username, password);
			sqlStatement = conn.createStatement();
			System.out.println(sqlStatement);
			
			
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 631, 435);
		LogincontentPanel = new JPanel();
		LogincontentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(LogincontentPanel);
		LogincontentPanel.setLayout(null);
		
		JPanel Leftpanel = new JPanel();
		Leftpanel.setBackground(new Color(255, 128, 0));
		Leftpanel.setBounds(0, 0, 271, 398);
		LogincontentPanel.add(Leftpanel);
		Leftpanel.setLayout(null);
		
		JLabel BedrockMartLeft = new JLabel("Bedrock Mart");
		BedrockMartLeft.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BedrockMartLeft.setForeground(new Color(255, 255, 255));
		BedrockMartLeft.setHorizontalAlignment(SwingConstants.CENTER);
		BedrockMartLeft.setBounds(0, 72, 206, 43);
		Leftpanel.add(BedrockMartLeft);
		
		JLabel adminLoginPanel = new JLabel("Admin Login Panel");
		adminLoginPanel.setHorizontalAlignment(SwingConstants.CENTER);
		adminLoginPanel.setForeground(Color.WHITE);
		adminLoginPanel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		adminLoginPanel.setBounds(10, 125, 206, 43);
		Leftpanel.add(adminLoginPanel);
		
		JLabel bedrockLogoPanelLeft = new JLabel("");
		bedrockLogoPanelLeft.setHorizontalAlignment(SwingConstants.CENTER);
		bedrockLogoPanelLeft.setIcon(new ImageIcon(SignUp.class.getResource("/supermarket/images/bedrockmart_logo100x70.png")));
		bedrockLogoPanelLeft.setBounds(10, 163, 250, 166);
		Leftpanel.add(bedrockLogoPanelLeft);
		
		JLabel LoginLable = new JLabel("");
		LoginLable.setFont(new Font("Tahoma", Font.BOLD, 14));
		LoginLable.setForeground(new Color(255, 128, 0));
		LoginLable.setHorizontalAlignment(SwingConstants.CENTER);
		LoginLable.setBounds(417, 10, 81, 33);
		LogincontentPanel.add(LoginLable);
		
		roles = new JComboBox<String>();
		String [] options = {"Admin", "Supervisor", "Cashier", "Accountant"};
		roles.setModel(new DefaultComboBoxModel<String>(options));
		roles.setBounds(387, 65, 160, 33);
		LogincontentPanel.add(roles);
		
		JLabel SelectRoleLable = new JLabel("Select Role");
		SelectRoleLable.setHorizontalAlignment(SwingConstants.RIGHT);
		SelectRoleLable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		SelectRoleLable.setBounds(274, 69, 103, 23);
		LogincontentPanel.add(SelectRoleLable);
		
		userNameField = new JTextField();
		userNameField.setBounds(387, 126, 160, 33);
		LogincontentPanel.add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(387, 203, 160, 33);
		LogincontentPanel.add(passwordField);
		
		JLabel UserNameLable = new JLabel("Username");
		UserNameLable.setHorizontalAlignment(SwingConstants.RIGHT);
		UserNameLable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		UserNameLable.setBounds(274, 136, 103, 23);
		LogincontentPanel.add(UserNameLable);
		
		JLabel PassWordLable = new JLabel("Password");
		PassWordLable.setHorizontalAlignment(SwingConstants.RIGHT);
		PassWordLable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		PassWordLable.setBounds(274, 207, 103, 23);
		LogincontentPanel.add(PassWordLable);
		
		signUpButton = new JButton("Sign Up");
		signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		signUpButton.setBounds(327, 278, 95, 33);
		signUpButton.addActionListener(this);
		LogincontentPanel.add(signUpButton);
		
		resetButton = new JButton("Reset");
		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resetButton.setBounds(449, 278, 95, 33);
		resetButton.addActionListener(this);
		LogincontentPanel.add(resetButton);
		
		JLabel weDeliverFooterLabel = new JLabel(".. we deliver!");
		weDeliverFooterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		weDeliverFooterLabel.setForeground(new Color(255, 128, 0));
		weDeliverFooterLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		weDeliverFooterLabel.setBounds(373, 355, 174, 29);
		LogincontentPanel.add(weDeliverFooterLabel);
		
		JLabel footerLogoLabel = new JLabel("");
		footerLogoLabel.setIcon(new ImageIcon(SignUp.class.getResource("/supermarket/images/bedrockmart-footer.png")));
		footerLogoLabel.setBounds(311, 343, 63, 41);
		LogincontentPanel.add(footerLogoLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String signUpQuerry = String.format("INSERT INTO login_tbl VALUES('%s','%s','%s')", roles.getSelectedItem().toString() ,  userNameField.getText(),
				 passwordField.getPassword().toString());
		if (e.getSource()==resetButton) {
			userNameField.setText("");
		} else if (e.getSource()==signUpButton) {
			if (userNameField.getText().isBlank() ||  passwordField.getPassword().toString().isBlank()) {
				JOptionPane.showMessageDialog(LogincontentPanel, "Field cannot be empty");
			}else {
				try {
					sqlStatement.executeUpdate(signUpQuerry);
					JOptionPane.showMessageDialog(LogincontentPanel, "Successfully registered");
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
			}
		}
		
	}
}
