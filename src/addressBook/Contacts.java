package addressBook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Contacts implements Serializable {
	
	/**
	 * Generated Serial Version ID
	 */
	private static final long serialVersionUID = 5920691586809376049L;
	//entry fields
	private String Name;
	private String first;
	private String last;
	private String streetAddress;
	private String streetAddress2;
	private String cityAddress;
	private String stateAddress;
	private int stateIndex;
	private String zipAddress;
	private String phone;
	private String postalAddress;
	private String email;
	private boolean isEditable;
	
	ArrayList<EntryField> Info = new ArrayList<EntryField>();
	
	//constructor
	Contacts(String first, String last, 
			String phoneNum, String street, 
			String street2, String city, 
			String state, String zip, String email, int stateInt){

		this.first = first;
		this.last = last;
		this.streetAddress = street;
		this.streetAddress2 = street2;
		this.cityAddress = city;
		this.stateIndex = stateInt;
		this.stateAddress = state;
		this.zipAddress = zip;
		this.email = email;
		this.phone = phoneNum;
		
		this.Name = first + " " + last;
		this.postalAddress = this.streetAddress + "\n" + this.cityAddress+ ", "+ this.stateAddress + "\n" + this.zipAddress;
		
		addEntryField("First", this.first);
		addEntryField("Last", this.last);
		addEntryField("Street", this.streetAddress);
		addEntryField("Street2", this.streetAddress2);
		addEntryField("City", this.cityAddress);
		addEntryField("State", this.stateAddress);
		addEntryField("Zip", this.zipAddress);
		addEntryField("Phone", this.phone);
		addEntryField("Email", this.email);
	}

	//default constructor
	Contacts(){
		this.first = "";
		this.last = "";
		this.streetAddress = "";
		this.streetAddress2 = "";
		this.cityAddress = "";
		this.stateIndex = 0;
		this.stateAddress = "";
		this.zipAddress = "";
		this.email = "";
		this.phone = "";
		
		this.Name = "";
		this.postalAddress = "";
		
		addEntryField("First", this.first);
		addEntryField("Last", this.last);
		addEntryField("Street", this.streetAddress);
		addEntryField("Street2", this.streetAddress2);
		addEntryField("City", this.cityAddress);
		addEntryField("State", this.stateAddress);
		addEntryField("Zip", this.zipAddress);
		addEntryField("Phone", this.phone);
		addEntryField("Email", this.email);
	}
	
	public static Comparator<Contacts> ContactNameComparator = new Comparator<Contacts>(){
		public int compare(Contacts c1, Contacts c2){
			String name1 = c1.getFirst().toUpperCase();
			String name2 = c2.getFirst().toUpperCase();
			return name1.compareTo(name2);
		}
	};
	
	public static Comparator<Contacts> ContactZipComparator = new Comparator<Contacts>(){
		public int compare(Contacts c1, Contacts c2){
			int zip1 = Integer.parseInt(c1.getZipAddress());
			int zip2 = Integer.parseInt(c2.getZipAddress());
			return zip1 - zip2;
		}
	};
	
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
		return Name;
	}
	
	public String getFirst() {
		return first;
	}

	public String getLast(){
		return last;
	}
	
	public String getAddress(){
		return postalAddress;
	}
	
	public String getStreetAddress(){
		return this.streetAddress;
	}
	
	public String getStreetAddress2(){
		return this.streetAddress2;
	}
	
	public String getCityAddress(){
		return this.cityAddress;
	}
	
	public String getState(){
		return this.stateAddress;
	}
	
	public int getStateIndex(){
		return this.stateIndex;
	}
	
	public String getZipAddress(){
		return this.zipAddress;
	}
	
	public String getPhone(){
		return this.phone;
	}
	
	public String getEmail(){
		return email;
	}
	
	public boolean getEditable(){
		return isEditable;
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
	
	public void updatePostal(){
		this.postalAddress = this.streetAddress + "\n" + this.cityAddress+ ", "+ this.stateAddress + "\n" + this.zipAddress;
	}
	
	public void setName(){
		this.Name = this.first + " " + this.last;
	}
	
	public void setFirst(String first) {
		this.first = first;
		setEntry("First", first);
	}
	
	public void setLast(String last){
		this.last = last;
		setName(); 
		setEntry("Last", last);
	}
	
	public void setStreetAddress(String address){
		this.streetAddress = address;
		setEntry("Street", address);
	}
	
	public void setStreetAddress2(String address){
		this.streetAddress2 = address;
		setEntry("Street2", address);
	}
	
	public void setCityAddress(String address){
		this.cityAddress = address;
		setEntry("City", address);
	}
	
	public void setState(String state){
		this.stateAddress = state;
		setEntry("State", state);
	}
	
	public void setStateIndex(int index){
		this.stateIndex = index;
	}
	
	public void setZipAddress(String address){
		this.zipAddress = address;
		setEntry("Zip", address);
	}
	
	public void setPhone(String address){
		this.phone = address;
		setEntry("Phone", address);
	}
	
	public void setEmail(String email){
		this.email = email;
		setEntry("Email", email);
	}
	
	public void setEditable(boolean option){
		this.isEditable = option;
	}

}
