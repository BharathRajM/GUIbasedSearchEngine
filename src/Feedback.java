import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Feedback extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Feedback frame = new Feedback();
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
	public Feedback() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 405);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDontWorryYoull = new JLabel("Your feedback will remain anonymous ");
		lblDontWorryYoull.setForeground(new Color(139, 0, 0));
		lblDontWorryYoull.setFont(new Font("Agency FB", Font.PLAIN, 25));
		lblDontWorryYoull.setBounds(68, 337, 356, 30);
		contentPane.add(lblDontWorryYoull);
		
		JLabel lblNewLabel = new JLabel("Your Feedback is very helpful for us!");
		lblNewLabel.setFont(new Font("Agency FB", Font.PLAIN, 25));
		lblNewLabel.setForeground(new Color(139, 0, 0));
		lblNewLabel.setBounds(77, 135, 289, 39);
		contentPane.add(lblNewLabel);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(220, 220, 220));
		textArea.setBounds(10, 185, 414, 121);
		contentPane.add(textArea);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(!textArea.getText().isEmpty())
				{
					FactoryClass.getInstance().giveFeedback(textArea.getText());
					JOptionPane.showMessageDialog(null,"We received your feedback. \n Thank you");
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please write something to submit :)");
				}
			}
		});
		btnSubmit.setBounds(237, 312, 89, 23);
		contentPane.add(btnSubmit);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\logoSmall.jpg"));
		label.setBounds(143, 11, 147, 129);
		contentPane.add(label);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnCancel.setBounds(101, 312, 89, 23);
		contentPane.add(btnCancel);
	}
}
