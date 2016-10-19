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
    
    protected JComponent bookListLibraryPane(){					//creates JPanel component for the textArea displaying
        JPanel bookLibrary = new JPanel();						//all available books
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
        
        addBookButton.addActionListener(new ActionListener()
                                        {
            public void actionPerformed(ActionEvent args)
            {
                String bookName;
                bookName = JOptionPane.showInputDialog("Book name");
                System.out.println(bookName);
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
        
        JPanel bookList = new JPanel(new GridLayout(2,1));								//put the buttons in a pane
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
    
    class OpenBookFrame extends JFrame implements ActionListener //single book
    {
        
        private AddressBook Book;
        DefaultListModel<String> ContactListModel = new DefaultListModel<String>();
        JList<String> jlist = new JList<String>(ContactListModel);
        OpenBookFrame thisFrame = this;
        
        public void updateContactList(){
            ContactListModel.clear();
            for(int i=0;i<Book.entries.size();i++){
                ContactListModel.addElement(Book.getContact(i).getName());
            }
        }
        
        public OpenBookFrame(AddressBook book)
        {
            super("Opened Book");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);		//lets you close single books without exiting program
            
            this.Book = book;
            JButton addPersonButton = new JButton("Add");
            JButton deletePersonButton = new JButton("Delete");
            JButton viewPersonButton = new JButton("Open");
            JButton zipSortButton = new JButton("Zip sort");
            JButton nameSortButton = new JButton("Name sort");
            //JButton editPersonButton = new JButton("Edit");
            
            for(int i=0;i<Book.entries.size();i++){
                ContactListModel.addElement(Book.getContact(i).getName());
            }
            
            addPersonButton.addActionListener(new ActionListener() 	//action listener for export book button
                                              {
                public void actionPerformed(ActionEvent args)
                {
                    System.out.println("add person button clicked");
                    Contacts fake_person = new Contacts("John", "Brodnax", "1515 Riverview", "" ,"Eugene", "OR", "97403", "5124669790", "jbrodnax");
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
            
            viewPersonButton.addActionListener(new ActionListener() 	//action listener for export book button
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
            /*
             editPersonButton.addActionListener(new ActionListener() 	//action listener for export book button
             {
             public void actionPerformed(ActionEvent args)
             {
             int index = jlist.getSelectedIndex();
             if(index >= 0 && index <= ContactListModel.size()){
             Contacts c  = Book.getContact(index);
             c.setEditable(true);
             createContactPages(c, thisFrame); //edit to allow person to edit
             updateContactList();
             System.out.println("edit person button clicked");
             }else{
             System.out.println("Error: Person does not exist");
             }
             }
             });
             */
            jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
           	jlist.addListSelectionListener(new ListSelectionListener(){
                
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    // TODO Auto-generated method stub
                    
                }});
           	
            JScrollPane scrollPane = new JScrollPane(jlist);
            scrollPane.setPreferredSize(new Dimension(300, 350));
            
            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(Box.createVerticalGlue()); //takes all extra space
            contentPane.add(scrollPane);
            JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
            JPanel addDeleteButtons = new JPanel(new GridLayout(1,3));
            JPanel sortButtons = new JPanel(new GridLayout(1,3));
            
            //First line of buttons -- Add, Delete, View:
            addDeleteButtons.add(addPersonButton);
            addDeleteButtons.add(deletePersonButton);
            addDeleteButtons.add(viewPersonButton);
            
            //Sorting buttons
            sortButtons.add(zipSortButton);
            sortButtons.add(nameSortButton);
            
            //Adding all buttons
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
        private String zip;
        private String aptNum;
        private String street;
        
        //All 50 states and some US territories -- in Alphabetical order
        private String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN",
				"IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM",
				"NY", "NC", "ND", "OH"," OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
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
            JLabel stateAddressLabel = new JLabel("State: ");
            JLabel zipAddressLabel = new JLabel("ZIP Code: ");
            JLabel emailLabel = new JLabel("Email: ");
            
            //Text Fields: 
            final JTextField firstNameField = new JTextField("First Name: ");
            final JTextField lastNameField = new JTextField("Last Name: ");
            final JTextField phoneField = new JTextField("Phone Number: ");
            final JTextField streetAddressField = new JTextField("Address Line 1: ");
            final JTextField aptNumField = new JTextField("Address Line 2: ");
            final JTextField cityAddressField = new JTextField("City: ");
            //final JTextField stateAddressField = new JTextField("State: ");
            final JTextField zipAddressField = new JTextField("ZIP Code: ");
            final JTextField emailField = new JTextField("Email: ");
            
            //State drop-down menu
            JComboBox stateMenu = new JComboBox();
            for(int i = 0; i < 53; i++) {
            	stateMenu.addItem(states[i]);
            	}
            
            firstNameField.setText(c.getFirst());
            lastNameField.setText(c.getLast());
            streetAddressField.setText(c.getStreetAddress());
            aptNumField.setText(c.getStreetAddress());
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
                //stateAddressField.setEditable(true);
                zipAddressField.setEditable(true);
                emailField.setEditable(true);
            }
            
            saveButton.addActionListener(new ActionListener()
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
                    state = stateAddressField.getText();
                    zip = zipAddressField.getText();
                    email = emailField.getText();
                    Name = firstName + " " + lastName;
                    
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
            
            cancelButton.addActionListener(new ActionListener()
                                           {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Cancel button has been clicked");
<<<<<<< HEAD
=======
                    /* Error: Closing without saving? 
                     * JOptionPane.showMessageDialog(null, "Changes have not been saved, would you like to continue?", "Error", JOptionPane.ERROR_MESSAGE);
                     */
>>>>>>> a79f32c90f3877e4d08b0b58921e1edff21c47a7
                }
            });
            
            firstNameField.addActionListener(new ActionListener()
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
            
            /*stateAddressField.addActionListener(new ActionListener()
                                                {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String stateAddress = stateAddressField.getText();
                }
            });
            */
            
            stateMenu.addActionListener(new ActionListener() 
            {
            	@Override
            	public void actionPerformed(ActionEvent e)
            	{
            		System.out.println("State has been selected");
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
            
            //Entry fields with appropriate labels
            JPanel labelPanel1 = new JPanel(new GridLayout(1, 2));
            labelPanel1.add(firstNameLabel);
            labelPanel1.add(firstNameField);
            
            JPanel labelPanel2 = new JPanel(new GridLayout(1, 2));
            labelPanel2.add(lastNameLabel);
            labelPanel2.add(lastNameField);
            
            JPanel labelPanel3 = new JPanel(new GridLayout(1, 2));
            labelPanel3.add(phoneLabel);
            labelPanel3.add(phoneField);
            
            JPanel labelPanel4 = new JPanel(new GridLayout(1, 2));
            labelPanel4.add(streetAddressLabel);
            labelPanel4.add(streetAddressField);
            
            JPanel labelPanel5 = new JPanel(new GridLayout(1, 2));
            labelPanel5.add(aptNumLabel);
            labelPanel5.add(aptNumField);
            
            JPanel labelPanel6 = new JPanel(new GridLayout(1, 2));
            labelPanel6.add(cityAddressLabel);
            labelPanel6.add(cityAddressField);
            
            JPanel labelPanel7 = new JPanel(new GridLayout(1, 2));
            labelPanel7.add(stateAddressLabel);
            //labelPanel7.add(stateAddressField);
            labelPanel7.add(stateMenu);
            
            JPanel labelPanel8 = new JPanel(new GridLayout(1, 2));
            labelPanel8.add(zipAddressLabel);
            labelPanel8.add(zipAddressField);
            
            JPanel labelPanel9 = new JPanel(new GridLayout(1, 2));
            labelPanel9.add(emailLabel);
            labelPanel9.add(emailField);
            
            //Entry field panel -- holds all the text fields
            JPanel entryFieldPanel = new JPanel(new GridLayout(9, 1));
            entryFieldPanel.add(labelPanel1);
            entryFieldPanel.add(labelPanel2);
            entryFieldPanel.add(labelPanel3);
            entryFieldPanel.add(labelPanel4);
            entryFieldPanel.add(labelPanel5);
            entryFieldPanel.add(labelPanel6);
            entryFieldPanel.add(labelPanel7);
            entryFieldPanel.add(labelPanel8);
            entryFieldPanel.add(labelPanel9);
            
            contentPane.add(entryFieldPanel);
            
            //Save and Cancel buttons
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            contentPane.add(buttonPanel);
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
