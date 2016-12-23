import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

class Phonebook implements Serializable {
	private String name;
	private int phonebookNumber;
	private String phonenumber;
	
	public Phonebook(String name, int phonebookNumber, String phonenumber) {
		this.name = name;
		this.phonebookNumber = phonebookNumber;
		this.phonenumber = phonenumber;
	}
	
	
	public int getPhonebookNumber() {
		return phonebookNumber;
	}
	
	public void setPhonebookNumber(int phonebookNumber) {
		this.phonebookNumber = phonebookNumber;
	}
	
	@Override
	public String toString() {
		return "["+phonebookNumber+"]"+"Name : " + name + ", Phone : " + phonenumber;
	}
}

class PhonebookClass {
	InputStreamReader inputStreamReader = new InputStreamReader(System.in);
	Vector<Phonebook> phonebookVector = new Vector<Phonebook>();
	final static int initialPhonebookNumber = -1;
	
	public void phonebookMain() throws IOException, ClassNotFoundException {
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		boolean managingPhonebook = true;
		fileOpen();
		while(managingPhonebook){
			System.out.println("--------Phonebook--------");
			System.out.println("Please enter the task number.");
			System.out.println("1.Add Phonebook");
			System.out.println("2.View All Phonebook");
			System.out.println("3.Delete Phonebook");
			System.out.println("0.Quit");
			System.out.print(">>>");
			switch(inputNumber.readLine()){
				case "1":
					add();
					break;
				case "2":
					view();
					break;
				case "3":
					if(isPhonebookEmpty() == true) {
						System.out.println("There are no phonebook.");
						break;
					}
					BufferedReader tempNumber = new BufferedReader(inputStreamReader);
					System.out.println("Please enter the phonebook number to clear.");
					System.out.print(">>>");
					int phonebookNumber = Integer.parseInt(tempNumber.readLine());
					delete(phonebookNumber);
					break;
				case "0":
					System.out.println("Leave the phonebook menu.");
					fileSave(phonebookVector);
					managingPhonebook = false;
					break;
				default :
					System.out.println("Please enter a number between 0 and 3!");
					break;
			}
		}
	}
	
	public void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("Please enter the name.");
		System.out.print(">>>");
		String inputName = tempContent.readLine();
		System.out.println("Please enter the phone number. (000-0000-0000)");
		System.out.print(">>>");
		String inputPhonenumber = tempContent.readLine();
		
		while(true){
			System.out.println("Would you like to add a phonebook? (Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Phonebook phonebook = new Phonebook(inputName, initialPhonebookNumber, inputPhonenumber);
				phonebookVector.add(phonebook);
				System.out.println("You have saved your phonebook!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("You have canceled the creation.");
				break;
			} else
				System.out.println("Please enter Y or N!");
		}
	}
	
	boolean view() {
		if(isPhonebookEmpty()){
			System.out.println("There are no phonebook.");
			return false;
		}else{
			for(int i = 0 ; i < phonebookVector.size() ; i++){
				System.out.println(phonebookVector.get(i).toString());
			}
			return true;
		}
	}
	
	public boolean isPhonebookEmpty() {
		if(phonebookVector.isEmpty())
			return true;
		else
			return false;
	}
	
	void delete(int phonebookNumber) {
		for(int i = 0 ; i < phonebookVector.size() ; i++){
			int tempPhonebook = phonebookVector.get(i).getPhonebookNumber();
			if(equalPhonebookNumber(tempPhonebook, phonebookNumber)) {
				phonebookVector.remove(i);
				updateNumber();
				System.out.println("Phonebook " + phonebookNumber + " has been deleted!");
				return;
			}
		}
		System.out.println("There is no phonebook number " + phonebookNumber + ". Check the phonebook again with the view menu.");
	}
	
	public boolean equalPhonebookNumber(int temp, int pn) {
		if(temp == pn)
			return true;
		else
			return false;
	}
	
	void fileSave(Object phonebook) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("phonebook.txt"));
		oos.writeObject(phonebook);
		oos.close();
	}
	
	void fileOpen() throws FileNotFoundException, IOException, ClassNotFoundException	{
		File file = new File("phonebook.txt");
		try {
			if(file.exists()){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("phonebook.txt"));
				phonebookVector = (Vector<Phonebook>) ois.readObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	void updateNumber() {
		for(int i=0 ; i < phonebookVector.size() ; i++)
			phonebookVector.get(i).setPhonebookNumber(i+1);
	}
}
