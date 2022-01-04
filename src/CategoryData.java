import java.awt.EventQueue;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.SwingConstants;

public class CategoryData{

	JFrame frame1;
	private JTextArea name;
	private JTextField billNo;
	private JTextField date;
	private JTextField amount;
	private JTable table;
	private String dataLine;
	private String[] categoryNameType1;
	int row;
	ArrayList<BusinessAccount> BusinessAccountList;
	DefaultTableModel dtm;
	String header[]= new String[]{"Date","Bill No","Details","Amount"};
	private JTextField total;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryData window = new CategoryData();
					window.frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void displayBusinessAccountDetails(){
		dtm.setRowCount(0);
		for(int i=0; i<BusinessAccountList.size();i++){
			Object[] obj={BusinessAccountList.get(i).date,BusinessAccountList.get(i).billNo,BusinessAccountList.get(i).name,BusinessAccountList.get(i).amount};
			dtm.addRow(obj);
		}
	}

	/**
	 * Create the application.
	 */
	public CategoryData() {
		initialize();
		BusinessAccountList=new ArrayList<>();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		frame1 = new JFrame();
		frame1.getContentPane().setBackground(new Color(100, 100, 200));
		frame1.setBounds(300, 100, 1100, 483);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		
		File categoryTXT = new File("src//temp//CategoryData.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(categoryTXT))) {
		    while((dataLine = br.readLine()) != null)  {
		    	categoryNameType1 = dataLine.split(" - "); 
		    } 
		    br.close();
		} catch (IOException e1) {
		 	JOptionPane.showMessageDialog(null, "Error");
			e1.printStackTrace();
		}
		
		JLabel categoryGet = new JLabel(categoryNameType1[0] + " [" + categoryNameType1[2] + " - " + categoryNameType1[1] + "]");
		categoryGet.setHorizontalAlignment(SwingConstants.LEFT);
		categoryGet.setFont(new Font("Tahoma", Font.PLAIN, 20));
		categoryGet.setBounds(29, 10, 448, 32);
		frame1.getContentPane().add(categoryGet);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setForeground(new Color(200, 200, 200));
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDate.setBounds(29, 29, 102, 100);
		frame1.getContentPane().add(lblDate);
		
		date = new JTextField();
		date.setBounds(127, 70, 111, 20);
		frame1.getContentPane().add(date);
		date.setColumns(10);
		
		Calendar cal = (Calendar) Calendar.getInstance();
		date.setText(dateFormat.format(cal.getTime()));
		
		JLabel lblBusinessAccountId = new JLabel("Bill No");
		lblBusinessAccountId.setForeground(new Color(200, 200, 200));
		lblBusinessAccountId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBusinessAccountId.setBounds(29, 66, 102, 100);
		frame1.getContentPane().add(lblBusinessAccountId);
		
		billNo = new JTextField();
		billNo.setBounds(127, 110, 111, 20);
		frame1.getContentPane().add(billNo);
		billNo.setColumns(10);
		
		JLabel lblBusinessAccountName = new JLabel("Details");
		lblBusinessAccountName.setForeground(new Color(200, 200, 200));
		lblBusinessAccountName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBusinessAccountName.setBounds(29, 103, 102, 100);
		frame1.getContentPane().add(lblBusinessAccountName);
		
		name = new JTextArea();
		name.setBounds(127, 150, 111, 50);
		frame1.getContentPane().add(name);
		name.setLineWrap(true);
		name.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setForeground(new Color(200, 200, 200));
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAmount.setBounds(29, 180, 102, 100);
		frame1.getContentPane().add(lblAmount);
		
		amount = new JTextField();
		amount.setBounds(127, 220, 111, 20);
		frame1.getContentPane().add(amount);
		amount.setColumns(10);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice=JOptionPane.showConfirmDialog(null, "Delete this data ?", "Delete",JOptionPane.YES_NO_OPTION);
				if(choice==0){
					dtm.removeRow(row);
					BusinessAccountList.remove(row);
					displayBusinessAccountDetails();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(127, 260, 89, 23);
		frame1.getContentPane().add(btnDelete);
		
		
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				billNo.setText("");
				name.setText("");
				amount.setText("");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setBounds(226, 260, 89, 23);
		frame1.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(325, 45, 737, 339);
		frame1.getContentPane().add(scrollPane);
		
		dtm=new DefaultTableModel(header,0);
		
		table = new JTable();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				row=table.getSelectedRow();
				date.setText(dtm.getValueAt(row, 0).toString());
				billNo.setText(dtm.getValueAt(row, 1).toString());
				name.setText(dtm.getValueAt(row, 2).toString());
				amount.setText(dtm.getValueAt(row, 3).toString());
			}
		});
		scrollPane.setViewportView(table);

		
		table.setModel(dtm);
		File CategoryTxt = new File("src//CategoryData//" + categoryNameType1[2] + "//" + categoryNameType1[1] + "//" + categoryNameType1[0] + ".txt");
		if (CategoryTxt.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(CategoryTxt))) {
				Object[] tableLines = br.lines().toArray();
				for (int i = 0; i < tableLines.length; i++) {
					String line = tableLines[i].toString().trim();
					String[] dataRow = line.split(",");
					dtm.addRow(dataRow);
					row = i;
				}
				br.close();
			} catch (IOException e1) {
			 	JOptionPane.showMessageDialog(null, "Error");
				e1.printStackTrace();
			}
		}
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] addRow = {date.getText(),billNo.getText(),name.getText(),amount.getText()};
				dtm.addRow(addRow);
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setBounds(29, 260, 89, 23);
		frame1.getContentPane().add(btnAdd);
		
		/**
		 
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.addMouseListener(new java.awt.event.MouseAdapter() {
			        @Override
			        public void mouseClicked(java.awt.event.MouseEvent evt) {
			            int row1 = table.rowAtPoint(evt.getPoint());
			            int col1 = table.columnAtPoint(evt.getPoint());
			            if (row1 >= 0 && col1 >= 0) {
							dtm.setValueAt(date.getText(), row1, col1);
							dtm.setValueAt(billNo.getText(), row1, col1+1);
							dtm.setValueAt(name.getText(), row1, col1+2);
							dtm.setValueAt(amount.getText(), row1, col1+3);
			            }
			        }
			    });
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(29, 300, 89, 23);
		frame1.getContentPane().add(btnUpdate);
		* 
		*/
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.revalidate();
				double totalDouble = 0;
				int row2 = table.getRowCount();
				for (row2 = 0; row2 < table.getRowCount(); row2++) {
					double t = Double.parseDouble((String) table.getValueAt(row2, 3));
					totalDouble = totalDouble + t;	
					total.setText(String.valueOf(totalDouble));
				}
				
				PrintWriter totalWrite = null;
				try {
					totalWrite = new PrintWriter("src//total//" + categoryNameType1[2] + "//" + categoryNameType1[1] + "//" + categoryNameType1[0] + "Total.txt", "UTF-8");
		    		totalWrite.println(totalDouble);
		    		totalWrite.close();
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					JOptionPane.showMessageDialog(null, "Error: No file match");
					e1.printStackTrace();
				}
				
				PrintWriter dataWrite = null;
				int row1 = table.getRowCount();
	            int col1 = table.getColumnCount();
	            try {
					dataWrite = new PrintWriter("src//CategoryData//" + categoryNameType1[2] + "//" + categoryNameType1[1] + "//" + categoryNameType1[0] + ".txt", "UTF-8");
					for (row1 = 0; row1 < table.getRowCount(); row1++) {
		                for (col1= 0; col1 < table.getColumnCount()-1; col1++) {
		                	dataWrite.print(table.getValueAt(row1, col1) + ",");
		                }
		                dataWrite.print(table.getValueAt(row1, col1));
		                dataWrite.print("\n");
		            }
					dataWrite.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
				   try {dataWrite.close();} catch (Exception ex) {}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(187, 340, 89, 21);
		frame1.getContentPane().add(btnNewButton);
		
		total = new JTextField();
		total.setBounds(901, 412, 161, 19);
		frame1.getContentPane().add(total);
		total.setColumns(10);
		
		table.revalidate();
		double totalDouble = 0;
		int row2 = table.getRowCount();
		for (row2 = 0; row2 < table.getRowCount(); row2++) {
			double t = Double.parseDouble((String) table.getValueAt(row2, 3));
			totalDouble = totalDouble + t;	
			total.setText(String.valueOf(totalDouble));
		}
		
		JButton getTotal = new JButton("Get Total");
		getTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.revalidate();
				double totalDouble = 0;
				 int row2 = table.getRowCount();
				for (row2 = 0; row2 < table.getRowCount(); row2++) {
					double t = Double.parseDouble((String) table.getValueAt(row2, 3));
					totalDouble = totalDouble + t;	
					total.setText(String.valueOf(totalDouble));
				}
			}
		});
		getTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
		getTotal.setBounds(71, 340, 89, 21);
		frame1.getContentPane().add(getTotal);
	}
	
	static void modifyFile(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
         
        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));
            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
             
            //Replacing oldString with newString in the oldContent
            String newContent = oldContent.replaceAll(oldString, newString);
             
            //Rewriting the input text file with newContent
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources
                reader.close();
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}