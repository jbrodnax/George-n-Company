package addressBook;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

	//Serialize AddressBook class to be saved to Desktop
	//File output is hardcoded for now to my Desktop
	public static void serializeBook(AddressBook aBook){
		try{
			String bookName = aBook.getBookName();
			FileOutputStream fout = new FileOutputStream("Books/"+bookName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(aBook);
			oos.close();
			System.out.println("Done");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//Read and deserialize any object of type .ser and return it to caller
	//However, caller expects object type AddressBook Class
	public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}
	
}
