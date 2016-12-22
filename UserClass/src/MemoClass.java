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
	static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
	static Vector<Memo> memoVector = new Vector<Memo>();
	final static int initialMemoNumber = -1;
	
	static void memoMain() throws IOException {
		BufferedReader inputNumber = new BufferedReader(inputStreamReader);
		boolean managingMemo = true;
		while(managingMemo){
			System.out.println("----------MEMO----------");
			System.out.println("Please enter the task number.");
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
						System.out.println("There are no memo.");
						break;
					}
					BufferedReader tempNumber = new BufferedReader(inputStreamReader);
					System.out.println("Please enter the memo number to clear.");
					System.out.print(">>>");
					String tempStr = tempNumber.readLine();
					if(isStringNum(tempStr)){
						int memoNumber = Integer.parseInt(tempStr);
						delete(memoNumber);
					}
					else
						System.out.println("Please enter the number!");
					break;
				case "0":
					System.out.println("Leave the memo menu.");
					fileSave(memoVector);
					managingMemo = false;
					break;
				default :
					System.out.println("Please enter a number between 0 and 3!");
					break;
			}
		}
	}
	
	public static boolean isStringNum(String s) {
	    try {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
    }
	
	static void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("Please enter your memo.");
		System.out.print(">>>");
		String inputContent = tempContent.readLine();
		while(true){
			System.out.println("Would you like to add a memo? (Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Date tempToday = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String today = transFormat.format(tempToday);
				Memo memo = new Memo(inputContent, initialMemoNumber, today);
				memoVector.add(memo);
				System.out.println("You have saved your memo!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("You have canceled the creation.");
				break;
			} else
				System.out.println("Please enter Y or N!");
		}
	}
	
	static void view() {
		if(memoVector.isEmpty()){
			System.out.println("There are no memo.");
			return;
		}
		for(int i = 0 ; i < memoVector.size() ; i++){
			System.out.println(memoVector.get(i).toString());
		}
	}
	
	static boolean delete(int memoNumber) {
		for(int i = 0 ; i < memoVector.size() ; i++){
			Memo tempMemo = memoVector.get(i);
			if(tempMemo.getMemoNumber()==memoNumber){
				memoVector.remove(i);
				updateNumber();
				System.out.println("Memo " + memoNumber + " has been deleted!");
				return true;
			}
		}
		System.out.println("There is no memo number. Check the memo number again with the view menu.");
		return false;
	}
	
	static void fileSave(Object memo) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("memo.txt"));
		oos.writeObject(memo);
		oos.close();
	}
	
	static void fileOpen() throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File("memo.txt");
		try {
			if(file.exists()){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("memo.txt"));
				memoVector = (Vector<Memo>) ois.readObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static void updateNumber() {
		for(int i=0 ; i < memoVector.size() ; i++)
			memoVector.get(i).setMemoNumber(i+1);
	}
}