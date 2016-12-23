import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

class User {
	String content = null;
	private String userId = null;
	private String userPassword = null;
	
	public User() throws IOException {
		getUserInfo();
		UserClass user = new UserClass(userId,userPassword);
	}
	
	public User(String title) throws IOException {
		getUserInfo();
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void getUserInfo() throws IOException {
		BufferedReader bufferedreader = ReadUserInfoFile();
		while(isNotEmpty(bufferedreader)){
			String getSubstring = content.substring(0,3);
			if(CheckIdAndPassword(getSubstring)){
				userId = content.substring(3);
			}else
				userPassword = content.substring(3);	
		}
	}
	
	public BufferedReader ReadUserInfoFile() throws FileNotFoundException{
		FileReader filereader = null;
		filereader =  new FileReader(FilePath());
		BufferedReader bufferedreader = new BufferedReader(filereader,1024);
		return bufferedreader;
	}
	
	public String FilePath(){
		String path = System.getProperty("user.dir");
		path += "\\userInfo.txt";
		return path;
	}
	
	public String ReadPerOneline(BufferedReader bufferedreader) throws IOException{
		content = bufferedreader.readLine();
		return content;
	}
	
	public boolean isNotEmpty(BufferedReader bufferedreader) throws FileNotFoundException, IOException{
		if(ReadPerOneline(bufferedreader)!=null)
			return true;
		else
			return false;
	}
	
	public boolean CheckIdAndPassword(String getSubstring){
		if(getSubstring.equals("id:"))
			return true;
		else
			return false;
	}
	
	public String getInputNumber() throws IOException{
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		return inputNumber.readLine();
	}
}
public class UserClass {
	String userId = null;
	String userPassword = null;
	boolean managePw = true;
	boolean manageId = true;
	boolean managingInfo = true;
	
	public UserClass(String userId, String userPassword, String title) {
		this.userId = userId;
		this.userPassword = userPassword;
	}
	public UserClass(String userId, String userPassword) throws IOException {
		this.userId = userId;
		this.userPassword = userPassword;
		login();
	}
	
	public void login() throws IOException {
		System.out.print("Please enter your ID. \n>>>");
		String inputNumber = getInputNumber();
		if(isId(inputNumber)){
			System.out.print("Please enter your Password. \n>>>");
			inputNumber = getInputNumber();
			if(isPassword(inputNumber)){
				userMain();
			}else
				rejectLogin();
		}else
			rejectLogin();
	}
	
	public String getInputNumber() throws IOException{
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		return inputNumber.readLine();
	}
	
	public boolean isId(String inputNumber) throws IOException {
		if(inputNumber.equals(userId)){
			return true;
		}else 
			return false;
	}
	
	public boolean isPassword(String inputNumber) throws IOException {
		if(inputNumber.equals(userPassword)){
			return true;
		}else 
			return false;
	}
	
	public void userMain() throws IOException {
		while(true){
			System.out.println("Please enter the task number.");
			System.out.println("1.Change id or password");
			System.out.println("2.PhoneBook");
			System.out.println("3.Schedule");
			System.out.println("4.Memo");
			System.out.println("0.Logout");
			System.out.print(">>>");
			String inputNumber = getInputNumber();
			ChooseAction(inputNumber);
		}
	}
	
	public void ChooseAction(String inputNumber) throws IOException{
		switch(inputNumber){
		case "1":
			ChangeInformationMain();
			break;
		case "2":
			new PhonebookClass();
			break;
		case "3":
			new ScheduleClass();
			break;
		case "4":
			MemoClass memo = new MemoClass();
			memo.memoMain();
			break;
		case "0":
			System.out.println("Good-bye.");
			System.exit(0);
			break;
		}
	}
	
	public void rejectLogin() throws IOException{
		System.out.print("It is wrong.");
		login();
	}
	
	public void ChangeInformationMain() throws IOException {
		while(managingInfo){
			System.out.println("Please enter the task number.");
			System.out.println("1.Change id");
			System.out.println("2.Change password");
			System.out.println("0.Quit");
			System.out.print(">>>");
			switch(getInputNumber()){
			case "1":
				ChangeId();
				break;
			case "2":
				ChangePassword();
				break;
			case "0":
				managingInfo=false;
				break;
			}
		}	
	}
	
	public void ChangeId() throws IOException {
		manageId = true;
		while(manageId){
			System.out.println("Please enter your present ID.");
			String inputNumber = getInputNumber();
			if(inputNumber.equals(userId)){
				manageId = false;
				SaveChangedId(manageId);
			}
		}
	}
	
	public void ChangePassword() throws IOException {
		managePw = true;
		while(managePw){
			System.out.println("Please enter your present Password.");
			String inputNumber = getInputNumber();
			if(inputNumber.equals(userPassword)){
				managePw = false;
				SaveChangedPw(managePw);
			}
		}
	}
	
	public void SaveChangedId(boolean manageId) throws IOException {
		if(!manageId){
			System.out.println("Please enter an ID to change.");
			String changeId = getInputNumber();
			userId = changeId;
		}
		SaveChangedInfoFile();
	}
	
	public void SaveChangedPw(boolean manageId) throws IOException {
		if(!managePw){
			System.out.println("Please enter an Password to change.");
			String changepw = getInputNumber();
			userPassword = changepw;
		}
		SaveChangedInfoFile();
	}
	
