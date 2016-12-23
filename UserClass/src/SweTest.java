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
	public void testisId() throws IOException {
		UserClass user = new UserClass();
		String userid = user.getUserId();
		assertTrue(user.isId(userid) == true);
		assertTrue(user.isId("1") == false);
		assertNotNull(user.isId(userid));
	}
	
	@Test
	public void testFilePath() throws IOException{
		UserClass user = new UserClass();
		assertNotNull(user.FilePath());
		
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
}
