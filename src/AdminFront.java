import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;

public class AdminFront extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JButton btnViewFeedbacks;
	private JButton btnViewSearchHistory;
	private JButton btnLogOut;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFront frame = new AdminFront();
					frame.setVisible(true);
					JOptionPane.showMessageDialog(null,"Welcome Admin!\nHave a great time!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminFront() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 590);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGiveTheUrl = new JLabel("Give the URL to start crawling from ");
		lblGiveTheUrl.setForeground(new Color(139, 0, 0));
		lblGiveTheUrl.setFont(new Font("Agency FB", Font.PLAIN, 30));
		lblGiveTheUrl.setBounds(261, 125, 375, 35);
		contentPane.add(lblGiveTheUrl);
		
		textField = new JTextField();
		textField.setBounds(230, 171, 375, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnGo = new JButton("Go");
		table = new JTable();
		table.setEnabled(false);
		table.setBounds(40, 91, 519, 301);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 252, 792, 264);
		contentPane.add(scrollPane);
		
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Seems like you have not entered a URL :)");
				}
				else
				{ 	CrawlInterface ci = FactoryClass.getInstance();
					Vector<Vector> rowData;
					try {
						rowData = ci.crawlStart(textField.getText()); 
						Vector<String> colNames = ci.getColNames();
						table.setModel(new DefaultTableModel(rowData,colNames));
						System.out.println(rowData.toString());
					}
					catch (MalformedURLException |NullPointerException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Seems like the URL is incorrect,check again! :)");
					}
				}	
			}
		});
		btnGo.setBounds(381, 218, 52, 23);
		contentPane.add(btnGo);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\logoSmall.jpg"));
		lblNewLabel.setBounds(339, 0, 144, 132);
		contentPane.add(lblNewLabel);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\spider.jpg"));
		label.setBounds(10, 11, 288, 230);
		contentPane.add(label);
		
		btnViewFeedbacks = new JButton("View Feedbacks");
		btnViewFeedbacks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewFeedback.main(null);
			}
		});
		btnViewFeedbacks.setBounds(647, 527, 155, 23);
		contentPane.add(btnViewFeedbacks);
		
		btnViewSearchHistory = new JButton("View Search History of users");
		btnViewSearchHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				CrawlInterface ci = FactoryClass.getInstance();
				Vector<Vector> rowData = ci.viewSearchHistory();
				Vector<String> colNames = ci.getSearchHistoryColNames();
				table.setModel(new DefaultTableModel(rowData,colNames) {
					@Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
					}
				}
				);
				System.out.println(rowData.toString());
			}
		});
		btnViewSearchHistory.setBounds(299, 527, 224, 23);
		contentPane.add(btnViewSearchHistory);
		
		btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LoginPage lp = new LoginPage();
				lp.main(null);
			}
		});
		btnLogOut.setBounds(10, 527, 89, 23);
		contentPane.add(btnLogOut);
	}
}
