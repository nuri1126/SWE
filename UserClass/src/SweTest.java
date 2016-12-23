import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

public class SweTest {
	
	@Test
	public void testCheckIdAndPassword() throws IOException {
		User user = new User("TEST");
		String userid = user.getUserId();
		assertTrue(user.CheckIdAndPassword("id:") == true);
		assertTrue(user.CheckIdAndPassword("pw:") == false);
		assertTrue(user.CheckIdAndPassword("TEST") == false);
		assertTrue(user.CheckIdAndPassword(userid) == false);
		assertNotNull(user.CheckIdAndPassword("id:"));
	}
	
   @Test
   public void testIsId() throws IOException{
      User user = new User("TEST");
	  UserClass userclass = new UserClass(user.getUserId(),user.getUserPassword(),"TEST");
	  String testString = user.getUserPassword()+"TEST";
	  assertTrue(userclass.isId(user.getUserId()) == true);
	  assertTrue(userclass.isId(testString) == false);
	  assertNotNull(userclass.isId("test"));
   }
	
   @Test
   public void testPhonebookEmpty() throws IOException, ClassNotFoundException {
	   PhonebookClass phonebook = new PhonebookClass();
	   phonebook.fileOpen();
	   int size = phonebook.phonebookVector.size();
	   assertNotNull(phonebook.isPhonebookEmpty());
	   assertEquals(isEmpty(size, phonebook),true);
   }
   
   public boolean isEmpty(int size, PhonebookClass phonebook) {
	   if(size == 0) {
		   if(phonebook.isPhonebookEmpty() == true)
			   return true;
		   else
			   return false;
	      }else{
	    	  if(phonebook.isPhonebookEmpty() == false)
	    		  return true;
	    	  else
	    		  return false;
	      }
	}
	
	@Test
	public void testEqualPhonebookNumber() throws IOException, ClassNotFoundException {
		PhonebookClass phonebook = new PhonebookClass();
		phonebook.fileOpen();
		int size = phonebook.phonebookVector.size();
		assertTrue(phonebook.equalPhonebookNumber(1,1) == true);
		assertTrue(phonebook.equalPhonebookNumber(2,1) == false);
		assertTrue(phonebook.equalPhonebookNumber(size,-1) == false);
		assertTrue(phonebook.equalPhonebookNumber(size,0) == false);
	}
	
	@Test
	public void testIsStringNum() throws IOException {
		MemoClass memo = new MemoClass();
		assertTrue(memo.isStringNum(null) == false);
		assertTrue(memo.isStringNum("test string") == false);
		assertTrue(memo.isStringNum("-100") == true);
		assertTrue(memo.isStringNum("-50") == true);
		assertTrue(memo.isStringNum("0") == true);
		assertTrue(memo.isStringNum("50") == true);
		assertTrue(memo.isStringNum("100") == true);
	}
	
	@Test
	public void testDelete() throws IOException, ClassNotFoundException {
		MemoClass memo = new MemoClass();
		memo.fileOpen();
		int size = memo.memoVector.size();
		assertTrue(memo.delete(-100) == false);
		assertTrue(memo.delete(-50) == false);
		assertTrue(memo.delete(0) == false);
		assertTrue(memo.delete(size) == true);
		assertTrue(memo.delete(size/2) == true);
	}

	@Test
	public void testScheduleDataExist() throws IOException {
		ScheduleClass schedule = new ScheduleClass();
		assertTrue(schedule.view(0)==true); 
		assertTrue(schedule.view(-1)==true);
		assertTrue(schedule.view(10)==true);
		assertTrue(schedule.view(2000)==true);
	}
}
