package testing;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import elementiScenario.BancoRifornimenti;
import elementiScenario.Ostacolo;
import elementiScenario.Scenario;

public class ITC_Scenario {
	
	private Scenario scenario;
	
	@Before
	public void beforeTest() {
		scenario = new Scenario("classic");
	}
	
	@Test
	public void testGetMaxRighe() {
		//valore default 5
		assertEquals(5, scenario.getMaxRighe());
	}
	
	@Test
	public void testGetMaxColonne() {
		//valore default 10
		assertEquals(10, scenario.getMaxColonne());
	}
	
	@Test
	public void testGetGriglia(){
		String [][] griglia = scenario.getGriglia();
		if (griglia != null) assertEquals(true, true);
		else assertEquals(true, false);
		
		//valore default 5
		assertEquals(5, griglia.length);
	}
	
	@Test
	public void testGetArrayListOstacoli(){
		ArrayList<Ostacolo> ostacoloArray = scenario.getArrayListOstacoli();
		if (ostacoloArray != null) assertEquals(true, true);
		else assertEquals(true, false);
		
		//valore default 10
		assertEquals(10, ostacoloArray.size());
	}
	
	@Test
	public void testGetArrayListBancoRifornimenti(){
		ArrayList<BancoRifornimenti> bancoRifornimentiArray = scenario.getArrayListBancoRifornimenti();
		if (bancoRifornimentiArray != null) assertEquals(true, true);
		else assertEquals(true, false);
		
		//valore default 1
		assertEquals(1, bancoRifornimentiArray.size());
	}

	@Test
	public void testmModificaGrigliaCancella() {
		String [][] griglia = scenario.getGriglia();
		
		//inserisce stringa "null" nella posizione indicata
		scenario.modificaGrigliaCancella(1, 1);
		
		assertEquals("null", griglia[1][1]);
	}
	
}