	public void SaveChangedInfoFile() {
		String saveContent = "id:"+userId+"\n"+"pw:"+userPassword;
		try {
			FileWriter filewriter = new FileWriter(FilePath());
			filewriter.write(saveContent);
			filewriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String FilePath(){
		String path = System.getProperty("user.dir");
		path += "\\userInfo.txt";
		return path;
	}
	public static void main(String[] args) throws IOException {
		User user = new User();
	}
}

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
	final int initialPhonebookNumber = -1;
	
	public PhonebookClass() throws IOException {
		phonebookMain();
	}
	
	void phonebookMain() throws IOException {
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		boolean managingPhonebook = true;
		File file = new File("phonebook.txt");
		if(file.exists()){
			try {
				fileOpen();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
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
					if(phonebookVector.isEmpty()){
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
	
	void add() throws IOException {
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
	
	void view() {
		if(phonebookVector.isEmpty()){
			System.out.println("There are no phonebook.");
			return;
		}
		for(int i = 0 ; i < phonebookVector.size() ; i++){
			System.out.println(phonebookVector.get(i).toString());
		}
	}
	
	void delete(int phonebookNumber) {
		for(int i = 0 ; i < phonebookVector.size() ; i++){
			Phonebook tempPhonebook = phonebookVector.get(i);
			if(tempPhonebook.getPhonebookNumber()==phonebookNumber){
				phonebookVector.remove(i);
				updateNumber();
				System.out.println("Phonebook " + phonebookNumber + " has been deleted!");
				return;
			}
		}
		System.out.println("There is no phonebook number " + phonebookNumber + ". Check the phonebook again with the view menu.");
	}
	
	void fileSave(Object phonebook) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("phonebook.txt"));
		oos.writeObject(phonebook);
		oos.close();
	}
	
	void fileOpen() throws FileNotFoundException, IOException, ClassNotFoundException	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("phonebook.txt"));
		phonebookVector = (Vector<Phonebook>) ois.readObject();
	}
	
	void updateNumber() {
		for(int i=0 ; i < phonebookVector.size() ; i++)
			phonebookVector.get(i).setPhonebookNumber(i+1);
	}
}

class Schedule implements Serializable {
	private String scheduleContent;
	private int scheduleNumber;
	private String scheduleDate;
	
	public Schedule(String scheduleContent, int scheduleNumber, String scheduleDate) {
		this.scheduleContent = scheduleContent;
		this.scheduleNumber = scheduleNumber;
		this.scheduleDate = scheduleDate;
	}
	
	public int getScheduleNumber() {
		return scheduleNumber;
	}
	
	public void setScheduleNumber(int scheduleNumber) {
		this.scheduleNumber = scheduleNumber;
	}
	
	@Override
	public String toString() {
		return "["+scheduleNumber+"]"+"Content : " + scheduleContent + ", Date : " + scheduleDate;
	}
}

class ScheduleClass {
	InputStreamReader inputStreamReader = new InputStreamReader(System.in);
	Vector<Schedule> scheduleVector = new Vector<Schedule>();
	final int initialScheduleNumber = -1;
	
	public ScheduleClass() throws IOException {
		scheduleMain();
	}
	
	void scheduleMain() throws IOException {
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		boolean managingSchedule = true;
		File file = new File("schedule.txt");
		if(file.exists()){
			try {
				fileOpen();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		while(managingSchedule){
			System.out.println("----------SCHEDULE----------");
			System.out.println("Please enter the task number.");
			System.out.println("1.Add Schedule");
			System.out.println("2.View All Schedule");
			System.out.println("3.Delete Schedule");
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
					if(scheduleVector.isEmpty()){
						System.out.println("There are no schedule.");
						break;
					}
					BufferedReader tempNumber = new BufferedReader(inputStreamReader);
					System.out.println("Please enter the schedule number to clear.");
					System.out.print(">>>");
					int scheduleNumber = Integer.parseInt(tempNumber.readLine());
					delete(scheduleNumber);
					break;
				case "0":
					System.out.println("Leave the schedule menu.");
					fileSave(scheduleVector);
					managingSchedule = false;
					break;
				default :
					System.out.println("Please enter a number between 0 and 3!");
					break;
			}
		}
	}
	
	void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("Please enter your schedule.");
		System.out.print(">>>");
		String inputContent = tempContent.readLine();
		System.out.println("Please enter the date.(yyyy-MM-dd)");
		System.out.print(">>>");
		String inputDate = tempContent.readLine();
		
		while(true){
			System.out.println("Would you like to add a schedule? (Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Schedule schedule = new Schedule(inputContent, initialScheduleNumber, inputDate);
				scheduleVector.add(schedule);
				System.out.println("You have saved your schedule!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("You have canceled the creation.");
				break;
			} else
				System.out.println("Please enter Y or N!");
		}
	}
	
	void view() {
		if(scheduleVector.isEmpty()){
			System.out.println("There are no schedule.");
			return;
		}
		for(int i = 0 ; i < scheduleVector.size() ; i++){
			System.out.println(scheduleVector.get(i).toString());
		}
	}
	
	void delete(int scheduleNumber) {
		for(int i = 0 ; i < scheduleVector.size() ; i++){
			Schedule tempSchedule = scheduleVector.get(i);
			if(tempSchedule.getScheduleNumber()==scheduleNumber){
				scheduleVector.remove(i);
				updateNumber();
				System.out.println("Schedule " + scheduleNumber + " has been deleted!");
				return;
			}
		}
		System.out.println("There is no schedule number " + scheduleNumber + ". Check the schedule number again with the view menu.");
	}
	
	void fileSave(Object schedule) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("schedule.txt"));
		oos.writeObject(schedule);
		oos.close();
	}
	
	void fileOpen() throws FileNotFoundException, IOException, ClassNotFoundException	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("schedule.txt"));
		scheduleVector = (Vector<Schedule>) ois.readObject();
	}
	
	void updateNumber() {
		for(int i=0 ; i < scheduleVector.size() ; i++)
			scheduleVector.get(i).setScheduleNumber(i+1);
	}
}