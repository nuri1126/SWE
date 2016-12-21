import java.io.BufferedInputStream;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class UserClass {
	private String userId = null;
	private String userPassword = null;
	boolean managePw = true;
	boolean manageId = true;
	boolean managingInfo = true;
	String content = null;
	
	public UserClass() throws IOException {
		getUserInfo();
		login();
	}
	
	public void getUserInfo() throws IOException {
		BufferedReader bufferedreader = ReadUserInfoFile();
		
		while(isNotEmpty(bufferedreader)){
			String getSubstring = content.substring(0,3);
			System.out.println(getSubstring);
			
			if(CheckIdAndPassword(getSubstring))
				userId = content.substring(3);
			else
				userPassword = content.substring(3);
			
		}
	}
	
	public BufferedReader ReadUserInfoFile() throws FileNotFoundException{
		FileReader filereader = null;
		String path = UserClass.class.getResource("").getPath();
		filereader =  new FileReader(path+"userInfo.txt");
		BufferedReader bufferedreader = new BufferedReader(filereader,1024);
		return bufferedreader;
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
	
	public void login() throws IOException {
		System.out.print("회원님의 아이디를 입력해주세요. \n>>>");
		String inputNumber = getInputNumber();
		if(isIdAndPassword(inputNumber)){
			System.out.print("회원님의 패스워드를 입력해주세요. \n>>>");
			inputNumber = getInputNumber();
			if(isIdAndPassword(inputNumber)){
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
	
	public boolean isIdAndPassword(String inputNumber) throws IOException {
		if(inputNumber.equals(userId) || inputNumber.equals(userPassword)){
			return true;
		}else 
			return false;
	}
	
	public void userMain() throws IOException {
		String inputNumber = getInputNumber();
		
		while(true){
			System.out.println("원하는 작업을 입력하세요.");
			System.out.println("1.Change id or password");
			System.out.println("2.PhoneBook");
			System.out.println("3.Schedule");
			System.out.println("4.Memo");
			System.out.println("0.Logout");
			System.out.print(">>>");
			ChooseAction(inputNumber);
		}
	}
	
	public void ChooseAction(String inputNumber) throws IOException{
		switch(inputNumber){
		case "1":
			ChangeInformationMain();
			break;
		case "2":
			break;
		case "3":
			new ScheduleClass();
			break;
		case "4":
			new MemoClass();
			break;
		case "0":
			System.out.println("안녕히가세요.");
			System.exit(0);
			break;
		}
	}
	
	public void rejectLogin() throws IOException{
		System.out.print("틀렸습니다.");
		login();
	}
	
	public void ChangeInformationMain() throws IOException {
		while(managingInfo){
			System.out.println("원하는 작업을 입력하세요.");
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
		FileWriter filewriter = new FileWriter(UserClass.class.getResource("").getPath()+"userInfo.txt");
		String inputNumber = getInputNumber();

		while(manageId){
			System.out.println("현재 아이디를 입력하세요.");
			if(inputNumber.equals(userId)){
				SaveChangedInformation(filewriter, manageId);
				manageId = false;
				
			}
		}
	}
	
	public void ChangePassword() throws IOException {
		FileWriter filewriter = new FileWriter(UserClass.class.getResource("").getPath()+"userInfo.txt");
		String inputNumber = getInputNumber();
		
		while(managePw){
			System.out.println("현재 패스워드를 입력하세요.");
			if(inputNumber.equals(userPassword)){
				managePw = false;
				SaveChangedInformation(filewriter, manageId);
			}
		}
	}
	
	public void SaveChangedInformation(FileWriter filewriter, boolean manageId) throws IOException {
		if(!manageId){
			System.out.println("변결할 아이디를 입력하세요.");
			String changeId = getInputNumber();
			userId = changeId;
		}else{
			System.out.println("변결할 패스워드를 입력하세요.");
			String changepw = getInputNumber();
			userPassword = changepw;
		}
		String saveContent = "id:"+userId+"\n"+"pw:"+userPassword;
		filewriter.write(saveContent);
		filewriter.close();
	}
	
	public static void main(String[] args) throws IOException {
		UserClass user = new UserClass();
	}
}

class Memo implements Serializable {
	private String memoContent;
	private int memoNumber;
	private String memoDate;
	public Memo(String memoContent, int memoNumber, String memoDate) {
		this.memoContent = memoContent;
		this.memoNumber = memoNumber;
		this.memoDate = memoDate;
	}
	public int getMemoNumber() {
		return memoNumber;
	}
	public void setMemoNumber(int memoNumber) {
		this.memoNumber = memoNumber;
	}
	@Override
	public String toString() {
		return "["+memoNumber+"]"+"Content : " + memoContent + ", Date : " + memoDate;
	}
}
class MemoClass {
	InputStreamReader inputStreamReader = new InputStreamReader(System.in);
	Vector<Memo> memoVector = new Vector<Memo>();
	final int initialMemoNumber = -1;
	public MemoClass() throws IOException {
		memoMain();
	}
	void memoMain() throws IOException {
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		boolean managingMemo = true;
		File file = new File("memo.txt");
		if(file.exists()){
			try {
				fileOpen();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		while(managingMemo){
			System.out.println("----------MEMO----------");
			System.out.println("원하는 작업을 입력하세요.");
			System.out.println("1.Add Memo");
			System.out.println("2.View All Memo");
			System.out.println("3.Delete Memo");
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
					if(memoVector.isEmpty()){
						System.out.println("메모가 없습니다.");
						break;
					}
					BufferedReader tempNumber = new BufferedReader(inputStreamReader);
					System.out.println("지울 메모 번호를 입력하세요.");
					System.out.print(">>>");
					int memoNumber = Integer.parseInt(tempNumber.readLine());
					delete(memoNumber);
					break;
				case "0":
					System.out.println("메모 작업에서 나갑니다.");
					fileSave(memoVector);
					managingMemo = false;
					break;
				default :
					System.out.println("0~3 사이의 숫자를 입력하세요!");
					break;
			}
		}
	}
	void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("메모 내용을 입력하세요.");
		System.out.print(">>>");
		String inputContent = tempContent.readLine();
		while(true){
			System.out.println("메모를 추가하시겠습니까?(Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Date tempToday = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String today = transFormat.format(tempToday);
				Memo memo = new Memo(inputContent, initialMemoNumber, today);
				memoVector.add(memo);
				System.out.println("메모를 저장하였습니다!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("메모 작성을 취소합니다.");
				break;
			} else
				System.out.println("Y 또는 N를 입력해주세요!");
		}
	}
	void view() {
		if(memoVector.isEmpty()){
			System.out.println("메모가 없습니다.");
			return;
		}
		for(int i = 0 ; i < memoVector.size() ; i++){
			System.out.println(memoVector.get(i).toString());
		}
	}
	void delete(int memoNumber) {
		for(int i = 0 ; i < memoVector.size() ; i++){
			Memo tempMemo = memoVector.get(i);
			if(tempMemo.getMemoNumber()==memoNumber){
				memoVector.remove(i);
				updateNumber();
				System.out.println(memoNumber+"번 메모를 삭제했습니다!");
				return;
			}
		}
		System.out.println(memoNumber+"번에 해당하는 메모가 없습니다. view메뉴로 메모를 확인하세요.");
	}
	void fileSave(Object memo) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("memo.txt"));
		oos.writeObject(memo);
		oos.close();
	}
	void fileOpen() throws FileNotFoundException, IOException, ClassNotFoundException	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("memo.txt"));
		memoVector = (Vector<Memo>) ois.readObject();
	}
	void updateNumber() {
		for(int i=0 ; i < memoVector.size() ; i++)
			memoVector.get(i).setMemoNumber(i+1);
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
			System.out.println("원하는 작업을 입력하세요.");
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
						System.out.println("스케줄이 없습니다.");
						break;
					}
					BufferedReader tempNumber = new BufferedReader(inputStreamReader);
					System.out.println("지울 스케줄 번호를 입력하세요.");
					System.out.print(">>>");
					int scheduleNumber = Integer.parseInt(tempNumber.readLine());
					delete(scheduleNumber);
					break;
				case "0":
					System.out.println("스케줄 작업에서 나갑니다.");
					fileSave(scheduleVector);
					managingSchedule = false;
					break;
				default :
					System.out.println("0~3 사이의 숫자를 입력하세요!");
					break;
			}
		}
	}
	void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("스케줄 내용을 입력하세요.");
		System.out.print(">>>");
		String inputContent = tempContent.readLine();
		System.out.println("스케줄 날짜를 입력하세요.(yyyy-MM-dd)");
		System.out.print(">>>");
		String inputDate = tempContent.readLine();
		
		while(true){
			System.out.println("스케줄을 추가하시겠습니까?(Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Schedule schedule = new Schedule(inputContent, initialScheduleNumber, inputDate);
				scheduleVector.add(schedule);
				System.out.println("스케줄을 저장하였습니다!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("스케줄 작성을 취소합니다.");
				break;
			} else
				System.out.println("Y 또는 N를 입력해주세요!");
		}
	}
	void view() {
		if(scheduleVector.isEmpty()){
			System.out.println("스케줄이 없습니다.");
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
				System.out.println(scheduleNumber+"번 스케줄을 삭제했습니다~!");
				return;
			}
		}
		System.out.println(scheduleNumber+"번에 해당하는 스케줄이 없습니다. view메뉴로 스케줄을 확인하세요.");
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