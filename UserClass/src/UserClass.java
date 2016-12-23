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
	
	public User() throws IOException, ClassNotFoundException {
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
	public UserClass(String userId, String userPassword) throws IOException, ClassNotFoundException {
		this.userId = userId;
		this.userPassword = userPassword;
		login();
	}
	
	public void login() throws IOException, ClassNotFoundException {
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
	
	public void userMain() throws IOException, ClassNotFoundException {
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
	
	public void ChooseAction(String inputNumber) throws IOException, ClassNotFoundException{
		switch(inputNumber){
		case "1":
			ChangeInformationMain();
			break;
		case "2":
			PhonebookClass phone = new PhonebookClass();
			phone.phonebookMain();
			break;
		case "3":
			ScheduleClass schedule = new ScheduleClass();
			schedule.scheduleMain();
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
	
	public void rejectLogin() throws IOException, ClassNotFoundException{
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
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		User user = new User();
	}
}
