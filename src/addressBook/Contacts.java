package addressBook;

import java.io.Serializable;
import java.util.ArrayList;

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
	ArrayList<EntryField> Info = new ArrayList<EntryField>();
	
	//constructor
	Contacts(String first, String last, String address, String email){
		this.first = first;
		this.last = last;
		this.address = address;
		this.email = email;
		
		addEntryField("First", first);
		addEntryField("Last", last);
		addEntryField("Address", address);
		addEntryField("Email", email);
	}

	//default constructor
	Contacts(){
		setFirst("");
		setLast("");
		setAddress("");
		setEmail("");
	}
	
	public void addEntryField(String type, String info){
		Info.add(new EntryField(type, info));
	}
	
	public void printContact(){
		System.out.println("First Name: "+getFirst());
		System.out.println("Last Name: "+getLast());
		System.out.println("Address: "+getAddress());
		System.out.println("Email: "+getEmail());
	}
	
	public String getContactInfo(){
		String contactInfo = "";
		for(int i=0;i<Info.size();i++){
			EntryField e = Info.get(i);
			contactInfo+=e.getInfoType()+": ";
			contactInfo+=e.getInfo()+"\n";
		}
		return contactInfo;
	}
	
	public int haveEntryField(String type){
		for(int i=0;i<Info.size();i++){
			if(Info.get(i).getInfoType() == type){
				return i;
			}
		}
		return -1;
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
	public void setEntry(String type, String info){
		int i = haveEntryField(type);
		if(i!=-1){
			Info.get(i).setInfo(info);
		}else{
			System.out.println("Error: no entry field of type "+type);
		}
	}
	
	public void setFirst(String first) {
		this.first = first;
		setEntry("First", first);
	}
	
	public void setLast(String last){
		this.last = last;
		setEntry("Last", last);
	}
	
	public void setAddress(String address){
		this.address = address;
		setEntry("Address", address);
	}
	
	public void setEmail(String email){
		this.email = email;
		setEntry("Email", email);
	}

}
