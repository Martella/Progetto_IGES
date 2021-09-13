package testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import elementiScenario.BancoRifornimenti;

public class UTC_BancoRifornimenti {
	
	BancoRifornimenti bancoR;
	
	@Before
	public void beforeTest() {
		bancoR = new BancoRifornimenti();
	}
	

	@Test
	public void testGetTipo() {
		//valore default bancoRifornimenti
		assertEquals("bancoRifornimenti", bancoR.getTipo());
		assertNotEquals("robot", bancoR.getTipo());
	}
	@Test
	public void testGetX() {
		// valore default 0
		assertEquals(0, bancoR.getX());
	}
	
	@Test
	public void testGetY() {
		// valore default 0
		assertEquals(0, bancoR.getY());
	}
	
	@Test
	public void testGetRiservaEnergia() {
		// valore default 250
		assertEquals(250, bancoR.getRiservaEnergia());
	}	
	
	@Test
	public void testModificaX() {
		bancoR.modificaX(10);
		assertEquals(10, bancoR.getX());
		assertNotEquals(0, bancoR.getX());
	}

	@Test
	public void testModificaY() {
		bancoR.modificaY(10);
		assertEquals(10, bancoR.getY());
		assertNotEquals(0, bancoR.getY());
	}
	
	@Test
	public void testModificaRiservaEnergia() {
		bancoR.modificaRiservaEnergia(100);
		assertEquals(100, bancoR.getRiservaEnergia());
		assertNotEquals(250, bancoR.getRiservaEnergia());
	}
	
}
