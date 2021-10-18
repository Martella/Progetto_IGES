package testing;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllo.GameManager;

public class ITC_GameManager {
	
	GameManager manager;
	
	@Before
	public void beforeTest() {
		manager = new GameManager();
		manager.startNuovaPartita("classic", "controGiocatore", 4);
	}
	

	@Test
	public void testGenerale() {
		assertEquals(true, true);
	}
	
	@After
	public void afterTest(){
		
	}
}
