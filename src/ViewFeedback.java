import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

public class ViewFeedback extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewFeedback frame = new ViewFeedback();
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
	public ViewFeedback() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 463);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		table = new JTable();
		table.setEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setBounds(0, 0, 1, 1);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 148, 824, 266);
		contentPane.add(scrollPane);
		
		
		JLabel lblFeedbacks = new JLabel("Feedbacks for us");
		lblFeedbacks.setForeground(new Color(139, 0, 0));
		lblFeedbacks.setFont(new Font("Agency FB", Font.PLAIN, 25));
		lblFeedbacks.setBounds(367, 114, 169, 31);
		contentPane.add(lblFeedbacks);
		
		JButton btnViewFeedbacks = new JButton("View Feedbacks");
		btnViewFeedbacks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//Get the complete feedback table
				CrawlInterface ci = FactoryClass.getInstance();
				Vector<Vector> rowData = ci.viewFeedback();
				Vector<String> colNames=new Vector<>();
				colNames.add("Sl no.");
				colNames.add("Feedback");
				table.setModel(new DefaultTableModel(rowData,colNames) {
					@Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
					}
				}
				);
			}
		});
		btnViewFeedbacks.setBounds(608, 122, 159, 23);
		contentPane.add(btnViewFeedbacks);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(10, 122, 89, 23);
		contentPane.add(btnBack);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Ishu\\Documents\\EXAMPLE\\GUICRAWLER\\logoSmall.jpg"));
		lblNewLabel.setBounds(357, 0, 138, 133);
		contentPane.add(lblNewLabel);
	}
}
