package addressBook;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddressBooks
{
    final static String CREATE_WINDOW = "new_win";
    private Point framePlace = null;											//holds screen placement of frames
    ArrayList<AddressBook> myAddressBooks = new ArrayList<AddressBook>();
    DefaultListModel<String> bookListModel = new DefaultListModel<String>();
    JList<String> jlist = new JList<String>(bookListModel);

    public void updateBookLibrary(){											//called when a new book is created. it updates the listOfBooks
        bookListModel.clear();													//to display the newly created book
        Collections.sort(myAddressBooks, AddressBook.AddressBookNameComparator);
        for(int i=0;i<myAddressBooks.size();i++){
            bookListModel.addElement(myAddressBooks.get(i).getBookName());
        }
    }
    //create windows that display fields for each single contact
    public void createContactPages(Contacts c, OpenBookFrame b)
    {
        JFrame contactPage = new ContactPages(c, b);
        positionWindow(contactPage);
        contactPage.setPreferredSize(new Dimension(350, 400));
        contactPage.setVisible(true);
        contactPage.pack();
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
    //create window that holds the list of address books
    protected JComponent bookListLibraryPane(){						//creates JPanel component for the textArea displaying
        JPanel bookLibrary = new JPanel();							//all available books
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.addListSelectionListener(new ListSelectionListener(){
            
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // TODO Auto-generated method stub
                
            }});
        JScrollPane scrollPane = new JScrollPane(jlist);
        scrollPane.setPreferredSize(new Dimension(300, 350));
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
        
        addBookButton.addActionListener(new ActionListener()	//create action listener for button that adds a book 
        {
            public void actionPerformed(ActionEvent args)
            {
                String bookName;
                bookName = JOptionPane.showInputDialog(null, "Book name", "Add Book", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(bookName);
                if(bookName.isEmpty()){
                	System.out.println("Error: Book name cannot be empty");
                	JOptionPane.showMessageDialog(null, "Please enter in a book name!", "Error", JOptionPane.ERROR_MESSAGE);
                	return;
                }else if(bookListModel.contains(bookName)){
                	System.out.println("Error: Book name already exists");
                	JOptionPane.showMessageDialog(null, "This has the same name as another book!  Please enter another name.", "Error", JOptionPane.ERROR_MESSAGE);
                	return;
                }
                myAddressBooks.add(new AddressBook(bookName));
               	updateBookLibrary();
                /* Check if book has the same name as another book:
                * JOptionPane.showMessageDialog(null, "This has the same name as another book!  Please enter another name.", "Error", JOptionPane.ERROR_MESSAGE);
               	* Check for blank book name:
               	* JOptionPane.showMessageDialog(null, "Please enter in a book name!", "Error", JOptionPane.ERROR_MESSAGE);
               	*/
            }
        });
        
        deleteBookButton.addActionListener(new ActionListener() 	//action listener for delete book button
                                           {
            public void actionPerformed(ActionEvent args)
            {
                int confirm = JOptionPane.showConfirmDialog(null, "Delete selected address book?", "Confirm Delete",
                                                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int index = jlist.getSelectedIndex();
                if (confirm == JOptionPane.NO_OPTION)
                {
                    System.out.println("No button in delete book clicked");
                }
                else if (confirm == JOptionPane.YES_OPTION)
                {
                    System.out.println("Yes button in delete book clicked");
                    if(index < 0 || index > myAddressBooks.size()){
                    	System.out.println("Error: index is out of range");
                    	return;
                    }else{
                    	myAddressBooks.remove(index);
                    	updateBookLibrary();
                    }
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
                if(index < 0 || index > myAddressBooks.size()){
                	System.out.println("Error: Invalid book selected.");
                	return;
                }
                AddressBook book = myAddressBooks.get(index);
               	System.out.println("Opening Book: "+book.getBookName()+"\n");
               	
               	//Open individual book
               	JFrame openBook = new OpenBookFrame(book);				//new frame object for viewing addBook
                positionWindow(openBook);
                openBook.setTitle(book.getBookName() + " Address Book");
                openBook.setSize(new Dimension(400, 500));			//sets size of frame where you add a book
                openBook.setVisible(true);							//makes frame visible
                openBook.pack();
            }
        });
        
        importBookButton.addActionListener(new ActionListener() 	//action listener for import book button
                                           {
            public void actionPerformed(ActionEvent args)
            {
                String fileName;
                fileName = JOptionPane.showInputDialog(null, "Enter file to import from", "Import", JOptionPane.INFORMATION_MESSAGE);	//creates import book dialogue box
            }
        });
        
        exportBookButton.addActionListener(new ActionListener() 	//action listener for export book button
                                           {
            public void actionPerformed(ActionEvent args)
            {
                String bookName;
                bookName = JOptionPane.showInputDialog(null, "Enter file to export to", "Export", JOptionPane.INFORMATION_MESSAGE);	//creates export book dialogue box
            }
        });
        //add the buttons to the window displaying the list of address books
        JPanel bookList = new JPanel(new GridLayout(2,1));								
        JPanel firstLine = new JPanel(new GridLayout(1,3));
        
        firstLine.add(addBookButton);
        firstLine.add(deleteBookButton);
        firstLine.add(openBookButton);
        
        JPanel secondLine = new JPanel(new GridLayout(1,2));
        secondLine.add(importBookButton);
        secondLine.add(exportBookButton);
        bookList.add(firstLine);
        bookList.add(secondLine);
        return bookList;
    }
    
    private static void runGUI() 	//create the initial GUI window
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
        bookListFrame.pack();
    }
    
    public static void main(String[] args)	//run the program
    {
        runGUI();
    }
    
    class OpenBookFrame extends JFrame implements ActionListener //create the window for a single book to display contacts etc
    {
        
        private AddressBook Book;
        DefaultListModel<String> ContactListModel = new DefaultListModel<String>();
        JList<String> jlist = new JList<String>(ContactListModel);
        OpenBookFrame thisFrame = this;
        
        public void updateContactList(){
            ContactListModel.clear();
            Book.updateEntries();
            for(int i=0;i<Book.entries.size();i++){
                ContactListModel.addElement(Book.getContact(i).getName());
            }
        }
        
        public void nameSort(){
        	
        }
        
        public OpenBookFrame(AddressBook book)
        {
            super("Opened Book");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);		//lets you close single books without exiting program
            
            this.Book = book;
            //create the buttons for the single book window
            JButton addPersonButton = new JButton("Add");
            JButton deletePersonButton = new JButton("Delete");
            JButton viewPersonButton = new JButton("Open/Edit");
            JButton zipSortButton = new JButton("ZIP sort");
            JButton nameSortButton = new JButton("Name sort");
            
            JTextField searchField = new JTextField();		//create a dynamic search field for finding contacts
            
            for(int i=0;i<Book.entries.size();i++){
                ContactListModel.addElement(Book.getContact(i).getName());
            }
            
            addPersonButton.addActionListener(new ActionListener() 	//action listener for button that adds a contact
                                              {
                public void actionPerformed(ActionEvent args)
                {
                    System.out.println("add person button clicked");
                    Contacts new_person = new Contacts();
                    Book.addContact(new_person);
                    createContactPages(new_person, thisFrame);
                    //updateContactList();
                }
            });
            
            deletePersonButton.addActionListener(new ActionListener() 	//action listener for button deleting a person
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
                        int index = jlist.getSelectedIndex();
                        if(index >= 0 && index <= ContactListModel.size()){
                        	Book.deleteContactAt(index);
                        	updateContactList();
                        }
                    }
                    else if (confirm == JOptionPane.CLOSED_OPTION)
                    {
                        System.out.println("JOptionPane in delete person closed");
                    }
                }
            });
            
            viewPersonButton.addActionListener(new ActionListener() 	//action listener for button allowing viewing and editing person
                                               {
                public void actionPerformed(ActionEvent args)
                {
                    int index = jlist.getSelectedIndex();
                    if(index >= 0 && index <= ContactListModel.size()){
                        Contacts c = Book.getContact(index);
                        c.setEditable(false);
                        createContactPages(c, thisFrame);  //Need it to adjust it to simply opening a new book
                        System.out.println("view person button clicked");
                        updateContactList();
                    }else{
                        System.out.println("Error: Person does not exist");
                    }
                }
            });
            
            //create key listener for dynamic search of contacts in a single book
            KeyListener myKeyListener = new KeyListener() 
            {
                
            	public void keyTyped(KeyEvent e){}
                public void keyPressed(KeyEvent e){}
                public void keyReleased(KeyEvent e) 
                { 
                	String searchStr = searchField.getText();
                	System.out.println(searchStr); 
                }
            };
            searchField.addKeyListener(myKeyListener);
            
            /*
            searchField.addActionListener(new ActionListener()
            {
            	public void actionPerformed(ActionEvent args)
            	{
            		System.out.println("Search has been used");
            		//Dynamic search field
            	}
            });
            */

            zipSortButton.addActionListener(new ActionListener() 	//action listener for button sorting contacts by zip
                                            {
                public void actionPerformed(ActionEvent args)
                {
                    System.out.println("zip sort button clicked");
                    Book.setZipSort();
                    updateContactList();
                    //////////////////////////////////////////////////this is where you call the function
                    /////////////////////////////////////////////////that sorts by zip
                }
            });
            
            nameSortButton.addActionListener(new ActionListener() 	//action listener for button sorting contacts by name
                                             {
                public void actionPerformed(ActionEvent args)
                {
                    System.out.println("name sort button clicked");
                    Book.setNameSort();
                    updateContactList();
                    //////////////////////////////////////////////////this is where you call the function
                    /////////////////////////////////////////////////that sorts by name
                }
            });

            jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           	jlist.addListSelectionListener(new ListSelectionListener(){
                
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    // TODO Auto-generated method stub
                    
                }});
           	//create pane for displaying contacts in a single book
            JScrollPane scrollPane = new JScrollPane(jlist);
            scrollPane.setPreferredSize(new Dimension(300, 350));
            
            //Label for search
            JLabel searchLabel = new JLabel("Search: ");
            
            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(Box.createVerticalGlue()); //takes all extra space
            contentPane.add(scrollPane);
            //create panels for button3s in single book
            JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
            JPanel searchPanel = new JPanel();
            JPanel addDeleteButtons = new JPanel(new GridLayout(1,3));
            JPanel sortButtons = new JPanel(new GridLayout(1,3));
            
            //Search Panel: 
            searchPanel.add(searchLabel, BorderLayout.WEST);
            searchField.setPreferredSize(new Dimension(275, 35));
            searchPanel.add(searchField, BorderLayout.CENTER);
            
            //First line of buttons -- Add, Delete, View:
            addDeleteButtons.add(addPersonButton);
            addDeleteButtons.add(deletePersonButton);
            addDeleteButtons.add(viewPersonButton);
            
            //Sorting buttons
            sortButtons.add(zipSortButton);
            sortButtons.add(nameSortButton);
            
            //Adding all buttons and fields
            buttonPanel.add(searchPanel);
            buttonPanel.add(addDeleteButtons);
            buttonPanel.add(sortButtons);
            
            contentPane.add(buttonPanel);
        }
        
        public void actionPerformed(ActionEvent e)
        {
            setVisible(false);
            dispose();
        }
    }
   
    class ContactPages extends JFrame implements ActionListener
    {
        //add elements to single contact
        public Contacts Contact;
        public OpenBookFrame BookFrame;
        private AddressBook Book;
        private String Name;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String city;
        private String state;
        private int stateIndex;
        private String zip;
        private String aptNum;
        private String street;
        
        //All 50 states and some US territories -- in Alphabetical order of states then territories 
        private String[] states = {"Select", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN",
				"IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM",
				"NY", "NC", "ND", "OH","OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
				"WI", "WY", "GU", "PR", "VI"};
        
        public ContactPages(Contacts c, OpenBookFrame bookFrame) {
            super("Contact Information");
            this.Contact = c;
            this.BookFrame = bookFrame;
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");
            
            //Labels for the text fields:
            JLabel firstNameLabel = new JLabel("First Name: ");
            JLabel lastNameLabel = new JLabel("Last Name: ");
            JLabel phoneLabel = new JLabel("Phone Number: ");
            JLabel streetAddressLabel = new JLabel("Address Line 1: ");
            JLabel aptNumLabel = new JLabel("Address Line 2: ");
            JLabel cityAddressLabel = new JLabel("City: ");
            JLabel stateAddressLabel = new JLabel("State or US Territory: ");
            JLabel zipAddressLabel = new JLabel("ZIP Code: ");
            JLabel emailLabel = new JLabel("Email: ");
            
            //Text Fields: 
            final JTextField firstNameField = new JTextField("First Name: ");
            final JTextField lastNameField = new JTextField("Last Name: ");
            final JTextField phoneField = new JTextField("Phone Number: ");
            final JTextField streetAddressField = new JTextField("Address Line 1: ");
            final JTextField aptNumField = new JTextField("Address Line 2: ");
            final JTextField cityAddressField = new JTextField("City: ");
            final JTextField zipAddressField = new JTextField("ZIP Code: ");
            final JTextField emailField = new JTextField("Email: ");
            
            //State drop-down menu
            JComboBox stateMenu = new JComboBox();
            for(int i = 0; i < 54; i++) {
            	stateMenu.addItem(states[i]);
            	}
            
            //Set text field sizes:
            firstNameField.setPreferredSize(new Dimension(200, 30));
            lastNameField.setPreferredSize(new Dimension(200, 30));
            phoneField.setPreferredSize(new Dimension(200, 30));
            streetAddressField.setPreferredSize(new Dimension(200, 30));
            aptNumField.setPreferredSize(new Dimension(200, 30));
            cityAddressField.setPreferredSize(new Dimension(200, 30));
            zipAddressField.setPreferredSize(new Dimension(200, 30));
            emailField.setPreferredSize(new Dimension(200, 30));
            
            //Getting text
            firstNameField.setText(c.getFirst());
            lastNameField.setText(c.getLast());
            streetAddressField.setText(c.getStreetAddress());
            aptNumField.setText(c.getStreetAddress());
            stateMenu.setSelectedIndex(c.getStateIndex());
            cityAddressField.setText(c.getCityAddress());
            zipAddressField.setText(c.getZipAddress());
            phoneField.setText(c.getPhone());
            emailField.setText(c.getEmail());
            
            if(Contact.getEditable() == true){
                firstNameField.setEditable(true);
                lastNameField.setEditable(true);
                phoneField.setEditable(true);
                streetAddressField.setEditable(true);
                aptNumField.setEditable(true);
                cityAddressField.setEditable(true);
                zipAddressField.setEditable(true);
                emailField.setEditable(true);
            }
            
            saveButton.addActionListener(new ActionListener()//create action listener for button saving a person
                                         {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Save button has been clicked");
                    firstName = firstNameField.getText();
                    lastName = lastNameField.getText();
                    phone = phoneField.getText();
                    street = streetAddressField.getText();
                    aptNum = aptNumField.getText();
                    city = cityAddressField.getText();
                    stateIndex = stateMenu.getSelectedIndex();
                    state = states[stateIndex];
                    zip = zipAddressField.getText();
                    email = emailField.getText();
                    Name = firstName + " " + lastName;
                    //check for and validate input for single entries
                    if(firstName.isEmpty()){
                    	JOptionPane.showMessageDialog(null, "Contact info must contain a first name", "Error", JOptionPane.ERROR_MESSAGE);
                    	return;
                    }
                    if(phone.isEmpty()){
                    	JOptionPane.showMessageDialog(null, "Contact info must contain a phone number", "Error", JOptionPane.ERROR_MESSAGE);
                    	return;
                    }
                    
                    if(firstName != Contact.getFirst()){
                    	Contact.setFirst(firstName);
                    }
                    if(lastName != Contact.getLast()){
                    	Contact.setLast(lastName);
                    }
                    if(Name != Contact.getName()){
                    	Contact.setName();
                    }
                    if(phone != Contact.getPhone()){
                    	Contact.setPhone(phone);
                    }
                    if(stateIndex != Contact.getStateIndex()){
                    	Contact.setStateIndex(stateIndex);
                    }
                    if(street != Contact.getStreetAddress()){
                    	Contact.setStreetAddress(street);
                    }
                    if(city != Contact.getCityAddress()){
                    	Contact.setCityAddress(city);
                    }
                    if(zip != Contact.getZipAddress()){
                    	Contact.setZipAddress(zip);
                    }
                    if(email != Contact.getEmail()){
                    	Contact.setEmail(email);
                    }
                    //add aptNum
                    BookFrame.updateContactList();
                    dispose();
                }
                //Check for ZIP code format before saving
                //JOptionPane.showMessageDialog(null, "You've entered an unfamiliar ZIP Code, would you still like to save?", "Error", JOPtionPane.ERROR_MESSAGE); 
            });
            
            cancelButton.addActionListener(new ActionListener()//action listener for button canceling adding a single person
                                           {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Cancel button has been clicked");

                     //Error: Closing without saving?
                    int confirm = JOptionPane.showConfirmDialog(null, "Changes will not be saved.", "Continue?",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(confirm == JOptionPane.YES_OPTION){
                    	dispose();
                    }else{
                    	return;
                    }

                }
            });
            
            firstNameField.addActionListener(new ActionListener()		//read in the first name given
                                             {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String first = firstNameField.getText();
        
                }
            });
            
            phoneField.addActionListener(new ActionListener()			//read in the phone number
                                         {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String phone = phoneField.getText();
                }
            });
            
            streetAddressField.addActionListener(new ActionListener()	//read in the first address line
                                                 {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String streetAddress = streetAddressField.getText();
                }
            });
            
            cityAddressField.addActionListener(new ActionListener()		//read in the second address line
                                               {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cityAddress = cityAddressField.getText();
                }
            });

            stateMenu.addActionListener(new ActionListener() 			//read in the state selected
            {
            	@Override
            	public void actionPerformed(ActionEvent e)
            	{
            		System.out.println("State has been selected");
            	}
            });
            
            zipAddressField.addActionListener(new ActionListener()		//read in the zip code
                                              {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String zipAddress = zipAddressField.getText();
                }
            });
            
            emailField.addActionListener(new ActionListener()			//read in the email
                                         {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //String email = emailField.getText();
                }
            });
            
            //Labels to show 
            firstNameLabel.setOpaque(true);
            lastNameLabel.setOpaque(true);
            phoneLabel.setOpaque(true);
            streetAddressLabel.setOpaque(true);
            aptNumLabel.setOpaque(true);
            cityAddressLabel.setOpaque(true);
            stateAddressLabel.setOpaque(true);
            zipAddressLabel.setOpaque(true);
            emailLabel.setOpaque(true);
            
            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(Box.createVerticalGlue()); //takes all extra space
            
   			//Anchors
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 10, 5, 10);
            
            //Labels: 
            JPanel entryPanel1 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel1.add(firstNameLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel1.add(firstNameField, gbc);

            JPanel entryPanel2 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel2.add(lastNameLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel2.add(lastNameField, gbc);

            JPanel entryPanel3 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel3.add(phoneLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel3.add(phoneField, gbc);
            
            JPanel entryPanel4 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel4.add(streetAddressLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel4.add(streetAddressField, gbc);

            JPanel entryPanel5 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel5.add(aptNumLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel5.add(aptNumField, gbc);

            JPanel entryPanel6 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel6.add(cityAddressLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel6.add(cityAddressField, gbc);

            JPanel entryPanel7 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel7.add(stateAddressLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel7.add(stateMenu, gbc);

            JPanel entryPanel8 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel8.add(zipAddressLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel8.add(zipAddressField, gbc);

            JPanel entryPanel9 = new JPanel();
            gbc.anchor = GridBagConstraints.EAST;
            entryPanel9.add(emailLabel, gbc);
            gbc.anchor = GridBagConstraints.LINE_END;
            entryPanel9.add(emailField, gbc);

            JPanel entryFieldPanel = new JPanel(new GridLayout(9, 1));
            entryFieldPanel.add(entryPanel1);
            entryFieldPanel.add(entryPanel2);
            entryFieldPanel.add(entryPanel3);
            entryFieldPanel.add(entryPanel4);
            entryFieldPanel.add(entryPanel5);
            entryFieldPanel.add(entryPanel6);
            entryFieldPanel.add(entryPanel7);
            entryFieldPanel.add(entryPanel8);
            entryFieldPanel.add(entryPanel9);
            
            contentPane.add(entryFieldPanel);
            
            //Save and Cancel buttons
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            contentPane.add(buttonPanel);
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        }
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            setVisible(false);
            dispose();
        }
    }
}
