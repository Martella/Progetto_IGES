package testing;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestingProva {
	
	@Before
	public void beforeTest() {
		
	}
	

	@Test
	public void testGenerale() {
		assertEquals(true, true);
	}
	
	@After
	public void afterTest(){
		
	}
}
