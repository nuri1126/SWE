import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserClass {
	String userId="1311713";
	String userPassword="a1311713";
	public UserClass() throws IOException{
		login();
	}
	public void login() throws IOException{
		System.out.print("회원님의 아이디를 입력해주세요. \n>>>");
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader inputId = new BufferedReader(inputStreamReader);
		if(inputId.readLine().equals(userId)){
			System.out.print("회원님의 패스워드를 입력해주세요. \n>>>");
			BufferedReader inputPassword = new BufferedReader(inputStreamReader);
			if(inputPassword.readLine().equals(userPassword)){
				System.out.println(userId+"님, "+"환영합니다.");
				userMain();
			}else{System.out.print("틀렸습니다.");login();}
		}else{System.out.print("틀렸습니다.");login();}
	}
	public void userMain() throws IOException{
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		while(true){
			System.out.println("원하는 작업을 입력하세요.");
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
	}
	public static void main(String[] args) throws IOException {
		UserClass user = new UserClass();
	}
}
class Memo {
	private String memoContent;
	private int memoNumber;
	private String memoDate;
	
	public Memo(String memoContent, int memoNumber, String memoDate) {
		this.memoContent = memoContent;
		this.memoNumber = memoNumber;
		this.memoDate = memoDate;
	}
	public void setMemoNumber(int memoNumber) {
		this.memoNumber = memoNumber;
	}
}
class MemoClass {
	InputStreamReader inputStreamReader = new InputStreamReader(System.in);
	final int initialMemoNumber = -1;
	public MemoClass() throws IOException {
		memoMain();
	}
	void memoMain() throws IOException {
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		boolean managingMemo = true;
		while(managingMemo){
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
				BufferedReader memoNumber = new BufferedReader(inputStreamReader);
				System.out.println("지울 메모 번호를 입력하세요. \n>>>");
				int deleteNumber = Integer.parseInt(memoNumber.readLine());
				delete(deleteNumber);
				break;
			case "0":
				managingMemo = false;
				break;
			}
		}
	}
	void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("메모 내용을 입력하세요. \n>>>");
		String inputContent = tempContent.readLine();
		boolean validAnswer = true;
		while(validAnswer){
			System.out.println("메모를 추가하시겠습니까?(Y/N) \n>>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Date tempToday = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String today = transFormat.format(tempToday);
				Memo memo = new Memo(inputContent, initialMemoNumber, today);
				fileSave(memo);
			}
			else if(answer.equals("N")){
				System.out.println("메모 작성을 취소합니다.");
				break;
			}
			else{
				System.out.println("Y 또는 N를 입력해주세요.");
				validAnswer = false;
			}
		}
	}
	void view() {
		
	}
	void delete(int memoNumber) {
		
	}
	void fileSave(Memo memo) {
		
	}
	void fileOpen(Memo memo) {
		
	}
	void updateNumber() {
		
	}
}