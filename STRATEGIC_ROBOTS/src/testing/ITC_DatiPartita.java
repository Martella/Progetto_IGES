package testing;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllo.ControlloreInterattivo;
import elementiScenario.BancoRifornimenti;
import elementiScenario.Scenario;
import remote.DatiPartita;
import robot.RobotCombattente;
import robot.RobotLavoratore;

public class ITC_DatiPartita {
	
	private DatiPartita datiPartita;
	
	@Before
	public void beforeTest() {
		datiPartita = new DatiPartita();
	}
	

	@Test
	public void testModificaAndGetModalit‡Partita() {
		// valore di default null
		assertEquals(null, datiPartita.getModalit‡Partita());
		datiPartita.modificaModalit‡Partita("classic");
		assertEquals("classic", datiPartita.getModalit‡Partita());
	}

	@Test
	public void testModificaAndGetNumeroMossa() {
		// valore di default null
		assertEquals(null, datiPartita.getNumeroMossa());
		
		int [] numeroMossa = new int [1];
		numeroMossa[0] = 5;
		datiPartita.modificaNumeroMossa(numeroMossa);
		
		int [] numeroMossaSalvato = datiPartita.getNumeroMossa();
		
		assertEquals(numeroMossa.length, numeroMossaSalvato.length);
		assertEquals(5, numeroMossaSalvato[0]);
	}

	@Test
	public void testModificaAndGetScenario() {
		
		// valore di default null
		assertEquals(null, datiPartita.getScenario());
	
		Scenario scenario = new Scenario("classic");
	
		datiPartita.modificaScenario(scenario);

		Scenario scenario2 = datiPartita.getScenario();
				
		
		//valore default 5
		assertEquals(5, scenario2.getMaxRighe());
		//valore default 10
		assertEquals(10, scenario2.getMaxColonne());
		
		String [][] griglia = scenario2.getGriglia();
		if (griglia != null) assertEquals(true, true);
		else assertEquals(true, false);
		
		//valore default 5
		assertEquals(5, griglia.length);
		

		ArrayList<BancoRifornimenti> bancoRifornimentiArray = scenario2.getArrayListBancoRifornimenti();
		if (bancoRifornimentiArray != null) assertEquals(true, true);
		else assertEquals(true, false);
		
		//valore default 1
		assertEquals(1, bancoRifornimentiArray.size());

	}
	
	
	@Test
	public void testModificaAndGetControlloreInterattivo1() {
		
		// valore di default null
		assertEquals(null, datiPartita.getControlloreInterattivo1());
		
		Scenario scenario = new Scenario("classic");
		JFrame frame = new JFrame();
		ControlloreInterattivo controllore1 = new ControlloreInterattivo(scenario, frame);
		for(int i = 0; i < 2; i++) controllore1.aggiungiRobot(new RobotCombattente(Color.ORANGE));
		for(int i = 0; i < 2; i++) controllore1.aggiungiRobot(new RobotLavoratore(Color.ORANGE));
		
		ControlloreInterattivo controllore2 = new ControlloreInterattivo(scenario, frame);
		for(int i = 0; i < 2; i++) controllore2.aggiungiRobot(new RobotCombattente(Color.ORANGE));
		for(int i = 0; i < 2; i++) controllore2.aggiungiRobot(new RobotLavoratore(Color.ORANGE));
		
		datiPartita.modificaControlloreInterattivo1(controllore1);
		
		ControlloreInterattivo controlloreSalvato = datiPartita.getControlloreInterattivo1();
		
		assertEquals(2, controlloreSalvato.getRobotCombattenteArray().size());
		assertEquals(2, controlloreSalvato.getRobotLavoratoreeArray().size());
		assertEquals(4, controlloreSalvato.getInsiemeRobotCombattenteArray().size());
		assertEquals(4, controlloreSalvato.getInsiemeRobotLavoratoreeArray().size());
		
	}
	
	@Test
	public void testModificaAndGetControlloreInterattivo2() {
		
		// valore di default null
		assertEquals(null, datiPartita.getControlloreInterattivo2());
		
		Scenario scenario = new Scenario("classic");
		JFrame frame = new JFrame();
		ControlloreInterattivo controllore1 = new ControlloreInterattivo(scenario, frame);
		for(int i = 0; i < 2; i++) controllore1.aggiungiRobot(new RobotCombattente(Color.ORANGE));
		for(int i = 0; i < 2; i++) controllore1.aggiungiRobot(new RobotLavoratore(Color.ORANGE));
		
		ControlloreInterattivo controllore2 = new ControlloreInterattivo(scenario, frame);
		for(int i = 0; i < 2; i++) controllore2.aggiungiRobot(new RobotCombattente(Color.ORANGE));
		for(int i = 0; i < 2; i++) controllore2.aggiungiRobot(new RobotLavoratore(Color.ORANGE));
		
		datiPartita.modificaControlloreInterattivo2(controllore1);
		
		ControlloreInterattivo controlloreSalvato = datiPartita.getControlloreInterattivo2();
		
		assertEquals(2, controlloreSalvato.getRobotCombattenteArray().size());
		assertEquals(2, controlloreSalvato.getRobotLavoratoreeArray().size());
		
		// 8 perchË tiene conto anche del test testModificaAndGetControlloreInterattivo1() 
		assertEquals(8, controlloreSalvato.getInsiemeRobotCombattenteArray().size());
		assertEquals(8, controlloreSalvato.getInsiemeRobotLavoratoreeArray().size());
		
	}

}
