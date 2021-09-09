package testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import elementiScenario.Casella;
import robot.RobotCombattente;

public class ITC_Casella {
	
	private Casella casella;
	private RobotCombattente robot;
	
	@Before
	public void beforeTest() {
		casella = new Casella("classic");
		robot = new RobotCombattente();
	}
	
	@Test
	public void testGetTipoScenario() {
		//valore default classic
		assertEquals("classic", casella.getTipoScenario());
	}
	
	@Test
	public void testIsDrawOggetto() {
		//valore default false
		assertEquals(false, casella.isDrawOggetto());
	}
	
	@Test
	public void testIsDrawEnergia() {
		//valore default false
		assertEquals(false, casella.isDrawEnergia());
	}

	@Test
	public void testIsRiempiCasella(){
		//valore default false
		assertEquals(false, casella.isRiempiCasella());
	}

	@Test
	public void testGetColCas() {
		//valore default Color.WHITE
		assertEquals(Color.WHITE, casella.getColCas());
	}

	
	@Test
	public void testDisegnaOggetto() {
		casella.disegnaOggetto(robot);
		assertEquals(true, casella.isDrawOggetto());
	}
	
	@Test
	public void testDisegnaBarraEnergia() {
		casella.disegnaBarraEnergia(robot);
		assertEquals(true, casella.isDrawEnergia());
	}	
	
	
	@Test
	public void testColoraCasella() {
		casella.coloraCasella(Color.BLACK);
		assertEquals(Color.BLACK, casella.getColCas());
		assertNotEquals(Color.WHITE, casella.getColCas());
		assertEquals(true, casella.isRiempiCasella());
	}
	
	@Test
	public void testDeColoraCasella() {
		casella.deColoraCasella();
		assertEquals(false, casella.isRiempiCasella());
	}

	@Test
	public void testSvuotaCasella() {
		casella.svuotaCasella();
		assertEquals(false, casella.isDrawOggetto());
		assertEquals(false, casella.isDrawEnergia());
		assertEquals(false, casella.isRiempiCasella());
	}

}
