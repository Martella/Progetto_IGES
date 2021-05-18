package frame;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import generale.GameManager;

/**
 * Questa classe Ë un frame che da la possibilit‡ all'utente di settare le impostazioni di una nuova partita.
 */
public class ImpostazioniNuovaPartitaFrame extends JFrame{

	private final int FRAME_WIDTH = 500;
	final int FRAME_HEIGHT = 300;
	private JRadioButton classic;
	private JRadioButton fantasy;
	private JRadioButton casuale;
	private JRadioButton controGiocatore;
	private JRadioButton controComputer;
	private JRadioButton robots2;
	private JRadioButton robots4;



	public ImpostazioniNuovaPartitaFrame(){
		setSize(FRAME_WIDTH, FRAME_WIDTH);
		setResizable(false);
		setTitle("Impostazioni");
		add(creaPanelPrincipale());
	}
	
	public JPanel creaPanelPrincipale(){
		JPanel panelPrincipale = new JPanel();
		panelPrincipale.setLayout(new GridLayout(4, 1));
		panelPrincipale.add(creaSceltaScenario());
		panelPrincipale.add(creaSceltaModalit‡());
		panelPrincipale.add(creaSceltaRobots());
		panelPrincipale.add(creaButtonGioca());		
		return panelPrincipale;
	}
	
	public JPanel creaSceltaScenario(){
		JPanel panel = new JPanel(); 
		casuale = new JRadioButton("Casuale");
		classic = new JRadioButton("Classic");
		fantasy = new JRadioButton("Fantasy");
		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(classic);
		gruppo.add(fantasy);
		gruppo.add(casuale);
		classic.setSelected(true);
		panel.add(classic);
		panel.add(fantasy);		
		panel.add(casuale);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Scenario"));
		return panel;
	}
	
	public JPanel creaSceltaModalit‡(){
		JPanel panel = new JPanel(); 
		controGiocatore = new JRadioButton("Giocatore1 vs Giocatore2");
		controComputer = new JRadioButton("Giocatore1 vs Computer");
		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(controGiocatore);
		gruppo.add(controComputer);
		controGiocatore.setSelected(true);
		panel.add(controGiocatore);
		panel.add(controComputer);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Modalit‡"));
		return panel;
		
	}
	
	public JPanel creaSceltaRobots(){
		JPanel panel = new JPanel(); 
		robots4 = new JRadioButton("4 vs 4");
		robots2 = new JRadioButton("2 vs 2");
		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(robots4);
		gruppo.add(robots2);
		robots4.setSelected(true);
		panel.add(robots4);
		panel.add(robots2);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Robots"));
		return panel;
		
	}
	
	public JPanel creaButtonGioca(){
		JPanel panel = new JPanel(); 
		JButton button = new JButton("GIOCA");
		button.setPreferredSize(new Dimension(200,100));
		panel.add(button);
		
		class clickButton implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				String tipoScenario;
				String modalit‡Partita;
				int numRobots;
				
				if(classic.isSelected()) tipoScenario = "classic";
				else if(fantasy.isSelected()) tipoScenario = "fantasy";
				else tipoScenario = "casuale";
				
				if(controGiocatore.isSelected()) modalit‡Partita = "controGiocatore";
				else modalit‡Partita = "controComputer";
				
				if(robots4.isSelected()) numRobots = 4;
				else numRobots = 2;
				
				GameManager manager = new GameManager();
				manager.startNuovaPartita(tipoScenario, modalit‡Partita, numRobots);
				
				ImpostazioniNuovaPartitaFrame.this.dispose();
			}
			
		}
		
		ActionListener listener = new clickButton();
		button.addActionListener(listener);
		
		return panel;
		
	}

}
