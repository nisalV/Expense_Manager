/**
 * Initial user interface.
 */

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class AccountInfo {

	private JFrame frame;
	private JLabel clock;
	private JLayeredPane layeredPane;
	private JPanel personalCategory, businessCategory, mainPanel;
	private JTextPane personalCredit, businessCredit, personalDebit, businessDebit, personalBalance, businessBalance;
	private JTextField businessCategoryText, personalCategoryText;
	private ArrayList<String> businessArray = new ArrayList<>();
	private ArrayList<String> personalArray = new ArrayList<>();
	private JRadioButton businessIncome, businessExpense, personalIncome, personalExpense;
	private int j = 0;
	private int l = 0;
	private String BusinessLine, PersonalLine;
	private JTable businesstable, personaltable;
	private ButtonGroup businessType, personalType;
	private String fileArray[] = {"CategoryData//Personal//Expense", "CategoryData//Business//Income","CategoryData//Personal//Income", "CategoryData//Business//Expense", "temp", "total//Personal//Expense", "total//Business//Income", "total//Personal//Income", "total//Business//Expense"};
	public String businessCategorySet[], privateCategorySet[];
	public String businessCategorySetPart1, businessCategorySetPart2, privateCategorySetPart1, privateCategorySetPart2;
	public String bisCat[];
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountInfo window = new AccountInfo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Switch panels.
	 */
	public void switchPanels(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	/**
	 * Create the application.
	 */
	public AccountInfo() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
		for (int n = 0; n < fileArray.length; n++) {
			File firstFiles = new File("src//" + fileArray[n]);
		    if(!firstFiles.exists()) { 
		    	firstFiles.mkdirs();
		    } else {
		    	//
		    }
		}
		
		PrintWriter writerBusiness = null;
		PrintWriter writerPersonal = null;
		
		File businessCategoryTxt = new File("src//businessCategory.txt");
		File personalCategoryTxt = new File("src//personalCategory.txt");
		
		if (!businessCategoryTxt.exists()) { 
			try {
				writerBusiness = new PrintWriter("src//businessCategory.txt", "UTF-8");
				writerBusiness.close();
			} catch (IOException ex) {
			} finally {
			   try {writerBusiness.close();} catch (Exception ex) {}
			}
		}
		if (!personalCategoryTxt.exists()) { 
			try {
				writerPersonal = new PrintWriter("src//personalCategory.txt", "UTF-8");
				writerPersonal.close();
			} catch (IOException ex) {
			} finally {
			   try {writerPersonal.close();} catch (Exception ex) {}
			}
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 825, 448);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel userName = new JLabel("Expenses Management System");
		userName.setFont(new Font("Tahoma", Font.BOLD, 19));
		userName.setBounds(23, 10, 339, 29);
		frame.getContentPane().add(userName);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(10, 56, 791, 345);
		frame.getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		/**
		 * MainPanel.
		 */
		mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane.add(mainPanel, "name_15752115118600");
		mainPanel.setLayout(null);
		
		JButton personalUpdate = new JButton("Update");
		personalUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton businessUpdate = new JButton("Update");
		businessUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		/**
		 * Personal Category.
		 */
		personalCategory = new JPanel();
		personalCategory.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane.add(personalCategory, "name_15760297141300");
		personalCategory.setLayout(null);
		
		personalType = new ButtonGroup();
		
		personalIncome = new JRadioButton("Income");
		personalIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalIncome.setBounds(451, 10, 85, 21);
		personalCategory.add(personalIncome);
		
		personalExpense = new JRadioButton("Expense");
		personalExpense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalExpense.setBounds(538, 10, 91, 21);
		personalCategory.add(personalExpense);
		
		personalType.add(personalExpense);
		personalType.add(personalIncome);
		
		JScrollPane personalScroll = new JScrollPane();
		personalScroll.setBounds(26, 48, 147, 287);
		
		personaltable = new JTable();
		personaltable.setColumnSelectionAllowed(true);
		personaltable.setCellSelectionEnabled(true);
		
		DefaultTableModel personalTableModel;
		personalTableModel = new DefaultTableModel(1,1); 
		personaltable.setModel(personalTableModel);
	    
		personaltable.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row1 = personaltable.rowAtPoint(evt.getPoint());
	            int col1 = personaltable.columnAtPoint(evt.getPoint());
	            if (row1 >= 0 && col1 >= 0 && (personaltable.getModel().getValueAt(row1, col1) != null)) {
					PrintWriter writerPersonalCategoryData = null;
					try {
						writerPersonalCategoryData = new PrintWriter("src//temp//CategoryData.txt", "UTF-8");
						writerPersonalCategoryData.write(personaltable.getModel().getValueAt(row1, col1).toString() + " - " + "Personal");
						writerPersonalCategoryData.close();
					} catch (IOException ex) {
					} finally {
					   try {writerPersonalCategoryData.close();} catch (Exception ex) {}
					}
					
	                CategoryData window = new CategoryData();
					window.frame1.setVisible(true);
	            }
	        }
	    });
		
		if (personalCategoryTxt.exists()) {
		    try (BufferedReader brPersonal = new BufferedReader(new FileReader(personalCategoryTxt))) {
			    while((PersonalLine = brPersonal.readLine()) != null)  {
			    	personalTableModel.addRow(PersonalLine.split("/")); 
			        personalArray.add(PersonalLine);
			    }
			    personalTableModel.removeRow(0);
			    l = personalArray.size(); 
			    brPersonal.close();
			} catch (IOException e1) {
			 	JOptionPane.showMessageDialog(null, "Error");
				e1.printStackTrace();
			}
		    personalScroll.setViewportView(personaltable);
			personalCategory.add(personalScroll);
		}
		
		JLabel lblNewLabel_8 = new JLabel("Add a category");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_8.setBounds(180, 10, 108, 21);
		personalCategory.add(lblNewLabel_8);
		
		JButton personalBack = new JButton("Back");
		personalBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(mainPanel);
			}
		});
		personalBack.setBounds(44, 10, 85, 21);
		personalCategory.add(personalBack);
		
		personalCategoryText = new JTextField();
		personalCategoryText.setBounds(281, 10, 156, 22);
		personalCategory.add(personalCategoryText);
		personalCategoryText.setColumns(10);
		personalCategoryText.getText().toString();
		
		JButton personalAdd = new JButton("+");
		personalAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!personalCategoryText.getText().isEmpty() && (personalArray.size() <= 30) && (personalIncome.isSelected() || personalExpense.isSelected())) {
					boolean exist = false;
					try (BufferedReader brPersonal3 = new BufferedReader(new FileReader(personalCategoryTxt))) {
						String existData1 = personalCategoryText.getText() + " - " + personalIncome.getText() + "/";
						String existData2 = personalCategoryText.getText() + " - " + personalExpense.getText() + "/";
					    while((PersonalLine = brPersonal3.readLine()) != null)  {
					    	if (existData1.equals(PersonalLine) || existData2.equals(PersonalLine)) {
					    		exist = true;
					    		JOptionPane.showMessageDialog(null, "Category name already exist");
					    		break;
					    	}
					    } 
					    brPersonal3.close();
					    
					    if (exist == false) {
							personalArray.add(Objects.requireNonNull(personalCategoryText.getText().toString()));
							
							personalTableModel.setRowCount(0);
							Writer brPersonalNew = null;
							
							String personalTypeSelection = null;
							try {
								if (personalIncome.isSelected()) {
									personalTypeSelection = personalIncome.getText();
								} else if (personalExpense.isSelected()) {
									personalTypeSelection = personalExpense.getText();
								}
								
								brPersonalNew = new BufferedWriter(new FileWriter(personalCategoryTxt, true));
								brPersonalNew.write(personalArray.get(l) + " - " + personalTypeSelection + "/" + "\n");
								brPersonalNew.close();
								
								try (BufferedReader brPersonal2 = new BufferedReader(new FileReader(personalCategoryTxt))) {
								    while((PersonalLine = brPersonal2.readLine()) != null)  {
								    	personalTableModel.addRow(PersonalLine.split("/")); 
								    } 
								    brPersonal2.close();
								} catch (IOException e1) {
								 	JOptionPane.showMessageDialog(null, "Error");
									e1.printStackTrace();
								}
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null, "Error");
								e1.printStackTrace();
							}
							l = l + 1;
						}
					} catch (IOException e1) {
					 	JOptionPane.showMessageDialog(null, "Error");
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Fill all fields");
				}
			}
		});
		personalAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
		personalAdd.setBounds(641, 11, 55, 19);
		personalCategory.add(personalAdd);
		
		/**
		 * Business Category.
		 */
		businessCategory = new JPanel();
		businessCategory.setPreferredSize(new Dimension(200,200));
		businessCategory.setBorder(new LineBorder(new Color(0, 0, 0)));
		layeredPane.add(businessCategory, "name_18165113748200");
		businessCategory.setLayout(null);
		
		businessType = new ButtonGroup();
		
		businessIncome = new JRadioButton("Income");
		businessIncome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessIncome.setBounds(451, 10, 85, 21);
		businessCategory.add(businessIncome);
		
		businessExpense = new JRadioButton("Expense");
		businessExpense.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessExpense.setBounds(538, 10, 91, 21);
		businessCategory.add(businessExpense);
		
		businessType.add(businessExpense);
		businessType.add(businessIncome);
		
		JScrollPane businessScroll = new JScrollPane();
		businessScroll.setBounds(26, 48, 147, 287);
		
		businesstable = new JTable();
		businesstable.setColumnSelectionAllowed(true);
		businesstable.setCellSelectionEnabled(true);
		
		DefaultTableModel businessTableModel;
		businessTableModel = new DefaultTableModel(1,1); 
	    businesstable.setModel(businessTableModel);
	    
	    businesstable.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = businesstable.rowAtPoint(evt.getPoint());
	            int col = businesstable.columnAtPoint(evt.getPoint());
	            if (row >= 0 && col >= 0 && (businesstable.getModel().getValueAt(row, col) != null)) {
					
					PrintWriter writerBusinessCategoryData = null;
					try {
						writerBusinessCategoryData = new PrintWriter("src//temp//CategoryData.txt", "UTF-8");
						writerBusinessCategoryData.write(businesstable.getModel().getValueAt(row, col).toString() + " - " + "Business" + " - " + row);
						writerBusinessCategoryData.close();
					} catch (IOException ex) {
					} finally {
					   try {writerBusinessCategoryData.close();} catch (Exception ex) {}
					}
					
	                CategoryData window = new CategoryData();
					window.frame1.setVisible(true);
	            }
	        }
	    });
	    
		if (businessCategoryTxt.exists()) {
		    try (BufferedReader brBusiness = new BufferedReader(new FileReader(businessCategoryTxt))) {
			    while((BusinessLine = brBusiness.readLine()) != null)  {
			    	businessTableModel.addRow(BusinessLine.split("/")); 
			        businessArray.add(BusinessLine);
			    }
			    businessTableModel.removeRow(0);
			    j = businessArray.size(); 
			    brBusiness.close();
			} catch (IOException e1) {
			 	JOptionPane.showMessageDialog(null, "Error");
				e1.printStackTrace();
			}
		    businessScroll.setViewportView(businesstable);
			businessCategory.add(businessScroll);
		}
		
		JLabel lblNewLabel_1 = new JLabel("Add a category");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(180, 10, 108, 21);
		businessCategory.add(lblNewLabel_1);
		
		JButton businessBack = new JButton("Back");
		businessBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		businessBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(mainPanel);
			}
		});
		businessBack.setBounds(44, 12, 85, 21);
		businessCategory.add(businessBack);
		
		businessCategoryText = new JTextField();
		businessCategoryText.setBounds(281, 10, 156, 22);
		businessCategory.add(businessCategoryText);
		businessCategoryText.setColumns(10);
		businessCategoryText.getText().toString();
		
		JButton businessAdd = new JButton("+");
		businessAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!businessCategoryText.getText().isEmpty() && (businessArray.size() <= 30) && (businessIncome.isSelected() || businessExpense.isSelected())) {
					boolean exist = false;
					try (BufferedReader brBusiness4 = new BufferedReader(new FileReader(businessCategoryTxt))) {
						String existData1 = businessCategoryText.getText() + " - " + businessIncome.getText() + "/";
						String existData2 = businessCategoryText.getText() + " - " + businessExpense.getText() + "/";
					    while((BusinessLine = brBusiness4.readLine()) != null)  {
					    	if (existData1.equals(BusinessLine) || existData2.equals(BusinessLine)) {
					    		exist = true;
					    		JOptionPane.showMessageDialog(null, "Category name already exist");
					    		break;
					    	}
					    }
					    brBusiness4.close();
					    if (exist == false) {
					    	businessArray.add(Objects.requireNonNull(businessCategoryText.getText().toString()));
							
							businessTableModel.setRowCount(0);
							Writer brBusinessNew = null;
							
							String businessTypeSelection = null;
							try {
								if (businessIncome.isSelected()) {
									businessTypeSelection = businessIncome.getText();
								} else if (businessExpense.isSelected()) {
									businessTypeSelection = businessExpense.getText();
								}
								
								brBusinessNew = new BufferedWriter(new FileWriter(businessCategoryTxt, true));
								brBusinessNew.write(businessArray.get(j) + " - " + businessTypeSelection + "/" + "\n");
								brBusinessNew.close();
								try (BufferedReader brBusiness2 = new BufferedReader(new FileReader(businessCategoryTxt))) {
								    while((BusinessLine = brBusiness2.readLine()) != null)  {
								    	businessTableModel.addRow(BusinessLine.split("/")); 
								    } 
								    brBusiness2.close();
								} catch (IOException e1) {
								 	JOptionPane.showMessageDialog(null, "Error");
									e1.printStackTrace();
								}
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null, "Error");
								e1.printStackTrace();
							}
							j = j + 1;
					    }
					} catch (IOException e1) {
					 	JOptionPane.showMessageDialog(null, "Error");
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Fill all fields");
				}
			}
		});
		businessAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
		businessAdd.setBounds(641, 11, 55, 19);
		businessCategory.add(businessAdd);
		
		personalUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(personalCategory);
			}
		});
		personalUpdate.setBounds(28, 137, 85, 21);
		mainPanel.add(personalUpdate);
		
		businessUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(businessCategory);
			}
		});
		businessUpdate.setBounds(28, 282, 85, 21);
		mainPanel.add(businessUpdate);
		
		/**
		 * MainPanel textPlane.
		 */
		personalCredit = new JTextPane();
		personalCredit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		personalCredit.setEditable(false);
		personalCredit.setBounds(67, 52, 111, 19);
		mainPanel.add(personalCredit);
		
		businessCredit = new JTextPane();
		businessCredit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		businessCredit.setEditable(false);
		businessCredit.setBounds(67, 197, 111, 19);
		mainPanel.add(businessCredit);
		
		
		
		personalDebit = new JTextPane();
		personalDebit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		personalDebit.setEditable(false);
		personalDebit.setBounds(268, 52, 111, 19);
		mainPanel.add(personalDebit);
		
		businessDebit = new JTextPane();
		businessDebit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		businessDebit.setEditable(false);
		businessDebit.setBounds(268, 197, 111, 19);
		mainPanel.add(businessDebit);
		
		personalBalance = new JTextPane();
		personalBalance.setFont(new Font("Tahoma", Font.PLAIN, 13));
		personalBalance.setEditable(false);
		personalBalance.setBounds(490, 52, 111, 19);
		mainPanel.add(personalBalance);
		
		businessBalance = new JTextPane();
		businessBalance.setFont(new Font("Tahoma", Font.PLAIN, 13));
		businessBalance.setEditable(false);
		businessBalance.setBounds(490, 197, 111, 19);
		mainPanel.add(businessBalance);	
		
		/**
		 * MainPanel labels.
		 */
		JLabel lblNewLabel_2 = new JLabel("Personal");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(10, 23, 103, 19);
		mainPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Business");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(10, 168, 103, 19);
		mainPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Credit");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(20, 52, 45, 19);
		mainPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Credit");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(20, 197, 45, 19);
		mainPanel.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Debit");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(227, 52, 36, 19);
		mainPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_6_1 = new JLabel("Debit");
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_6_1.setBounds(227, 195, 36, 21);
		mainPanel.add(lblNewLabel_6_1);
		
		JLabel lblNewLabel_7 = new JLabel("Balance");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(430, 50, 57, 21);
		mainPanel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_7_1 = new JLabel("Balance");
		lblNewLabel_7_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_7_1.setBounds(430, 197, 57, 19);
		mainPanel.add(lblNewLabel_7_1);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel.setBounds(611, 44, 19, 27);
        mainPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_9 = new JLabel("");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_9.setBounds(611, 189, 19, 27);
        mainPanel.add(lblNewLabel_9); 
		
		JButton refresh = new JButton("Refresh");
		refresh.setToolTipText("Click to refresh the page");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GetTotal getTotal = new GetTotal();
		        businessDebit.setText(Double.toString(getTotal.A2.get(0)));
		        businessCredit.setText(Double.toString(getTotal.A2.get(1)));
		        personalDebit.setText(Double.toString(getTotal.A2.get(2)));
		        personalCredit.setText(Double.toString(getTotal.A2.get(3)));
		        businessBalance.setText(Double.toString(getTotal.A2.get(1) - getTotal.A2.get(0)));
		        personalBalance.setText(Double.toString(getTotal.A2.get(3) - getTotal.A2.get(2)));

		        if(Double.parseDouble(personalBalance.getText()) > 0){
		        	lblNewLabel.setText("↑");
		        	lblNewLabel.setForeground(Color.GREEN);
		        }else if (Double.parseDouble(personalBalance.getText()) < 0) {
		        	lblNewLabel.setText("↓");
		        	lblNewLabel.setForeground(Color.RED);
		        }
		        
		        if(Double.parseDouble(businessBalance.getText()) > 0){
		        	lblNewLabel_9.setText("↑");
		        	lblNewLabel_9.setForeground(Color.GREEN);
		        }else if (Double.parseDouble(businessBalance.getText()) < 0) {
		        	lblNewLabel_9.setText("↓");
		        	lblNewLabel_9.setForeground(Color.RED);
		        }
			}
		});
		refresh.setBounds(696, 10, 85, 21);
		mainPanel.add(refresh);
		
		/**
		 * Date & Clock.
		 */
		clock = new JLabel("New label");
		clock.setFont(new Font("Tahoma", Font.PLAIN, 13));
		clock.setBounds(639, 10, 162, 25);
		frame.getContentPane().add(clock);
		
		Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();    
        
        GetTotal getTotal = new GetTotal();
        businessDebit.setText(Double.toString(getTotal.A2.get(0)));
        businessCredit.setText(Double.toString(getTotal.A2.get(1)));
        personalDebit.setText(Double.toString(getTotal.A2.get(2)));
        personalCredit.setText(Double.toString(getTotal.A2.get(3)));
        businessBalance.setText(Double.toString(getTotal.A2.get(1) - getTotal.A2.get(0)));
        personalBalance.setText(Double.toString(getTotal.A2.get(3) - getTotal.A2.get(2)));
        
        if(Double.parseDouble(personalBalance.getText()) > 0){
        	lblNewLabel.setText("↑");
        	lblNewLabel.setForeground(Color.GREEN);
        }else if (Double.parseDouble(personalBalance.getText()) < 0) {
        	lblNewLabel.setText("↓");
        	lblNewLabel.setForeground(Color.RED);
        }
        
        if(Double.parseDouble(businessBalance.getText()) > 0){
        	lblNewLabel_9.setText("↑");
        	lblNewLabel_9.setForeground(Color.GREEN);
        }else if (Double.parseDouble(businessBalance.getText()) < 0) {
        	lblNewLabel_9.setText("↓");
        	lblNewLabel_9.setForeground(Color.RED);
        }
	}
}

class GetTotal {

	String totalText[] = {"Business//Expense", "Business//Income", "Personal//Expense", "Personal//Income"};
	ArrayList<Double> A2;
	
	public GetTotal() {
		A2 = new ArrayList<>();
		for (int m = 0; m < totalText.length; m++) {
			File folder = new File("src//total//" + totalText[m]);
			File[] listOfFiles = folder.listFiles();
			double D1 = 0;
			String BE;
			ArrayList<Double> A1 = new ArrayList<>();
			for (int i = 0; i < listOfFiles.length; i++) {
				File file = listOfFiles[i];
			    if (file.isFile() && file.getName().endsWith(".txt")) {
			    	try (BufferedReader totalRead1 = new BufferedReader(new FileReader(file))) {
			    		while((BE = totalRead1.readLine()) != null)  {
			    			A1.add(Double.parseDouble(BE));
			    			
					    } 
					    totalRead1.close();
					    D1 = D1 + A1.get(i);
					    
				    } catch (IOException e1) {
				    	JOptionPane.showMessageDialog(null, "Error");
					  	e1.printStackTrace();
				    }
			    } 
			    
			}
			A2.add(D1);
		}
	}
}
