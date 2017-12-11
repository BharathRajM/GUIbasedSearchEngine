import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
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
	public LoginPage() {
		setTitle("Login");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 565);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				SignUp.main(null);
			}
		});
		btnSignUp.setBounds(380, 416, 89, 23);
		contentPane.add(btnSignUp);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setForeground(new Color(210, 105, 30));
		lblLogin.setFont(new Font("Agency FB", Font.BOLD, 35));
		lblLogin.setBounds(380, 108, 89, 43);
		contentPane.add(lblLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(139, 0, 0));
		lblUsername.setFont(new Font("Agency FB", Font.PLAIN, 30));
		lblUsername.setBounds(130, 202, 139, 30);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Agency FB", Font.PLAIN, 30));
		lblPassword.setForeground(new Color(139, 0, 0));
		lblPassword.setBounds(134, 308, 135, 30);
		contentPane.add(lblPassword);

		
		textField = new JTextField();
		textField.setBounds(380, 212, 206, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(380, 318, 206, 20);
		contentPane.add(passwordField);
		
		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					boolean check=FactoryClass.getInstance().checkUser(textField.getText(), passwordField.getText());
					if(check)
					{
					/*	System.out.println("username="+textField.getText());
						System.out.println("password="+passwordField.getText());
						System.out.println("Successful");*/
						System.out.println("inside if Check value:"+check);
						dispose();
					//	setEnabled(false);
					}
					else
					{
						System.out.println("inside else Check value:"+check);
						JOptionPane.showMessageDialog(null,"Unsuccessful login! \n Please try again");
					}
					System.out.println("end of try Check value:"+check);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnGo.setBounds(380, 382, 89, 23);
		contentPane.add(btnGo);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\logoSmall.jpg"));
		lblNewLabel.setBounds(647, 382, 144, 134);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\Logo.jpg"));
		label.setBounds(167, 0, 480, 527);
		contentPane.add(label);
	}
}
