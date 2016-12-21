import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

public class MemoTest {
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
	public void testDelete() throws IOException {
		MemoClass memo = new MemoClass();
		int size = memo.memoVector.size();
		System.out.println(size);
		assertTrue(memo.delete(-100) == false);
		assertTrue(memo.delete(-50) == false);
		assertTrue(memo.delete(0) == false);
		assertTrue(memo.delete(size) == true);
		assertTrue(memo.delete(size/2) == true);
	}
}
