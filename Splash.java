package supermarket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;

public class Splash extends JFrame {

	private JPanel contentPane;
	JProgressBar MyprogressBar;
	JLabel percentageLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Splash splashFrame = new Splash();
		splashFrame.setVisible(true);
		try {
			for (int i = 0; i <= 100; i++) {
				Thread.sleep(60);
				splashFrame.MyprogressBar.setValue(i);
				splashFrame.percentageLabel.setText("Loading ..." + Integer.toString(i) + "%");
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());

		}
		
		new Login().setVisible(true);
		splashFrame.dispose();

	}

	/**
	 * Create the frame.
	 */
	public Splash() {
		setResizable(false);
		setTitle("Bedrock Mart POS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 409);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel bedrockHeaderLabel = new JLabel("WELCOME TO BEDROCK MART");
		bedrockHeaderLabel.setForeground(new Color(255, 255, 255));
		bedrockHeaderLabel.setFont(new Font("Californian FB", Font.BOLD, 24));
		bedrockHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bedrockHeaderLabel.setBounds(10, 10, 609, 38);
		contentPane.add(bedrockHeaderLabel);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel
				.setIcon(new ImageIcon(Splash.class.getResource("/supermarket/images/bedrockmart_logo250x175a.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(157, 84, 267, 167);
		contentPane.add(lblNewLabel);

		percentageLabel = new JLabel("%");
		percentageLabel.setForeground(new Color(255, 255, 255));
		percentageLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		percentageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		percentageLabel.setBounds(65, 295, 491, 43);
		contentPane.add(percentageLabel);

		MyprogressBar = new JProgressBar();
		MyprogressBar.setBounds(0, 348, 629, 24);
		contentPane.add(MyprogressBar);

	}
}
