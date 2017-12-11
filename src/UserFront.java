import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserFront extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	Vector<Vector> rowData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserFront frame = new UserFront();
					frame.setVisible(true);
					JOptionPane.showMessageDialog(null,"Welcome! \nHave a great time!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserFront() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 572);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRead = new JButton("Read");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				Voice v=VoiceManager.getInstance().getVoice("kevin16");
				v.allocate();
				try
				{
					for(int i=0;i<rowData.size();i++)
					{
						v.speak(rowData.get(i).get(0).toString());
					}
					if(rowData.isEmpty())
				    {
				    	v.speak("Sorry! No results found");
				    }
				}
				catch(NullPointerException e)
				{
					v.speak("Sorry! No results found");
				}
			}
		});
		btnRead.setBounds(704, 154, 89, 26);
		contentPane.add(btnRead);
		
		JLabel lblEnterTheSearch = new JLabel("Enter the search key");
		lblEnterTheSearch.setForeground(new Color(139, 0, 0));
		lblEnterTheSearch.setFont(new Font("Agency FB", Font.PLAIN, 30));
		lblEnterTheSearch.setBounds(22, 136, 249, 46);
		contentPane.add(lblEnterTheSearch);
		
		textField = new JTextField();
		textField.setBounds(269, 155, 292, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblResults = new JLabel("Results");
		lblResults.setForeground(new Color(139, 0, 0));
		lblResults.setFont(new Font("Agency FB", Font.PLAIN, 30));
		lblResults.setBounds(22, 215, 136, 36);
		contentPane.add(lblResults);
		
		JButton btnGo = new JButton("Go");
		btnGo.setBounds(622, 154, 72, 26);
		contentPane.add(btnGo);
		
		JLabel lblClickOnThe = new JLabel("Click on the link to open in a browser");
		lblClickOnThe.setBounds(279, 513, 266, 14);
		contentPane.add(lblClickOnThe);
		
/*		JLabel lblYouAreAccessing = new JLabel("You are accessing results from the index on the admin's database");
		lblYouAreAccessing.setBounds(205, 11, 436, 14);
		contentPane.add(lblYouAreAccessing);*/
		
		table = new JTable();
		
		table.setCellSelectionEnabled(true);
		table.setBounds(34, 104, 696, 268);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 251, 787, 254);
		contentPane.add(scrollPane);
		
		JLabel lblGenericNameFor = new JLabel("");
		lblGenericNameFor.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\ManActual.jpg"));
		lblGenericNameFor.setBounds(497, 51, 300, 434);
		contentPane.add(lblGenericNameFor);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\logoSmall.jpg"));
		lblLogo.setBounds(338, 0, 138, 132);
		contentPane.add(lblLogo);
		
		JButton btnFeedback = new JButton("Give Feedback");
		btnFeedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Feedback.main(null);
			}
		});
		btnFeedback.setBounds(622, 509, 171, 23);
		contentPane.add(btnFeedback);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LoginPage lp = new LoginPage();
				lp.main(null);
			}
		});
		btnLogOut.setBounds(10, 509, 89, 23);
		contentPane.add(btnLogOut);
		
		
		//contentPane.add(table);
	
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try 
				{
					if(textField.getText().equals(""))
					{
					//	System.out.println("Entering if");
						JOptionPane.showMessageDialog(null, "Please enter a search key");
					}
					else
					{
						CrawlInterface ci = FactoryClass.getInstance();
						rowData = ci.searchResult(textField.getText());
						if(!rowData.isEmpty())
						{
							Vector<String> colNames=new Vector<>();
							colNames.add("Title");
							colNames.add("Url");
							table.setModel(new DefaultTableModel(rowData,colNames) {
								@Override
							    public boolean isCellEditable(int row, int column) {
							       //all cells false
							       return false;
								}
							}
							);
							// Command to create an external process
							// String command = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
					        // Running the above command
							
							// Runtime.getRuntime().exec(new String[] { "Chrome", "http://www.google.com" });
							
							table.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent arg0) 
								{
									int index=table.getSelectedRow();
									System.out.println("Index = "+index);
									System.out.println(rowData.get(index).get(1));
								//	String selectedURL=rowData.get(index).get(1);
									try 
									{
										Runtime.getRuntime().exec(new String[] { "cmd", "/c","start chrome "+rowData.get(index).get(1) });
								//		FactoryClass.getInstance().userClickUpdate(rowData.get(index).get(1).toString());
									} 
									catch (IOException e) 
									{
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});

					        
					//		System.out.println(rowData.toString());
						}	
						else
						{
							JOptionPane.showMessageDialog(null, "Sorry no results found");
						}
					}
				}
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}
}
