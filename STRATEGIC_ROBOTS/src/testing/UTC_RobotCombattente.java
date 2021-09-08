package testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import eccezioni.InsufficientEnergyException;
import robot.RobotCombattente;

public class UTC_RobotCombattente {
	
	RobotCombattente robotC;
	@Before
	public void testPrima() {
		robotC = new RobotCombattente();
	}
	
	@Test
	public void testRobotGetTipo() {
		//valore default robotCombattente
		assertEquals("robotCombattente", robotC.getTipo());
		assertNotEquals("robot", robotC.getTipo());
	}
	
	@Test
	public void testRobotGetDannoAttacco() {
		// valore default 40
		assertEquals(40, robotC.getDannoAttacco());
		assertNotEquals(0, robotC.getDannoAttacco());
	}
	
	@Test
	public void testRobotGetEnergiaAttacco() {
		// valore default 5
		assertEquals(5, robotC.getEnergiaAttacco());
		assertNotEquals(0, robotC.getEnergiaAttacco());
	}
	
	@Test
	public void TestRobotAttacca() {
		// robot può attaccare solo se ha energia per l'attacco altrimenti lancia eccezione

		robotC.modificaEnergia(50);
		try {
			assertEquals(robotC.attacca(), true);
		} catch (InsufficientEnergyException e1) {
			assertEquals(false, true);
		}

		robotC.modificaEnergia(0);
		try {
			assertEquals(robotC.attacca(), true);
		} catch (InsufficientEnergyException e1) {
			assertEquals(true, true);
		}

	}

}
