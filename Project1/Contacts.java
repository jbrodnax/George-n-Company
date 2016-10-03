package addressBook;

import java.io.Serializable;

public class Contacts implements Serializable {
	
	/**
	 * Generated Serial Version ID
	 */
	private static final long serialVersionUID = 5920691586809376049L;
	//entry fields
	private String first;
	private String last;
	private String address;
	private String email;
	
	//constructor
	Contacts(String first, String last, String address, String email){
		this.setFirst(first);
		this.last = last;
		this.address = address;
		this.email = email;
	}

	//default constructor
	Contacts(){
		setFirst("");
		setLast("");
		setAddress("");
		setEmail("");
	}
	
	public void printContact(){
		System.out.println("First Name: "+getFirst());
		System.out.println("Last Name: "+getLast());
		System.out.println("Address: "+getAddress());
		System.out.println("Email: "+getEmail());
	}
	
	//Getter methods
	public String getName(){
		return getFirst();
	}
	
	public String getFirst() {
		return first;
	}

	public String getLast(){
		return last;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getEmail(){
		return email;
	}
	
	//Setter methods
	public void setFirst(String first) {
		this.first = first;
	}
	
	public void setLast(String last){
		this.last = last;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

}
