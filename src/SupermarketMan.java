import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.*;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

//import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class SupermarketMan {

	public JFrame frame;
	private JTextField pcode;
	private JTextField pname;
	private JTextField pprice;
	private JTextField pqty;
	private JTextField pdet;
	private JTextField pcodes;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupermarketMan window = new SupermarketMan();
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
	public SupermarketMan() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst,result;
	ResultSet rs;
	private JTable table;
	
	public void Connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/products_data","root","");
		}
		catch (ClassNotFoundException ex)
		{
			
		}
		catch (SQLException ex)
		{
			
		}
	}
	
	  public void table_load()
	    {
		  try
		  {
			  pst = con.prepareStatement("select * from product");
			  rs = pst.executeQuery();
			  table.setModel(DbUtils.resultSetToTableModel(rs));
		  }
		  catch (SQLException e)
		  {
			  e.printStackTrace();
		  }
	    }

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(BillingWindow.class.getResource("/com/test/icon/icons8-market-64.png")));
		frame.setTitle("SupermaeketMan Product Management");
		frame.setBounds(0, 0, 1500, 800);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		leftPanel.setBounds(10, 10, 110, 743);
		frame.getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		JButton billing_Btn = new JButton("Billing");
		billing_Btn.setIcon(new ImageIcon(SupermarketMan.class.getResource("/com/test/icon/icons8-billing-machine-50.png")));
		billing_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BillingWindow billingwindow = new BillingWindow();
				billingwindow.billingWindow.setVisible(true);
				frame.dispose();
			}
		});
		billing_Btn.setBounds(0, 0, 110, 355);
		billing_Btn.setVerticalTextPosition(JLabel.BOTTOM);
		billing_Btn.setHorizontalTextPosition(JLabel.CENTER);
		leftPanel.add(billing_Btn);
		
		JButton products_Btn = new JButton("Products");
		products_Btn.setIcon(new ImageIcon(SupermarketMan.class.getResource("/com/test/icon/icons8-fast-moving-consumer-goods-50.png")));
		products_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SAME WINDOW SO DO NOTHING
			}
		});
		products_Btn.setBounds(0, 388, 110, 355);
		products_Btn.setVerticalTextPosition(JLabel.BOTTOM);
		products_Btn.setHorizontalTextPosition(JLabel.CENTER);
		leftPanel.add(products_Btn);
		
		JPanel rightPanel2 = new JPanel();
		rightPanel2.setBounds(130, 10, 1346, 743);
		frame.getContentPane().add(rightPanel2);
		rightPanel2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("   Product Management");
		lblNewLabel_1.setBounds(10, 10, 1346, 45);
		rightPanel2.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 65, 613, 550);
		rightPanel2.add(panel_1);
		panel_1.setBorder(new TitledBorder(null, "Product Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Product Name :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(23, 133, 201, 23);
		panel_1.add(lblNewLabel);
		
		JLabel lblProductPrice = new JLabel("Product Qty :");
		lblProductPrice.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblProductPrice.setBounds(23, 276, 201, 23);
		panel_1.add(lblProductPrice);
		
		JLabel lblProductPrice_1 = new JLabel("Product Price :");
		lblProductPrice_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblProductPrice_1.setBounds(23, 204, 201, 23);
		panel_1.add(lblProductPrice_1);
		
		JLabel lblProductPrice_2 = new JLabel("Product Details :");
		lblProductPrice_2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblProductPrice_2.setBounds(23, 345, 201, 23);
		panel_1.add(lblProductPrice_2);
		
		JLabel lblProductCode = new JLabel("Product Code :");
		lblProductCode.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblProductCode.setBounds(23, 69, 201, 23);
		panel_1.add(lblProductCode);
		
		pcode = new JTextField();
		pcode.setBounds(226, 69, 330, 28);
		panel_1.add(pcode);
		pcode.setColumns(10);
		
		pname = new JTextField();
		pname.setColumns(10);
		pname.setBounds(226, 133, 330, 28);
		panel_1.add(pname);
		
		pprice = new JTextField();
		pprice.setColumns(10);
		pprice.setBounds(226, 204, 330, 28);
		panel_1.add(pprice);
		
		pqty = new JTextField();
		pqty.setColumns(10);
		pqty.setBounds(226, 276, 330, 28);
		panel_1.add(pqty);
		
		pdet = new JTextField();
		pdet.setColumns(10);
		pdet.setBounds(226, 345, 330, 28);
		panel_1.add(pdet);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String productcode,productname,productprice,productqty,productdet;
				
				productcode = pcode.getText();
				productname = pname.getText();
				productprice = pprice.getText();
				productqty = pqty.getText();
				productdet = pdet.getText();
				
				try {
					pst = con.prepareStatement("insert into product(name,quantity,price,details)values(?,?,?,?)");
					pst.setString(1, productname);
					pst.setString(2, productqty);
					pst.setString(3, productprice);
					pst.setString(4, productdet);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added to Database");
					table_load();
					          
					pcode.setText("");
					pname.setText("");
					pprice.setText("");
					pqty.setText("");
					pdet.setText("");
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(22, 426, 245, 100);
		panel_1.add(btnNewButton);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(347, 426, 245, 100);
		panel_1.add(btnClear);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 625, 613, 108);
		rightPanel2.add(panel_2);
		panel_2.setBorder(new TitledBorder(null, "Search Product", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setLayout(null);
		
		JLabel lblProductCode_1 = new JLabel("Product Code :");
		lblProductCode_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblProductCode_1.setBounds(22, 31, 134, 23);
		panel_2.add(lblProductCode_1);
		
		pcodes = new JTextField();
		pcodes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String id = pcodes.getText();
		 
		                pst = con.prepareStatement("select id,name,price,quantity,details from product where id = ?");
		                pst.setString(1, id);
		                ResultSet rs = pst.executeQuery();
		 
		            if(rs.next()==true)
		            {
		            	String i = rs.getString(1);
		                String name = rs.getString(2);
		                String price = rs.getString(3);
		                String qty = rs.getString(4);
		                String details = rs.getString(5);
		                
		                pcode.setText(i);
		                pname.setText(name);
		                pprice.setText(price);
		                pqty.setText(qty);
		                pdet.setText(details);
		                
		            }  
		            else
		            {
		            	pcode.setText("");
		                pname.setText("");
		                pprice.setText("");
		                pqty.setText("");
		                pdet.setText("");
		            }
		            
		 
		 
		        }
		catch (SQLException ex) {
		          
		        }
				
			}
		});
		pcodes.setColumns(10);
		pcodes.setBounds(206, 32, 197, 28);
		panel_2.add(pcodes);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(633, 65, 711, 496);
		rightPanel2.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSearch = new JButton("Update");
		btnSearch.setBounds(633, 609, 320, 99);
		rightPanel2.add(btnSearch);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(1016, 609, 320, 99);
		rightPanel2.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prodcodes;
				
				prodcodes = pcodes.getText();
				
				try {
					pst = con.prepareStatement("delete from product where id=?");
					pst.setString(1, prodcodes);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted from Database");
					table_load();
					          
					pcode.setText("");
					pname.setText("");
					pprice.setText("");
					pqty.setText("");
					pdet.setText("");
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String productcode,productname,productprice,productqty,productdet,prodcodes;
				
				productcode = pcode.getText();
				productname = pname.getText();
				productprice = pprice.getText();
				productqty = pqty.getText();
				productdet = pdet.getText();
				prodcodes = pcodes.getText();
				
				try {
					pst = con.prepareStatement("update product set name=?,price=?,quantity=?,details=? where id=?");
					pst.setString(1, productname);
					pst.setString(2, productprice);
					pst.setString(3, productqty);
					pst.setString(4, productdet);
					pst.setString(5, prodcodes);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated to Database");
					table_load();
					          
					pcode.setText("");
					pname.setText("");
					pprice.setText("");
					pqty.setText("");
					pdet.setText("");
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}
}
