package testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import eccezioni.InsufficientEnergyException;
import robot.Robot;
import robot.RobotLavoratore;

public class UTC_RobotLavoratore {
	
	RobotLavoratore robotL;
	
	@Before
	public void beforeTest() {
		robotL = new RobotLavoratore();
	}
	
	@Test
	public void testGetTipo() {
		//valore default robotLavoratore
		assertEquals("robotLavoratore", robotL.getTipo());
		assertNotEquals("robot", robotL.getTipo());
	}
	
	@Test
	public void testGetEnergiaCura() {
		// valore default 5
		assertEquals(5, robotL.getEnergiaCura());
		assertNotEquals(0, robotL.getEnergiaCura());
	}
	
	@Test
	public void testGetCapacit‡Cura() {
		// valore default 20
		assertEquals(20, robotL.getCapacit‡Cura());
		assertNotEquals(0, robotL.getCapacit‡Cura());
	}
	
	@Test
	public void testSpostaOgetto() {
		// robot puÚ spostare un ogetto solo se ha energia per sposare un ogetto
		robotL.modificaEnergia(50);
		try {
			assertEquals(robotL.spostaOggetto(), true);
		} catch (InsufficientEnergyException e) {
			assertEquals(false, true);
		}
		
		robotL.modificaEnergia(0);
		try {
			assertEquals(robotL.spostaOggetto(), true);
		} catch (InsufficientEnergyException e) {
			assertEquals(true, true);
		}
		
	}
	
	@Test
	public void testModificaEnergiaCura() {
		robotL.modificaEnergiaCura(15);
		assertEquals(15, robotL.getEnergiaCura());
		assertNotEquals(5, robotL.getEnergiaCura());		
	}
	
	@Test
	public void testModificaCapacit‡Cura() {
		robotL.modificaCapacit‡cura(50);
		assertEquals(50, robotL.getCapacit‡Cura());
		assertNotEquals(20, robotL.getCapacit‡Cura());		
	}

	@Test
	public void testCuraRobot() {
		
		/*Viene sottratta all'energia del RobotLavoratore l'energia che serve per curare un Robot, se il RobotLavoratore ha energia a sufficienza.
		Viene aggiunta all'energia del Robot da curare la capacit‡ di cura del RobotLavoratore se ha energia a sufficienza per curare.
		L'energia del Robot da curare non puÚ superare la sua massima energia. */
		
		Robot robotDaCurare = new Robot(); 
		robotDaCurare.modificaEnergia(70);
		
		robotL.modificaEnergia(11);
		robotL.modificaEnergiaCura(5);
		robotL.modificaCapacit‡cura(20);
		
		//caso in cui cura normalmente il robot
		try {
			robotL.curaRobot(robotDaCurare);
			assertEquals(robotL.getEnergia(), 6);
			assertEquals(robotDaCurare.getEnergia(), 90);
			
		} catch (InsufficientEnergyException e) {
			assertEquals(false, true);
		}
		
		//caso in cui con la cura il robot da curare supera l'energia max
		try {
			robotL.curaRobot(robotDaCurare);
			assertEquals(robotL.getEnergia(), 1);
			assertEquals(robotDaCurare.getEnergia(), 100);
			
		} catch (InsufficientEnergyException e) {

			assertEquals(false, true);
		}
		
		
		//caso in cui non si ha abbastanza energia per curare e viene lanciata l'eccezione
		try {
			robotL.curaRobot(robotDaCurare);
			assertEquals(false, true);
			
		} catch (InsufficientEnergyException e) {
			System.out.println(robotL.getEnergia());
			assertEquals(true, true);
		}
		
		
	}
	
}
