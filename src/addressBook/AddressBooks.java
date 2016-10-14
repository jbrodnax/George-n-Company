
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class AddressBooks
{
    final static String CREATE_WINDOW = "new_win";
    private Point framePlace = null;						//holds screen placement of frames
    ////////create the windows you can open when you are looking at your list of books
    
    public void createOpenBookWindow()
    {
        JFrame openBook = new OpenBookFrame();				//new frame object for viewing addBook
        positionWindow(openBook);
        openBook.setSize(new Dimension(400, 500));			//sets size of frame where you add a book
        openBook.setVisible(true);							//makes frame visible
        //openBook.pack();
    }
    
    public void createContactPages()
    {
        JFrame contactPage = new ContactPages();
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
                createOpenBookWindow();							//creates single address book window
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
        public OpenBookFrame()
        {
            super("Opened book");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);		//lets you close single books without exiting program
            
            //Implement Jlist here to allow people to select the user for options
            
            JButton addPersonButton = new JButton("Add");
            JButton deletePersonButton = new JButton("Delete");
            JButton viewPersonButton = new JButton("Open");
            JButton zipSortButton = new JButton("Zip sort");
            JButton nameSortButton = new JButton("Name sort");
            JButton editPersonButton = new JButton("Edit");
            
            addPersonButton.addActionListener(new ActionListener() 	//action listener for export book button
                                              {
                public void actionPerformed(ActionEvent args)
                {
                    createContactPages();
                    System.out.println("add person button clicked");
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
                    createContactPages();  //Need it to adjust it to simply opening a new book
                    System.out.println("view person button clicked");
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
                    createContactPages(); //edit to allow person to edit
                    System.out.println("edit person button clicked");
                }
            });
            
            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(Box.createVerticalGlue()); //takes all extra space
            contentPane.add(addPersonButton);
            contentPane.add(deletePersonButton);
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
        public ContactPages() {
            super("Contact Information");
            
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
            
            saveButton.addActionListener(new ActionListener()
                                         {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Save button has been clicked");
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
                    String name = nameField.getText();
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
                    String email = emailField.getText();
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
