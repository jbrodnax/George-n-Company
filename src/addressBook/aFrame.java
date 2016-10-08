package addressBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

class aFrame extends JFrame {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JButton addContact  = new JButton("Add");
  private JButton disContact = new JButton("Display");
  private JButton save = new JButton("Save");
  private JButton load = new JButton("Load");
  private JButton edit = new JButton("Edit");

  private JTextField txtAddress = new JTextField();
  private JTextField txtFirst = new JTextField();
  private JTextField txtLast = new JTextField();
  private JTextField txtEmail = new JTextField();
  private JTextField txtSearch = new JTextField();
  private JTextArea txtContacts = new JTextArea();
  private JTextArea txtContactInfo = new JTextArea();

  private JLabel lblAddress = new JLabel("Address :");
  private JLabel lblFirst = new JLabel("First :");
  private JLabel lblLast = new JLabel("Last :");
  private JLabel lblEmail = new JLabel("Email: ");
  private JLabel lblSearch = new JLabel("Search: ");
  public AddressBook aBook;

  public aFrame(AddressBook Book){
    setTitle("Address Book");
    setSize(400,600);
    setLocation(new Point(300,200));
    setLayout(null);
    
    aBook = Book;

    initComponent();
    initEvent();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    
  }
  
  private void initComponent(){
	  
	    addContact.setBounds(300,130, 100,25);
	    disContact.setBounds(300,100, 100,25);
	    save.setBounds(300, 70, 100, 25);
	    load.setBounds(300, 40, 100, 25);
	    edit.setBounds(300, 10, 100, 25);

	    txtAddress.setBounds(100,10,100,20);
	    txtFirst.setBounds(100,35,100,20);
	    txtLast.setBounds(100,60,100,20);
	    txtEmail.setBounds(100, 85, 100, 20);
	    txtSearch.setBounds(100, 110, 100, 20);
	    txtContacts.setBounds(25,160,175,300);
	    txtContactInfo.setBounds(210, 160, 175, 300);

	    lblAddress.setBounds(20,10,100,20);
	    lblFirst.setBounds(20,35,100,20);
	    lblLast.setBounds(20,60,100,20);
	    lblEmail.setBounds(20,85, 100, 20);
	    lblSearch.setBounds(20, 110, 100, 20);

	    add(addContact);
	    add(disContact);
	    add(save);
	    add(load);
	    add(edit);

	    add(lblAddress);
	    add(lblFirst);
	    add(lblLast);
	    add(lblEmail);
	    add(lblSearch);

	    add(txtAddress);
	    add(txtFirst);
	    add(txtLast);
	    add(txtEmail);
	    add(txtSearch);
	    add(txtContacts);
	    add(txtContactInfo);
	  }

  private void initEvent(){

	    this.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e){
	       System.exit(1);
	      }
	    });

	    disContact.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        btnDisplayContactInfo(e);
	      }
	    });

	    addContact.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        btnAddContactClick(e);
	        DisplayContacts();
	      }
	    });
	    
	    save.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        btnSaveClick(e);
		      }
		    });
	    
	    load.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        btnLoadClick(e);
		      }
		    });
	    
	    edit.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        btnEditClick(e);
		      }
		    });
	  }
	  
	  private void DisplayContacts(){
	    aBook.printContacts();
	    txtContacts.setText("");
	    for(int i=0;i<aBook.numContacts();i++){
	    	Integer num = i;
	    	txtContacts.append(num.toString() + ") " + aBook.entries.get(i).getFirst() + "\n");
	    }
	  }
	  
	  private void btnDisplayContactInfo(ActionEvent evt){
		  String First;
		  int i;
		  Contacts c;
		  try{
			  First = txtSearch.getText();
			  i = aBook.haveContact(First);
			  if(i!=-1){
				  c = aBook.getContact(i);
				  txtContactInfo.setText(c.getContactInfo());
				  
			  }else{
				  txtContactInfo.setText("Contact: "+First+" does not exist");
			  }
		  }catch(Exception e){
			  System.out.println(e);
			  JOptionPane.showMessageDialog(null, 
			          e.toString(),
			          "Error", 
			          JOptionPane.ERROR_MESSAGE);
		  }
	  }
	  
	  private void btnAddContactClick(ActionEvent evt){
	    String Addr, First, Last, Email;
	    try{
	      Addr = txtAddress.getText();
	      First = txtFirst.getText();
	      Last = txtLast.getText();
	      Email = txtEmail.getText();
	      aBook.addContact(new Contacts(First, Last, Addr, Email));

	    }catch(Exception e){
	      System.out.println(e);
	      JOptionPane.showMessageDialog(null, 
	          e.toString(),
	          "Error", 
	          JOptionPane.ERROR_MESSAGE);
	    }
	  }
	  
	  private void btnSaveClick(ActionEvent evt){
		  Serializer serializer = new Serializer();
			serializer.serializeBook(aBook);
	  }
	  
	  private void btnLoadClick(ActionEvent evt){
			try{
				String fileName = "aBook.ser";
				AddressBook newBook = (AddressBook) Serializer.deserialize(fileName);
				newBook.printContacts();
			}catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
	  }
	  
	  private void btnEditClick(ActionEvent evt){
		  String Addr, First, Last, Email;
		  int i;
		  Contacts c;
		    try{
		      Addr = txtAddress.getText();
		      First = txtFirst.getText();
		      Last = txtLast.getText();
		      Email = txtEmail.getText();
		      i = aBook.haveContact(First);
		      if(i!=-1){
		    	  c = aBook.getContact(i);
		    	  c.setAddress(Addr);
		    	  c.setFirst(First);
		    	  c.setLast(Last);
		    	  c.setEmail(Email);
		      }else{
		    	  System.out.println("Error: "+First+" is not a contact");
		      }

		    }catch(Exception e){
		      System.out.println(e);
		      JOptionPane.showMessageDialog(null, 
		          e.toString(),
		          "Error", 
		          JOptionPane.ERROR_MESSAGE);
		    }
	  }
}