package elementiScenario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

import interfacce.Disegnabile;
import interfacce.Energetico;

public class Casella extends JComponent {

	private Disegnabile oggetto;
	private Energetico oggettoEnergetico;
	private boolean drawOggetto;
	private boolean drawEnergia;
	private boolean riempiCasella;
	private Color colCas;
	private String tipoScenario;
	
	/**
	 * 
	 * @param s tipo dello scenario
	 */
	public Casella(String s){
		tipoScenario = s;
		drawOggetto = false;
		drawEnergia = false;
		riempiCasella = false;
		colCas = Color.WHITE;
	}

	public void paintComponent (Graphics gg){
		Graphics2D g = (Graphics2D) gg;

		Rectangle rett = new Rectangle(5, 5, 1000, 1000);
		g.setColor(Color.BLACK);

		if (tipoScenario.equals("classic")) g.draw(rett);

				
		if(riempiCasella) {
			g.setColor(colCas);
			g.fill(rett);
		}
		
		if (drawOggetto) oggetto.disegna(g);

		if (drawEnergia){
			BarraEnergia barra = new BarraEnergia(oggettoEnergetico.getEnergia());
			barra.disegna(g);			
		}



	}
	
	public void disegnaOggetto(Disegnabile o){
		oggetto = o;
		drawOggetto = true;
		repaint();
	}

	public void disegnaBarraEnergia(Energetico o){
		oggettoEnergetico = o;
		drawEnergia = true;
		repaint();
	}
	
	public void coloraCasella(Color c){
		colCas = c;
		riempiCasella = true;
		repaint();
	}
	
	public void deColoraCasella(){
		riempiCasella = false;
		repaint();
		
	}

	public void svuotaCasella(){
		drawOggetto = false;
		drawEnergia = false;
		riempiCasella = false;
		repaint();
	}

}
