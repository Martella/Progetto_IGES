package testing;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Robot;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllo.Controllore;
import elementiScenario.Scenario;
import robot.RobotCombattente;
import robot.RobotLavoratore;

public class ITC_Controllore {
	
	private Controllore controllore;
	
	@Before
	public void beforeTest() {
		Scenario scenario = new Scenario("classic");
		JFrame frame = new JFrame();
		controllore = new Controllore(scenario, frame);
	}
	
	@Test
	public void testGetstato() {
		assertEquals(false, controllore.getStato());
	}
	
	@Test
	public void testGetStatoMossa() {
		assertEquals(false, controllore.getStatoMossa());
	}


	@Test
	public void testGetRobotCombattenteArray() {
		ArrayList<RobotCombattente> robotCombattenteArray = controllore.getRobotCombattenteArray();
		if (robotCombattenteArray != null) assertEquals(true, true);
		else assertEquals(true, false);
	}
	
	@Test
	public void testgetRobotLavoratoreeArray() {
		ArrayList<RobotLavoratore> robotLavoratoreArray = controllore.getRobotLavoratoreeArray();
		if (robotLavoratoreArray != null) assertEquals(true, true);
		else assertEquals(true, false);
	}
	
	
	@Test
	public void testGetInsiemeRobotCombattenteArray() {
		ArrayList<RobotCombattente> robotCombattenteArray = controllore.getInsiemeRobotCombattenteArray();
		if (robotCombattenteArray != null) assertEquals(true, true);
		else assertEquals(true, false);
	}
	
	@Test
	public void testgetInsiemeRobotLavoratoreeArray() {
		ArrayList<RobotLavoratore> robotLavoratoreArray = controllore.getRobotLavoratoreeArray();
		if (robotLavoratoreArray != null) assertEquals(true, true);
		else assertEquals(true, false);
	}
	
	
	@Test
	public void testAggiungiRobot() {
		
		for(int i = 0; i < 2; i++) controllore.aggiungiRobot(new RobotCombattente(Color.ORANGE));
		for(int i = 0; i < 2; i++) controllore.aggiungiRobot(new RobotLavoratore(Color.ORANGE));
		
		Scenario scenario = new Scenario("classic");
		JFrame frame = new JFrame();
		Controllore controllore2 = new Controllore(scenario, frame);
		for(int i = 0; i < 2; i++) controllore2.aggiungiRobot(new RobotCombattente(Color.ORANGE));
		for(int i = 0; i < 2; i++) controllore2.aggiungiRobot(new RobotLavoratore(Color.ORANGE));
		
		
		assertEquals(2, controllore.getRobotCombattenteArray().size());
		assertEquals(2, controllore.getRobotLavoratoreeArray().size());
		assertEquals(4, controllore.getInsiemeRobotCombattenteArray().size());
		assertEquals(4, controllore.getInsiemeRobotLavoratoreeArray().size());
		
	}
	
	@Test
	public void testModificaStatoMossa() {
		controllore.modificaStatoMossa(true);
		assertEquals(true, controllore.getStatoMossa());
	}
}
