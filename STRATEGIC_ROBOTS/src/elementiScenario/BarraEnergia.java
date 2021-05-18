package elementiScenario;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BarraEnergia {

	private int energia;
	private int maxEnergia;
	
	public BarraEnergia(){
		energia = 0;
		maxEnergia = 100;
	}

	public BarraEnergia(int e){
		energia = e;
		maxEnergia = 100;
	}
	
	/**
	 * Disegna la BarraEnergia in un grapichs passato come parametro
	 * @param gg Grapichs su cui disegnare
	 */
	public void disegna(Graphics2D gg){
		
		Graphics2D g = gg;
		
		Rectangle energiaRettangolo = new Rectangle(10, 127, maxEnergia, 3);
		Rectangle barraEnergia = new Rectangle(10, 127, energia, 3);
		
		g.setColor(Color.BLACK);
		g.draw(energiaRettangolo);
				
		g.draw(barraEnergia);
		g.drawString("" + energia, 115, 133);
		
		if (energia > 25) g.setColor(Color.CYAN);
		else g.setColor(Color.RED);
		
		g.fill(barraEnergia);


		
	}

}
