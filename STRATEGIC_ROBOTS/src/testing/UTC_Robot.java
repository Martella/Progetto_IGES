package testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import eccezioni.InsufficientEnergyException;
import robot.Robot;

public class UTC_Robot {
	
	Robot robot;
	
	@Before
	public void testBefore() {
		robot = new Robot();
	}
	

	@Test
	public void testRobotGetTipo() {
		//valore default robot
		assertEquals("robot", robot.getTipo());
		assertNotEquals("prova", robot.getTipo());
	}
	
	@Test
	public void testRobotGetX() {
		// valore default 0
		assertEquals(0, robot.getX());
		assertNotEquals(1, robot.getX());
	}
	
	@Test
	public void testRobotGetY() {
		// valore default 0
		assertEquals(0, robot.getY());
		assertNotEquals(1, robot.getY());
	}
	
	@Test
	public void testRobotGetEnergia() {
		// valore default 100
		assertEquals(100, robot.getEnergia());
		assertNotEquals(1, robot.getEnergia());
	}
	
	@Test
	public void testRobotGetEnergiaSpostamento() {
		// valore default 1
		assertEquals(1, robot.getEnergiaSpostamento());
		assertNotEquals(0, robot.getEnergiaSpostamento());
	}
	
	@Test
	public void testRobotGetMaxEnergia() {
		// valore default 100
		assertEquals(100, robot.getMaxEnergia());
		assertNotEquals(1, robot.getMaxEnergia());
	}
	
	@Test
	public void testRobotGetColore() {
		//valore default Color.LIGHT_GRAY
		assertEquals(Color.LIGHT_GRAY, robot.getColore());
		assertNotEquals(Color.BLACK, robot.getColore());
	}
	
	
	@Test
	public void testRobotModificaEnergia() {
		//range ammissibile 0-100
		robot.modificaEnergia(50);
		assertEquals(50, robot.getEnergia());
		robot.modificaEnergia(-10);
		assertEquals(0, robot.getEnergia());
		robot.modificaEnergia(110);
		assertEquals(100, robot.getEnergia());	
	}
	

	@Test
	public void TestRobotSpostamentoX() {
		// robot si sposta solo se ha energia altrimenti lancia eccezione
		
		robot.modificaEnergia(50);
		try {
			assertEquals(robot.spostamentoX(1), true);
		} catch (InsufficientEnergyException e) {
			assertEquals(false, true);
		}
		
		robot.modificaEnergia(0);
		try {
			assertEquals(robot.spostamentoX(1), true);
		} catch (InsufficientEnergyException e) {
			assertEquals(true, true);
		}
		
	}
	
	public void TestRobotspostamentoY() {
		// robot si sposta solo se ha energia per lo spostamento altrimenti lancia eccezione
		
		robot.modificaEnergia(50);
		try {
			assertEquals(robot.spostamentoY(1), true);
		} catch (InsufficientEnergyException e) {
			assertEquals(false, true);
		}
		
		robot.modificaEnergia(0);
		try {
			assertEquals(robot.spostamentoY(1), true);
		} catch (InsufficientEnergyException e) {
			assertEquals(true, true);
		}
		
	}

	@Test
	public void testRobotModificaX() {
		robot.modificaX(10);
		assertEquals(10, robot.getX());
		assertNotEquals(11, robot.getX());
	}

	@Test
	public void testRobotModificaY() {
		robot.modificaY(10);
		assertEquals(10, robot.getY());
		assertNotEquals(11, robot.getY());
	}

	@Test
	public void testRobotModificaEnergiaSpostamento() {
		robot.modificaEnergiaSpostamento(10);
		assertEquals(10, robot.getEnergiaSpostamento());
		assertNotEquals(1, robot.getEnergiaSpostamento());
	}
	
	@Test
	public void testRobotModificaColore() {
		robot.modificaColore(Color.BLACK);
		assertEquals(Color.BLACK, robot.getColore());
		assertNotEquals(Color.LIGHT_GRAY, robot.getColore());
	}

	
}
