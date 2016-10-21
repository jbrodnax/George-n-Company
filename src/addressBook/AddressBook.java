package addressBook;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class AddressBook implements Serializable {
	/**
	 * Generated Serial Version ID
	 */
	private static final long serialVersionUID = 5372416145887794709L;
	public boolean SortByName;
	public boolean SortByZIP;
	public boolean isSearch;
	private String searchStr;
	
	//public Contacts[] entries;			//Array of Contacts for this.AddressBook
	ArrayList<Contacts> entries = new ArrayList<Contacts>();	//this array holds all contacts
	ArrayList<Contacts> searchEntries = new ArrayList<Contacts>();	//this array holds "searched" contacts
	
	private String bookName;

	//Initializes new AddressBook
	//For the time being, default max number of contacts per book is 10
	public AddressBook(String Name){
		bookName = Name;
		this.SortByName = true;
		this.SortByZIP = false;
		this.isSearch = false;
		this.searchStr = "";
		
	}
	
	public void searchContacts(String s){
		this.isSearch = true;
		//updateEntries();
		this.searchStr = s;
		searchEntries.clear();
		if(s.isEmpty()){
			for(int i=0;i<entries.size();i++){
				searchEntries.add(entries.get(i));
			}
			return;
		}
		for(int i=0;i<entries.size();i++){
			if(entries.get(i).getName().toUpperCase().contains(s.toUpperCase())){
				searchEntries.add(entries.get(i));
				//Collections.sort(searchEntries, Contacts.ContactNameComparator);
			}
		}
		return;
	}
	
	public void updateEntries(){
		if(this.SortByName){
			Collections.sort(entries, Contacts.ContactNameComparator);
		}else{
			Collections.sort(entries, Contacts.ContactZipComparator);
		}
		searchContacts(this.searchStr);
	}
	
	public static Comparator<AddressBook> AddressBookNameComparator = new Comparator<AddressBook>(){
		public int compare(AddressBook a1, AddressBook a2){
			String name1 = a1.getBookName();
			String name2 = a2.getBookName();
			return name1.compareTo(name2);
		}
	};
	
	//Adds new Contact instance to this book's entries array
	public void addContact(Contacts c){
		entries.add(c);
		updateEntries();
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
			updateEntries();
			return 0;
		}
		return -1;
	}
	
	public int searchIndexToEntries(int i){
		for(int j=0;j<entries.size();j++){
			if(searchEntries.get(i) == entries.get(j)){
				return j;
			}
		}
		return -1;
	}
	
	public void deleteContactAt(int i){
		int j = searchIndexToEntries(i);
		if(j == -1){
			System.out.println("Error: searchIndexToEntries failed");
			return;
		}
		entries.remove(j);
		updateEntries();
	}
	
	public Contacts getContact(int index){
		Contacts c;
		c = searchEntries.get(index);
		//c = entries.get(index);
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
	
	public void setNameSort(){
		this.SortByName = true;
		this.SortByZIP = false;
		updateEntries();
	}
	
	public void setZipSort(){
		this.SortByName = false;
		this.SortByZIP = true;
		updateEntries();
	}
	
	public String getBookName(){
		return this.bookName;
	}
	
}
