package addressBook;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddressBooks
{
    final static String CREATE_WINDOW = "new_win";
    private Point framePlace = null;						//holds screen placement of frames
    ////////create the windows you can open when you are looking at your list of books
    ArrayList<AddressBook> myAddressBooks = new ArrayList<AddressBook>();  
    DefaultListModel<String> bookListModel = new DefaultListModel<String>();
    JList<String> jlist = new JList<String>(bookListModel);
    
    public void createOpenBookWindow(AddressBook book)
    {
        JFrame openBook = new OpenBookFrame(book);				//new frame object for viewing addBook
        positionWindow(openBook);
        openBook.setSize(new Dimension(400, 500));			//sets size of frame where you add a book
        openBook.setVisible(true);							//makes frame visible
        //openBook.pack();
    }
    
    public void updateBookLibrary(){						//called when a new book is created. it updates the listOfBooks
    	bookListModel.clear();													//to display the newly created book
    		for(int i=0;i<myAddressBooks.size();i++){
    			bookListModel.addElement(myAddressBooks.get(i).getBookName());
    		}
      }
    
    public void createContactPages(Contacts c, OpenBookFrame b)
    {
        JFrame contactPage = new ContactPages(c, b);
        positionWindow(contactPage);
        contactPage.setSize(400, 500);
        contactPage.setVisible(true);
        //contactPage.pack();
    }
    
    public void positionWindow(JFrame frame)				//sets location for multiple windows
    {														//so their not on top of each other
        JFrame newFrame = frame;
        if (framePlace != null)
        {
            framePlace.translate(30, 30);
            newFrame.setLocation(framePlace);
        }
        else
        {
            framePlace = newFrame.getLocation();
        }
    }
    
    protected JComponent bookListLibraryPane(){					//creates JPanel component for the textArea displaying
    	 	JPanel bookLibrary = new JPanel();						//all available books
    	 	jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	 	jlist.addListSelectionListener(new ListSelectionListener(){
   
    			@Override
    			public void valueChanged(ListSelectionEvent e) {
    				// TODO Auto-generated method stub
  			
    		}});
    	   	JScrollPane scrollPane = new JScrollPane(jlist);
    	   	bookLibrary.add(scrollPane);
   	
    	  	return bookLibrary;
    	   }
    
    protected JComponent bookListButtonPane() 					//Create the buttons which go in the book list window
    {
        JButton addBookButton = new JButton("Add");
        JButton deleteBookButton = new JButton("Delete");
        JButton openBookButton = new JButton("Open");
        JButton importBookButton = new JButton("Import");
        JButton exportBookButton = new JButton("Export");
        
        addBookButton.addActionListener(new ActionListener()
                                        {
            public void actionPerformed(ActionEvent args)
            {
                String bookName;
                bookName = JOptionPane.showInputDialog("Book name");
                System.out.println(bookName);
                myAddressBooks.add(new AddressBook(bookName));
               	updateBookLibrary();
            }
        });
        
        deleteBookButton.addActionListener(new ActionListener() 	//action listener for delete book button
                                           {
            public void actionPerformed(ActionEvent args)
            {
                int confirm = JOptionPane.showConfirmDialog(null, "Delete selected address book?", "Confirm Delete",
                                                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.NO_OPTION)
                {
                    System.out.println("No button in delete book clicked");
                }
                else if (confirm == JOptionPane.YES_OPTION)
                {
                    System.out.println("Yes button in delete book clicked");
                }
                else if (confirm == JOptionPane.CLOSED_OPTION)
                {
                    System.out.println("JOptionPane in delete book closed");
                }
            }
        });
        
        openBookButton.addActionListener(new ActionListener() 		//action listener for open book button
                                         {
            public void actionPerformed(ActionEvent args)
            {
            	int index = jlist.getSelectedIndex();
              	AddressBook book = myAddressBooks.get(index);
               	System.out.println("Opening Book: "+book.getBookName()+"\n");
               	createOpenBookWindow(book);							//creates single addre
            }
        });
        
        importBookButton.addActionListener(new ActionListener() 	//action listener for import book button
                                           {
            public void actionPerformed(ActionEvent args)
            {
                String fileName;
                fileName = JOptionPane.showInputDialog("Enter file to import from");	//creates import book dialogue box
            }
        });
        
        exportBookButton.addActionListener(new ActionListener() 	//action listener for export book button
                                           {
            public void actionPerformed(ActionEvent args)
            {
                String bookName;
                bookName = JOptionPane.showInputDialog("Enter file to export to");	//creates export book dialogue box
            }
        });
        
        JPanel bookList = new JPanel();								//put the buttons in a pane
        bookList.add(addBookButton);
        bookList.add(deleteBookButton);
        bookList.add(openBookButton);
        bookList.add(importBookButton);
        bookList.add(exportBookButton);
        return bookList;
    }
    
    private static void runGUI() 	//create the initial gui window
    {
        JFrame bookListFrame = new JFrame("All Address Books");			//make window with address books listed
        bookListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//exit program when you close this window
        AddressBooks bookList = new AddressBooks();
        Container contentPane = bookListFrame.getContentPane();
        contentPane.add(bookList.bookListButtonPane(), BorderLayout.PAGE_END);
        contentPane.add(bookList.bookListLibraryPane(), BorderLayout.PAGE_START);
        bookListFrame.setSize(new Dimension(400, 500));					//sets size of frame holding list of books
        bookListFrame.setLocationRelativeTo(null); 						//center it
        bookListFrame.setVisible(true);									//make it visible
        //bookListFrame.pack();
    }
    
    public static void main(String[] args)	//run the program
    {
        runGUI();
    }
    
    class OpenBookFrame extends JFrame implements ActionListener //single book
    {
    
    	private AddressBook Book;
      	DefaultListModel<String> ContactListModel = new DefaultListModel<String>();
        JList<String> jlist = new JList<String>(ContactListModel);
        OpenBookFrame thisFrame = this;
       
        public void updateContactList(){						
        	ContactListModel.clear();													
         	for(int i=0;i<Book.entries.size();i++){
          		ContactListModel.addElement(Book.getContact(i).getFirst());
         	}
        }
    	
        public OpenBookFrame(AddressBook book)
        {
            super("Opened book");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);		//lets you close single books without exiting program
            
            //Implement Jlist here to allow people to select the user for options
            this.Book = book;
            JButton addPersonButton = new JButton("Add");
            JButton deletePersonButton = new JButton("Delete");
            JButton viewPersonButton = new JButton("Open");
            JButton zipSortButton = new JButton("Zip sort");
            JButton nameSortButton = new JButton("Name sort");
            JButton editPersonButton = new JButton("Edit");
            
            for(int i=0;i<Book.entries.size();i++){
            	ContactListModel.addElement(Book.getContact(i).getFirst());
            }
            
            addPersonButton.addActionListener(new ActionListener() 	//action listener for export book button
                                              {
                public void actionPerformed(ActionEvent args)
                {
             		System.out.println("add person button clicked");
            		Contacts fake_person = new Contacts("John", "Brodnax", "1515", "jbrodnax");
            		Book.addContact(fake_person);
            		updateContactList();
                }
            });
            
            deletePersonButton.addActionListener(new ActionListener() 	//action listener for export book button
                                                 {
                public void actionPerformed(ActionEvent args)
                {
                    int confirm = JOptionPane.showConfirmDialog(null, "Delete selected person?", "Confirm Delete",
                                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirm == JOptionPane.NO_OPTION)
                    {
                        System.out.println("No button in delete person clicked");
                    }
                    else if (confirm == JOptionPane.YES_OPTION)
                    {
                        System.out.println("Yes button in delete person clicked");
                    }
                    else if (confirm == JOptionPane.CLOSED_OPTION)
                    {
                        System.out.println("JOptionPane in delete person closed");
                    }
                }
            });
            
            viewPersonButton.addActionListener(new ActionListener() 	//action listener for export book button
                                               {
                public void actionPerformed(ActionEvent args)
                {
                	int index = jlist.getSelectedIndex();
                	Contacts c = Book.getContact(index);
                    createContactPages(c, thisFrame);  //Need it to adjust it to simply opening a new book
                    System.out.println("view person button clicked");
                    updateContactList();
                    
                }
            });
            
            zipSortButton.addActionListener(new ActionListener() 	//action listener for export book button
                                            {
                public void actionPerformed(ActionEvent args)
                {
                    System.out.println("zip sort button clicked");
                    //////////////////////////////////////////////////this is where you call the function
                    /////////////////////////////////////////////////that sorts by zip
                }
            });
            
            nameSortButton.addActionListener(new ActionListener() 	//action listener for export book button
                                             {
                public void actionPerformed(ActionEvent args)
                {
                    System.out.println("name sort button clicked");
                    //////////////////////////////////////////////////this is where you call the function
                    /////////////////////////////////////////////////that sorts by name
                }
            });
            
            editPersonButton.addActionListener(new ActionListener() 	//action listener for export book button
                                               {
                public void actionPerformed(ActionEvent args)
                {
                	int index = jlist.getSelectedIndex();
                	Contacts c  = Book.getContact(index);
                    createContactPages(c, thisFrame); //edit to allow person to edit
                    System.out.println("edit person button clicked");
                }
            });
            
            jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           	jlist.addListSelectionListener(new ListSelectionListener(){
     
           			@Override
           			public void valueChanged(ListSelectionEvent e) {
             				// TODO Auto-generated method stub
          				
           	}});
           	
            JScrollPane scrollPane = new JScrollPane(jlist);
            
            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(Box.createVerticalGlue()); //takes all extra space
            contentPane.add(addPersonButton);
            contentPane.add(deletePersonButton);
            contentPane.add(scrollPane);
            contentPane.add(viewPersonButton);
            contentPane.add(zipSortButton);
            contentPane.add(nameSortButton);
            contentPane.add(editPersonButton);
            //button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        }
        
        public void actionPerformed(ActionEvent e)
        {
            setVisible(false);
            dispose();
        }
    }
    
    class ContactPages extends JFrame implements ActionListener
    {
    	
    	public Contacts Contact;
    	public OpenBookFrame BookFrame;
    	private AddressBook Book;
    	private String firstName;
    	private String lastName;
    	private String email;
    	
        public ContactPages(Contacts c, OpenBookFrame bookFrame) {
            super("Contact Information");
            this.Contact = c;
            this.BookFrame = bookFrame;
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");
            
            final JTextField nameField = new JTextField("Name: ");
            final JTextField phoneField = new JTextField("Phone Number: ");
            final JTextField streetAddressField = new JTextField("Street Address: ");
            final JTextField cityAddressField = new JTextField("City: ");
            final JTextField stateAddressField = new JTextField("State: ");
            final JTextField zipAddressField = new JTextField("Zip code: ");
            final JTextField emailField = new JTextField("Email: ");
            nameField.setText(c.getFirst());
            streetAddressField.setText(c.getAddress());
            emailField.setText(c.getEmail());
            
            saveButton.addActionListener(new ActionListener()
                                         {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Save button has been clicked");
                    firstName = nameField.getText();
                    Contact.setFirst(firstName);
                    BookFrame.updateContactList();
                }
            });
            
            cancelButton.addActionListener(new ActionListener()
                                           {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Cancel button has been clicked");
                }
            });
            
            nameField.addActionListener(new ActionListener()
                                        {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                     //firstName = nameField.getText();
                }
            });
            
            phoneField.addActionListener(new ActionListener()
                                         {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String phone = phoneField.getText();
                }
            });
            
            streetAddressField.addActionListener(new ActionListener()
                                                 {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String streetAddress = streetAddressField.getText();
                }
            });
            
            cityAddressField.addActionListener(new ActionListener()
                                               {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cityAddress = cityAddressField.getText();
                }
            });
            
            stateAddressField.addActionListener(new ActionListener()
                                                {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String stateAddress = stateAddressField.getText();
                }
            });
            
            zipAddressField.addActionListener(new ActionListener() 
                                              {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String zipAddress = zipAddressField.getText();
                }
            });
            
            emailField.addActionListener(new ActionListener() 
                                         {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //String email = emailField.getText();
                }
            });
            
            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(Box.createVerticalGlue()); //takes all extra space
            contentPane.add(nameField);
            contentPane.add(phoneField);
            contentPane.add(streetAddressField);
            contentPane.add(cityAddressField);
            contentPane.add(stateAddressField);
            contentPane.add(zipAddressField);
            contentPane.add(emailField);
            
            JPanel panel = new JPanel(new GridLayout(1, 2));
            panel.add(saveButton);
            panel.add(cancelButton);
            contentPane.add(panel);
            //button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        }
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            setVisible(false);
            dispose();
        }
    }
}
