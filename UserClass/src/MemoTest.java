import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

public class MemoTest {
	@Test
	public void testDelete() throws IOException {
		MemoClass memo = new MemoClass();
		assertTrue(memo.isStringNum(null) == false);
		assertTrue(memo.isStringNum("test string") == false);
		assertTrue(memo.isStringNum("0") == true);
		assertTrue(memo.isStringNum("50") == true);
		assertTrue(memo.isStringNum("100") == true);
	}
}
