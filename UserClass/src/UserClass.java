import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
			case "1":changeIdAndPassword();
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
	public void changeIdAndPassword(){
		
	}
	public static void main(String[] args) throws IOException {
		UserClass user = new UserClass();
	}

}
