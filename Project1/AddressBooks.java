package addressBook;
 
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
    }
    
    public void positionWindow(JFrame frame)				//sets location for multiple windows
    {														//so their not on top of eachother
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
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton openButton = new JButton("Open");
        JButton importButton = new JButton("Import");
        JButton exportButton = new JButton("Export");
        
        addButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent args) 
            {
                String bookName;
        		bookName = JOptionPane.showInputDialog("Book name");
            }
        });
        
        deleteButton.addActionListener(new ActionListener() 	//action listener for delete book button
        {
            public void actionPerformed(ActionEvent args) 
            {
            	int confirm = JOptionPane.showConfirmDialog(null, "Delete selected address book?", "Confirm Delete",
            	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            	if (confirm == JOptionPane.NO_OPTION) 
            	{
            	      System.out.println("No button clicked");
            	} 
            	else if (confirm == JOptionPane.YES_OPTION) 
            	{
            	      System.out.println("Yes button clicked");
            	} 
            	else if (confirm == JOptionPane.CLOSED_OPTION) 
            	{
            	      System.out.println("JOptionPane closed");
            	}
            	  
            	
            }
        });
        
        openButton.addActionListener(new ActionListener() 		//action listener for open book button
        {
            public void actionPerformed(ActionEvent args) 
            {
            	createOpenBookWindow();							//creates single address book window
            }
        });
        
        importButton.addActionListener(new ActionListener() 	//action listener for import book button
        {
            public void actionPerformed(ActionEvent args) 
            {
                String fileName;
        		fileName = JOptionPane.showInputDialog("Enter file to import from");	//creates import book dialogue box
            }
        });
        
        exportButton.addActionListener(new ActionListener() 	//action listener for export book button
        {
            public void actionPerformed(ActionEvent args) 
            {
                String bookName;
        		bookName = JOptionPane.showInputDialog("Enter file to export to");	//creates export book dialogue box
            }
        });
        
        JPanel pane = new JPanel();								//put the buttons in a pane
        pane.add(addButton);
        pane.add(deleteButton);
        pane.add(openButton);
        pane.add(importButton);
        pane.add(exportButton);
        return pane;
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
 
            JButton button = new JButton("Add person");
            button.addActionListener(this);
 
            Container contentPane = getContentPane();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(Box.createVerticalGlue()); //takes all extra space
            contentPane.add(button);
            button.setAlignmentX(Component.CENTER_ALIGNMENT); //horizontally centered
        }
 
        public void actionPerformed(ActionEvent e) 
        {
            setVisible(false);
            dispose();
        }
    } 
}