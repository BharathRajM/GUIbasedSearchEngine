import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField usernametf;
	private JTextField passwordtf;
	private JTextField repasswordtf;
	private JPasswordField passwordField;
	private JPasswordField repasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 340);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Username");
		lblName.setForeground(new Color(139, 0, 0));
		lblName.setFont(new Font("Agency FB", Font.PLAIN, 22));
		lblName.setBounds(74, 173, 119, 20);
		contentPane.add(lblName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(139, 0, 0));
		lblPassword.setFont(new Font("Agency FB", Font.PLAIN, 22));
		lblPassword.setBounds(74, 201, 119, 17);
		contentPane.add(lblPassword);
		
		JLabel lblHelloUser = new JLabel("We are excited!");
		lblHelloUser.setForeground(new Color(139, 0, 0));
		lblHelloUser.setFont(new Font("Agency FB", Font.PLAIN, 25));
		lblHelloUser.setBounds(156, 121, 163, 38);
		contentPane.add(lblHelloUser);
		
		JLabel lblRetypePassword = new JLabel("Re-Type Password");
		lblRetypePassword.setForeground(new Color(139, 0, 0));
		lblRetypePassword.setFont(new Font("Agency FB", Font.PLAIN, 22));
		lblRetypePassword.setBounds(74, 229, 151, 23);
		contentPane.add(lblRetypePassword);
		
		usernametf = new JTextField();
		usernametf.setBounds(235, 178, 86, 20);
		contentPane.add(usernametf);
		usernametf.setColumns(10);
				
		passwordField = new JPasswordField();
		passwordField.setBounds(235, 206, 86, 20);
		contentPane.add(passwordField);
		
		repasswordField = new JPasswordField();
		repasswordField.setBounds(235, 235, 86, 20);
		contentPane.add(repasswordField);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(usernametf.getText()==null || passwordField.getText()==null)
				{
					JOptionPane.showMessageDialog(null, "Please enter the required details");
				}
				else
				{
					if( passwordField.getText().equals(repasswordField.getText()))
					{
						try 
						{
							String status = FactoryClass.getInstance().signUp(usernametf.getText(), passwordField.getText());
							if(status.equals("User Created"))
							{
								JOptionPane.showMessageDialog(null, "User successfully created");
								dispose();
							}
							else if(status.equals("User already exists"))
							{
								JOptionPane.showMessageDialog(null, "Username already exists!");
							}
						}	 
						catch (SQLException e1) 
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please check your password");
					}
				}
			}
		});
		btnCreate.setBounds(235, 266, 86, 23);
		contentPane.add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
		btnCancel.setBounds(74, 266, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\logoSmall.jpg"));
		label.setBounds(146, 0, 151, 134);
		contentPane.add(label);

	}
}
