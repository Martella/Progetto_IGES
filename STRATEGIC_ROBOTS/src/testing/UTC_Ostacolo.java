package testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import elementiScenario.Ostacolo;

public class UTC_Ostacolo {
	
	Ostacolo ostacolo;
	
	@Before
	public void beforeTest() {
		ostacolo = new Ostacolo();
	}
	
	@Test
	public void testGetTipo() {
		//valore default ostacolo
		assertEquals("ostacolo", ostacolo.getTipo());
		assertNotEquals("robot", ostacolo.getTipo());
	}
	
	@Test
	public void testGetX() {
		// valore default 0
		assertEquals(0, ostacolo.getX());
		assertNotEquals(1, ostacolo.getX());
	}
	
	@Test
	public void testGetY() {
		// valore default 0
		assertEquals(0, ostacolo.getY());
		assertNotEquals(1, ostacolo.getY());
	}
	
	@Test
	public void testGetEnergia() {
		// valore default 100
		assertEquals(100, ostacolo.getEnergia());
		assertNotEquals(1, ostacolo.getEnergia());
	}
	
	@Test
	public void testModificaX() {
		ostacolo.modificaX(10);
		assertEquals(10, ostacolo.getX());
		assertNotEquals(11, ostacolo.getX());
	}

	@Test
	public void testModificaY() {
		ostacolo.modificaY(10);
		assertEquals(10, ostacolo.getY());
		assertNotEquals(11, ostacolo.getY());
	}
	
	@Test
	public void testSpostamentoX() {
		ostacolo.spostamentoX(20);
		assertEquals(20, ostacolo.getX());
		assertNotEquals(10, ostacolo.getX());
	}

	@Test
	public void testSpostamentoY() {
		ostacolo.spostamentoY(20);
		assertEquals(20, ostacolo.getY());
		assertNotEquals(10, ostacolo.getY());
	}
	
	@Test
	public void testModificaEnergia() {
		ostacolo.modificaEnergia(50);
		assertEquals(50, ostacolo.getEnergia());
		assertNotEquals(100, ostacolo.getEnergia());
	}
	
	
}
