import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.text.MessageFormat;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.sql.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class BillingWindow extends javax.swing.JFrame implements Runnable, ThreadFactory {

	public JFrame billingWindow;
	private JTable billtable;
	private JTextField tf_Pcode;
	private JTextField tf_Productname;
	private JTextField tf_Qty;
	private JTextField tf_Price;
	
	
	private Webcam webcam = null;
	
	private Executor executor = Executors.newSingleThreadExecutor(this);
	int index = 1;
	int availableQuantity = 1;
	int totQty = 0;
	int totPrice = 0;
	Boolean autoCam = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillingWindow window = new BillingWindow();
					window.billingWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BillingWindow() {
		super();
		Connect();
		setTitle("Supermarket Management");
		initialize();
		
		executor.execute(this);
	}
	
	Connection con;
	PreparedStatement pst,result;
	ResultSet rs;
	
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
	
	public void run() {
        do {
            if (autoCam == true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Result result = null;
                BufferedImage image = null;

                if (webcam.isOpen()) {

                    if ((image = webcam.getImage()) == null) {
                        continue;
                    }

                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                    try {
                        result = new MultiFormatReader().decode(bitmap);
                    } catch (NotFoundException e) {
                        // fall thru, it means there is no QR code in image
                        System.out.println("FALLTHROUGH ");

                    }
                }

                if (result != null) {
                	tf_Pcode.setText(result.getText());
                    System.out.println("Output is :" + result.getText());
                    //UPDATE PRODUCT DETAILS
                    updateDetails();
                }
            }
        } while (true);
    }
	
	@Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "example-runner");
        t.setDaemon(true);
        return t;
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		billingWindow = new JFrame();
		billingWindow.setIconImage(Toolkit.getDefaultToolkit().getImage(BillingWindow.class.getResource("/com/test/icon/icons8-market-64.png")));
		billingWindow.setTitle("SupermaeketMan Billing");
		billingWindow.setBounds(0, 0, 1500, 800);
		//frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		billingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		billingWindow.getContentPane().setLayout(null);
		
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		leftPanel.setBounds(10, 10, 110, 743);
		billingWindow.getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		rightPanel.setBounds(130, 10, 1351, 743);
		billingWindow.getContentPane().add(rightPanel);
		rightPanel.setLayout(null);
		
		JButton billing_Btn = new JButton("Billing");
		billing_Btn.setRequestFocusEnabled(false);
		billing_Btn.setIcon(new ImageIcon(BillingWindow.class.getResource("/com/test/icon/icons8-billing-machine-50.png")));
		billing_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SAME WINDOW SO DO NOTHING
			}
		});
		billing_Btn.setBounds(0, 0, 110, 355);
		billing_Btn.setVerticalTextPosition(JLabel.BOTTOM);
		billing_Btn.setHorizontalTextPosition(JLabel.CENTER);
		leftPanel.add(billing_Btn);
		
		JButton products_Btn = new JButton("Products");
		products_Btn.setIcon(new ImageIcon(BillingWindow.class.getResource("/com/test/icon/icons8-fast-moving-consumer-goods-50.png")));
		products_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webcam.close();
				SupermarketMan marketman = new SupermarketMan();
				marketman.frame.setVisible(true);
				//billingWindow.hide();
				billingWindow.dispose();
				
			}
		});
		products_Btn.setBounds(0, 378, 110, 355);
		products_Btn.setVerticalTextPosition(JLabel.BOTTOM);
		products_Btn.setHorizontalTextPosition(JLabel.CENTER);
		leftPanel.add(products_Btn);
		
		Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);
		
		JPanel camPanel = new WebcamPanel(webcam);
		camPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		camPanel.setBounds(10, 10, 521, 723);
		rightPanel.add(camPanel);
		camPanel.setLayout(null);
		
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new TitledBorder(null, "Product Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		dataPanel.setBounds(541, 10, 337, 723);
		rightPanel.add(dataPanel);
		dataPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Product Code");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 29, 133, 54);
		dataPanel.add(lblNewLabel);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblProductName.setBounds(10, 93, 133, 54);
		dataPanel.add(lblProductName);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblQuantity.setBounds(10, 157, 133, 54);
		dataPanel.add(lblQuantity);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPrice.setBounds(10, 221, 133, 54);
		dataPanel.add(lblPrice);
		
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf_Pcode.setText("");
	            tf_Productname.setText("");
	            tf_Price.setText("");
	            tf_Qty.setText("");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnClear.setBounds(10, 349, 317, 54);
		dataPanel.add(btnClear);
		
		JButton btnAutocamMode = new JButton("AutoCam mode : ON");
		btnAutocamMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (autoCam == true) {
		            autoCam = false;
		            System.out.println("autoCam is false");
		            btnAutocamMode.setText("AutoCam Mode : OFF");
		        } else {
		            autoCam = true;
		            System.out.println("autoCam is true");
		            btnAutocamMode.setText("AutoCam Mode : ON");
		        }
			}
		});
		btnAutocamMode.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAutocamMode.setBounds(10, 413, 317, 54);
		dataPanel.add(btnAutocamMode);
		
		tf_Pcode = new JTextField();
		tf_Pcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateDetails();
				
			}
		});
		tf_Pcode.setBounds(153, 50, 174, 19);
		dataPanel.add(tf_Pcode);
		tf_Pcode.setColumns(10);
		
		tf_Productname = new JTextField();
		tf_Productname.setColumns(10);
		tf_Productname.setBounds(153, 114, 174, 19);
		dataPanel.add(tf_Productname);
		
		tf_Qty = new JTextField();
		tf_Qty.setColumns(10);
		tf_Qty.setBounds(153, 178, 174, 19);
		dataPanel.add(tf_Qty);
		
		tf_Price = new JTextField();
		tf_Price.setColumns(10);
		tf_Price.setBounds(153, 242, 174, 19);
		dataPanel.add(tf_Price);
		
		JPanel listPanel = new JPanel();
		listPanel.setBorder(new TitledBorder(null, "Bill Generated", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listPanel.setBounds(888, 10, 453, 723);
		rightPanel.add(listPanel);
		listPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 433, 401);
		listPanel.add(scrollPane);
		
		billtable = new JTable();
		billtable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No.", "PCode", "Product Name", "Qty.", "Price"
			}
		));
		billtable.getColumnModel().getColumn(0).setPreferredWidth(15);
		billtable.getColumnModel().getColumn(1).setPreferredWidth(40);
		billtable.getColumnModel().getColumn(2).setPreferredWidth(80);
		billtable.getColumnModel().getColumn(3).setPreferredWidth(15);
		scrollPane.setViewportView(billtable);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Bill Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 432, 433, 281);
		listPanel.add(panel);
		panel.setLayout(null);
		
		JButton btnClearBill = new JButton("Clear Bill");
		btnClearBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) billtable.getModel();
				model.setRowCount(0);
				totQty=0;
				totPrice=0;
				//lblTotalItems.setText("Total Items : "+String.valueOf(totQty));
		        //lblTotalBill.setText("Total Price : "+String.valueOf(totPrice));
			}
		});
		btnClearBill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnClearBill.setBounds(10, 138, 413, 54);
		panel.add(btnClearBill);
		
		JButton btnMakeBill = new JButton("MAKE BILL");
		btnMakeBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Print Bill
				try {
		            MessageFormat header = new MessageFormat("TERNA SUPERMARKET");
		            MessageFormat footer = new MessageFormat("Total Quantity :"+totQty+"      "+"Total Amount"+totPrice);

		            billtable.print(JTable.PrintMode.NORMAL, header, footer);
		        } catch (PrinterException ex) {
		            JOptionPane.showMessageDialog(null, "Error printer \n" + ex.toString());
		        }
			}
		});
		btnMakeBill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnMakeBill.setBounds(10, 202, 413, 54);
		panel.add(btnMakeBill);
		
		JLabel lblTotalItems = new JLabel("Total Items :");
		lblTotalItems.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTotalItems.setBounds(10, 10, 382, 54);
		panel.add(lblTotalItems);
		
		JLabel lblTotalBill = new JLabel("Total Bill :");
		lblTotalBill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTotalBill.setBounds(10, 74, 382, 54);
		panel.add(lblTotalBill);
		
		JButton btnNewButton = new JButton("Add to List");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ADDING ITEM TO LIST
				System.out.print("CLICKED ADD BUTTON");
				if(availableQuantity > Integer.parseInt(tf_Qty.getText())) {
					DefaultTableModel model = (DefaultTableModel)billtable.getModel();
			        model.addRow(new Object[] { index++, tf_Pcode.getText(), tf_Productname.getText(), tf_Qty.getText(), Integer.valueOf(tf_Price.getText())*Integer.valueOf(tf_Qty.getText())});
			        totQty += Integer.valueOf(tf_Qty.getText());
			        totPrice = totPrice + Integer.valueOf(tf_Price.getText())*Integer.valueOf(tf_Qty.getText());
			        lblTotalItems.setText("Total Items : "+String.valueOf(totQty));
			        lblTotalBill.setText("Total Price : "+String.valueOf(totPrice));
		          //Update db
					try {
						pst = con.prepareStatement("update product set quantity=? where id=?");
						pst.setString(1, Integer.toString(availableQuantity - Integer.parseInt(tf_Qty.getText())));
						pst.setString(2, Integer.toString(Integer.parseInt(tf_Pcode.getText())));
						pst.executeUpdate();
						          
						tf_Pcode.setText("");
				        tf_Productname.setText("");
			            tf_Price.setText("");
			            tf_Qty.setText("");
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "The specified quantity is not available in the store");
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(10, 285, 317, 54);
		dataPanel.add(btnNewButton);
		
	}
	
	public void updateDetails()
	{
		try {
	          
        	String id = tf_Pcode.getText();

            pst = con.prepareStatement("select id,name,price,quantity,details from product where id = ?");
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();

        if(rs.next()==true)
        {
        	availableQuantity = Integer.parseInt(rs.getString(3));
        	String i = rs.getString(1);
            String name = rs.getString(2);
            String price = rs.getString(3);
            String qty = rs.getString(4);
            String details = rs.getString(5);
            
            tf_Pcode.setText(i);
            tf_Productname.setText(name);
            tf_Price.setText(price);
            tf_Qty.setText("1");
            //tf_Detail.setText(details);
            
        }  
        else
        {
        	String i = rs.getString(1);
        	tf_Pcode.setText(i);
            tf_Productname.setText("");
            tf_Price.setText("");
            tf_Qty.setText("");
        }
    }
catch (SQLException ex) {
      
    }
	}
}
