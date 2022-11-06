import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage {

	private JFrame frame;
	private JPasswordField passname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}
	
	public void Connect()
	{
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(BillingWindow.class.getResource("/com/test/icon/icons8-market-64.png")));
		frame.setTitle("SupermarketMan Login Page");
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.setBounds(100, 100, 648, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SupermarketMan");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("neueRadial-B-Black", Font.PLAIN, 43));
		lblNewLabel.setBounds(10, 24, 614, 67);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblSupermarketManagementSystem = new JLabel("Supermarket Management System");
		lblSupermarketManagementSystem.setHorizontalAlignment(SwingConstants.CENTER);
		lblSupermarketManagementSystem.setFont(new Font("neueRadial-B-Black", Font.PLAIN, 18));
		lblSupermarketManagementSystem.setBounds(10, 83, 614, 37);
		frame.getContentPane().add(lblSupermarketManagementSystem);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 130, 614, 323);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblSupermarketManagementSystem_1_1 = new JLabel("Username :");
		lblSupermarketManagementSystem_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblSupermarketManagementSystem_1_1.setFont(new Font("neueRadial-B-Book", Font.PLAIN, 18));
		lblSupermarketManagementSystem_1_1.setBounds(25, 96, 128, 22);
		panel.add(lblSupermarketManagementSystem_1_1);
		
		JLabel lblSupermarketManagementSystem_1_1_1 = new JLabel("Password :");
		lblSupermarketManagementSystem_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblSupermarketManagementSystem_1_1_1.setFont(new Font("neueRadial-B-Book", Font.PLAIN, 18));
		lblSupermarketManagementSystem_1_1_1.setBounds(25, 173, 128, 22);
		panel.add(lblSupermarketManagementSystem_1_1_1);
		
		passname = new JPasswordField();
		passname.setBounds(180, 169, 304, 30);
		panel.add(passname);
		
		JTextPane username = new JTextPane();
		username.setBounds(180, 88, 304, 30);
		panel.add(username);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/products_data","root","");
					String uname = username.getText();
					String pass = passname.getText();
					
					Statement stm = con.createStatement();
					String sql = "select * from login where name='"+uname+"' and password='"+pass+"'";
					ResultSet rs = stm.executeQuery(sql);
					
					if(rs.next())
					{
						BillingWindow billingwindow = new BillingWindow();
						billingwindow.billingWindow.setVisible(true);
						frame.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(loginBtn, "Invalid Credentials");
					}
					
				}
				catch (ClassNotFoundException ex)
				{
				}
				catch (SQLException ex)
				{
				}
			}
		});
		loginBtn.setBackground(UIManager.getColor("Button.light"));
		loginBtn.setFont(new Font("neueRadial-B-Regular", Font.PLAIN, 16));
		loginBtn.setBounds(69, 241, 161, 47);
		panel.add(loginBtn);
		
		JButton resetBtn = new JButton("Reset");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText("");
				passname.setText("");
			}
		});
		resetBtn.setFont(new Font("neueRadial-B-Regular", Font.PLAIN, 16));
		resetBtn.setBackground(UIManager.getColor("Button.light"));
		resetBtn.setBounds(386, 241, 161, 47);
		panel.add(resetBtn);
	}
}
