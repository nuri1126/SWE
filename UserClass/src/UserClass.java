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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class UserClass {
	String userId="1311713";
	String userPassword="a1311713";
	public UserClass() throws IOException {
		login();
	}
	public void login() throws IOException {
		System.out.print("ȸ������ ���̵� �Է����ּ���. \n>>>");
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader inputId = new BufferedReader(inputStreamReader);
		if(inputId.readLine().equals(userId)){
			System.out.print("ȸ������ �н����带 �Է����ּ���. \n>>>");
			BufferedReader inputPassword = new BufferedReader(inputStreamReader);
			if(inputPassword.readLine().equals(userPassword)){
				System.out.println(userId+"��, "+"ȯ���մϴ�.");
				userMain();
			}else{System.out.print("Ʋ�Ƚ��ϴ�.");login();}
		}else{System.out.print("Ʋ�Ƚ��ϴ�.");login();}
	}
	public void userMain() throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		while(true){
			System.out.println("���ϴ� �۾��� �Է��ϼ���.");
			System.out.println("1.Change id or password");
			System.out.println("2.PhoneBook");
			System.out.println("3.Schedule");
			System.out.println("4.Memo");
			System.out.println("0.Logout");
			System.out.print(">>>");
			switch(inputNumber.readLine()){
			case "1":
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
				System.out.println("�ȳ���������.");
				System.exit(0);
				break;
			}
		}
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
			System.out.println("���ϴ� �۾��� �Է��ϼ���.");
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
						System.out.println("�޸� �����ϴ�.");
						break;
					}
					BufferedReader tempNumber = new BufferedReader(inputStreamReader);
					System.out.println("���� �޸� ��ȣ�� �Է��ϼ���.");
					System.out.print(">>>");
					int memoNumber = Integer.parseInt(tempNumber.readLine());
					delete(memoNumber);
					break;
				case "0":
					System.out.println("�޸� �۾����� �����ϴ�.");
					fileSave(memoVector);
					managingMemo = false;
					break;
				default :
					System.out.println("0~3 ������ ���ڸ� �Է��ϼ���!");
					break;
			}
		}
	}
	void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("�޸� ������ �Է��ϼ���.");
		System.out.print(">>>");
		String inputContent = tempContent.readLine();
		while(true){
			System.out.println("�޸� �߰��Ͻðڽ��ϱ�?(Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Date tempToday = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String today = transFormat.format(tempToday);
				Memo memo = new Memo(inputContent, initialMemoNumber, today);
				memoVector.add(memo);
				System.out.println("�޸� �����Ͽ����ϴ�!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("�޸� �ۼ��� ����մϴ�.");
				break;
			} else
				System.out.println("Y �Ǵ� N�� �Է����ּ���!");
		}
	}
	void view() {
		if(memoVector.isEmpty()){
			System.out.println("�޸� �����ϴ�.");
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
				System.out.println(memoNumber+"�� �޸� �����߽��ϴ�!");
				return;
			}
		}
		System.out.println(memoNumber+"���� �ش��ϴ� �޸� �����ϴ�. view�޴��� �޸� Ȯ���ϼ���.");
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
			System.out.println("----------MEMO----------");
			System.out.println("���ϴ� �۾��� �Է��ϼ���.");
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
						System.out.println("�������� �����ϴ�.");
						break;
					}
					BufferedReader tempNumber = new BufferedReader(inputStreamReader);
					System.out.println("���� ������ ��ȣ�� �Է��ϼ���.");
					System.out.print(">>>");
					int scheduleNumber = Integer.parseInt(tempNumber.readLine());
					delete(scheduleNumber);
					break;
				case "0":
					System.out.println("������ �۾����� �����ϴ�.");
					fileSave(scheduleVector);
					managingSchedule = false;
					break;
				default :
					System.out.println("0~3 ������ ���ڸ� �Է��ϼ���!");
					break;
			}
		}
	}
	void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("������ ������ �Է��ϼ���.");
		System.out.print(">>>");
		String inputContent = tempContent.readLine();
		System.out.println("������ ��¥�� �Է��ϼ���.(yyyy-MM-dd)");
		System.out.print(">>>");
		String inputDate = tempContent.readLine();
		
		while(true){
			System.out.println("�������� �߰��Ͻðڽ��ϱ�?(Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Schedule schedule = new Schedule(inputContent, initialScheduleNumber, inputDate);
				scheduleVector.add(schedule);
				System.out.println("�������� �����Ͽ����ϴ�!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("������ �ۼ��� ����մϴ�.");
				break;
			} else
				System.out.println("Y �Ǵ� N�� �Է����ּ���!");
		}
	}
	
	void view() {
		if(scheduleVector.isEmpty()){
			System.out.println("�������� �����ϴ�.");
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
				System.out.println(scheduleNumber+"�� �������� �����߽��ϴ�!");
				return;
			}
		}
		System.out.println(scheduleNumber+"���� �ش��ϴ� �������� �����ϴ�. view�޴��� �������� Ȯ���ϼ���.");
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