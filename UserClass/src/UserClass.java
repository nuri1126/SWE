import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserClass {
	String userId = null;
	String userPassword = null;
	public UserClass() throws IOException{
		getUserInfo();
		login();
	}
	public void login() throws IOException{
		System.out.print("회원님의 아이디를 입력해주세요. \n>>>");
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader inputId = new BufferedReader(inputStreamReader);
		BufferedReader inputPassword = new BufferedReader(inputStreamReader);
		if(isLogin(inputId,inputPassword)){
			userMain();
		}else{
			login();
		}
	}
	public boolean isLogin(BufferedReader inputId,BufferedReader inputPassword) throws IOException{
		if(inputId.readLine().equals(userId)){
			System.out.print("회원님의 패스워드를 입력해주세요. \n>>>");
			if(inputPassword.readLine().equals(userPassword)){
				System.out.println(userId+"님, "+"환영합니다.");
				return true;
			}else{System.out.print("틀렸습니다.");return false;}
		}else{System.out.print("틀렸습니다.");return false;}
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
				break;
			case "0":System.out.println("안녕히가세요.");
				System.exit(0);
				break;
			}
		}
	}
	public void getUserInfo(){
		FileReader filereader = null;
		String path = UserClass.class.getResource("").getPath();
		try{
			filereader =  new FileReader(path+"user.txt");
			String content = null;
			BufferedReader bufferedreader = new BufferedReader(filereader,1024);
			
			while((content=bufferedreader.readLine())!=null){
				String getSubstring = content.substring(0,3);
				System.out.println(getSubstring);
				if(getSubstring.equals("id:")){
					userId = content.substring(3);
					System.out.println(userId);
				}else if(getSubstring.equals("pw:")){
					userPassword = content.substring(3);
				}
			}
		}catch(IOException e){
			System.out.println(e.toString());
		}
	}
	public void changeIdAndPassword(){
		
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














}
