package testing;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import elementiScenario.BarraEnergia;

public class UTC_BarraEnergia {
	
	BarraEnergia barra1;
	BarraEnergia barra2;
	
	@Before
	public void beforeTest() {
		barra1 = new BarraEnergia();
		barra2 = new BarraEnergia(50);
	}
	
	
	@Test
	public void testGetEnergia() {
		// valore default 0
		assertEquals(0, barra1.getEnergia());
		// valore default 50
		assertEquals(50, barra2.getEnergia());
	}
	
	@Test
	public void testGetMaxEnergia() {
		// valore default 100
		assertEquals(100, barra1.getMaxEnergia());
		assertEquals(100, barra2.getMaxEnergia());
	}
}
