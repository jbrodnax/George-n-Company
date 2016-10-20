package addressBook;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AddressBook implements Serializable {
	/**
	 * Generated Serial Version ID
	 */
	private static final long serialVersionUID = 5372416145887794709L;
	
	//public Contacts[] entries;			//Array of Contacts for this.AddressBook
	ArrayList<Contacts> entries = new ArrayList<Contacts>();
	ArrayList<String> nameSort = new ArrayList<String>();
	private String bookName;

	//Initializes new AddressBook
	//For the time being, default max number of contacts per book is 10
	public AddressBook(String Name){
		bookName = Name;
	}
	
	//Adds new Contact instance to this book's entries array
	public void addContact(Contacts c){
		entries.add(c);
	}
	
	public void sortByName(){
		nameSort.clear();
		for(int i=0;i<entries.size();i++){
			nameSort.add(entries.get(i).getName());
		}
	}
	
	//Loops through entries array and prints all entry fields of each contact found in the entries array
	public void printContacts(){
		if(entries.size() > 0){
			for (int i=0;i<entries.size();i++)
				entries.get(i).printContact();
		}else{
			System.out.println("You have no friends.. Try making some.");
		}
	}
	
	public int numContacts(){
		return entries.size();
	}
	
	//Returns index number of contact entry if it exists, else -1 is returned
	//string arg is compared to the "first" name of a contact entry, not "last"
	//name comparisons are case sensitive
	//Inefficient search
	public int haveContact(String s){
		for (int i=0;i<entries.size();i++){
			if (entries.get(i).getName().equals(s)){
				return i;
			}
		}
		return -1;
	}
	
	//If the requested contact exists, the contact's entries index number is set by haveContact
	//The contact at index i is then overwritten by shifting all later contact entries down by one
	public int deleteContact(String s){
		int index = haveContact(s);
		if(index >= 0){
			entries.remove(index);
			return 0;
		}
		return -1;
	}
	
	public void deleteContactAt(int i){
		entries.remove(i);
	}
	
	public Contacts getContact(int index){
		Contacts c;
		c = entries.get(index);
		return c;
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
	
	public void setBookName(String Name){
		bookName = Name;
	}
	
	public String getBookName(){
		return this.bookName;
	}
	
}
