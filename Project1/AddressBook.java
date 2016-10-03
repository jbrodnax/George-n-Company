package addressBook;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class AddressBook implements Serializable {
	/**
	 * Generated Serial Version ID
	 */
	private static final long serialVersionUID = 5372416145887794709L;
	
	private Contacts[] entries;			//Array of Contacts for this.AddressBook
	private int numEntries;				//Number of contacts in AddressBook

	//Initializes new AddressBook
	//For the time being, default max number of contacts per book is 10
	public AddressBook(){
		entries = new Contacts[10];
		numEntries = 0;
	}
	
	//Adds new Contact instance to this book's entries array
	public void addContact(Contacts c){
		entries[numEntries] = c;
		numEntries++;
	}
	
	//Loops through entries array and prints all entry fields of each contact found in the entries array
	public void printContacts(){
		if(numEntries > 0){
			for (int i=0;i<numEntries;i++)
				entries[i].printContact();
		}else{
			System.out.println("You have no friends.. Try making some.");
		}
	}
	
	public int numContacts(){
		return numEntries;
	}
	
	//Returns index number of contact entry if it exists, else -1 is returned
	//string arg is compared to the "first" name of a contact entry, not "last"
	//name comparisons are case sensitive
	//Inefficient search
	private int haveContact(String s){
		for (int i=0;i<numEntries;i++){
			if (entries[i].getName().equals(s)){
				return i;
			}
		}
		return -1;
	}
	
	//If the requested contact exists, the contact's entries index number is set by haveContact
	//The contact at index i is then overwritten by shifting all later contact entries down by one
	//This is also an inefficient method. Basically, using an array to store contatcs is just bad. I'll implement a dictionary soon
	public int deleteContact(String s){
		int index = haveContact(s);
		if(index >= 0){
			for (int i=index;i<numEntries;i++){
				entries[i] = entries[i+1];
			}
			numEntries--;
			return 0;
		}
		return -1;
	}
	
	//Pretty straight-forward text-display
	public static void menu(){
		System.out.println("(1) Add contact");
		System.out.println("(2) Delete contact");
		System.out.println("(3) Number of contacts");
		System.out.println("(4) Display all contacts");
		System.out.println("(5) Edit name:");
		System.out.println("(6) Save Address Book");
		System.out.println("(7) Load Address Book");
		System.out.println("(8) Quit");
		System.out.println("Enter you menu option:");
	}
	
	//event loop
	//Wont need this functionality once the GUI is working
	public static void main(String[] args) throws IOException{
		Scanner stdin = new Scanner(System.in);
		
		AddressBook aBook = new AddressBook();
		menu();
		int choice = stdin.nextInt();
		
		while(choice != 8){
			if(choice == 1){
				if(aBook.numContacts() < 10){
					System.out.println("First Name:");
					String first = stdin.next();
					System.out.println("Last Name:");
					String last = stdin.next();
					System.out.println("Address:");
					String address = stdin.next();
					System.out.println("Email:");
					String email = stdin.next();
					
					aBook.addContact(new Contacts(first, last, address, email));
				}else{
					System.out.println("Address Book is full");
				}
			}
			else if(choice == 2){
				System.out.println("Name of contact to delete:");
				String name = stdin.next();
				if((aBook.deleteContact(name)) == -1){
					System.out.println("Contact does not exist");
				}else{
					System.out.println("Contact deleted");
				}
			}
			else if(choice == 3){
				System.out.println("You have " + aBook.numContacts() + "contacts");
			}
			else if(choice == 4){
				aBook.printContacts();
			}
			else if(choice == 5){
				System.out.println("Enter contact name:");
				String name = stdin.next();
				int i = aBook.haveContact(name);
				if(i != -1){
					aBook.entries[i].printContact();
					System.out.println("Change first name to:");
					name = stdin.next();
					aBook.entries[i].setFirst(name);
				}
			}
			else if(choice == 6){
				System.out.println("Saving Address Book...");
				Serializer serializer = new Serializer();
				serializer.serializeBook(aBook);
			}
			else if(choice == 7){
				System.out.println("Enter path/to/file:");
				try{
					String fileName = stdin.next();
					AddressBook newBook = (AddressBook) Serializer.deserialize(fileName);
					newBook.printContacts();
				}catch(IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			else if(choice != 8){
				System.out.println("Invalid menu option..");
			}
			menu();
			choice = stdin.nextInt();
		}
		stdin.close();
	}
}
