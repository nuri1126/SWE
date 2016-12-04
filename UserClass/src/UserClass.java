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
		System.out.print("ȸ������ ���̵� �Է����ּ���. \n>>>");
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
			System.out.print("ȸ������ �н����带 �Է����ּ���. \n>>>");
			if(inputPassword.readLine().equals(userPassword)){
				System.out.println(userId+"��, "+"ȯ���մϴ�.");
				return true;
			}else{System.out.print("Ʋ�Ƚ��ϴ�.");return false;}
		}else{System.out.print("Ʋ�Ƚ��ϴ�.");return false;}
	}
	public void userMain() throws IOException{
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
				break;
			case "4":
				break;
			case "0":System.out.println("�ȳ���������.");
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














}
