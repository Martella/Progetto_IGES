package frame;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Questa classe genera un frame che da la possibilità all'utente di:
 * -Iniziare una nuova partita
 * -Conoscere le regole del gioco
 */
public class GiocoFrame extends JFrame{

	private final int FRAME_WIDTH = 500;
	private final int FRAME_HEIGHT = 500;

	public GiocoFrame(){
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		setTitle("Menù");
		creaPanelPrincipale();		
	}
	
	public void creaPanelPrincipale(){
		JPanel panelPrincipale = new JPanel();
		panelPrincipale.setLayout(new GridLayout(5, 1));
		JPanel titolo = titoloPanel();
		JPanel nuovaPartita = nuovaPartitaPanel();
		JPanel regole = regolePanel();
		JPanel esci = esciPanel();
		JPanel realizzatore = realizzatorePanel();
		panelPrincipale.add(titolo);
		panelPrincipale.add(nuovaPartita);
		panelPrincipale.add(regole);
		panelPrincipale.add(esci);
		panelPrincipale.add(realizzatore);
		add(panelPrincipale);
	}
	
	public JPanel titoloPanel(){
		JPanel panel = new JPanel();
		JLabel labelTitolo = new JLabel("StRaTeGiC  RoBoTs");
		labelTitolo.setPreferredSize(new Dimension(500,100));
		labelTitolo.setFont(new Font("Comic Sans MS" ,Font.ITALIC , 50));
		panel.add(labelTitolo);
		return panel;
	} 
	
	public JPanel nuovaPartitaPanel(){
		JPanel panel = new JPanel();
		JButton button = new JButton("Nuova Partita");
		button.setPreferredSize(new Dimension(200,100));
		panel.add(button);
		
		class clickButton implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				ImpostazioniNuovaPartitaFrame nuovaPartita = new ImpostazioniNuovaPartitaFrame();
				nuovaPartita.setLocationRelativeTo(null);
				nuovaPartita.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				nuovaPartita.setVisible(true);
				GiocoFrame.this.dispose();
			}
			
		}
		
		ActionListener listener = new clickButton();
		button.addActionListener(listener);
		
		return panel;
	} 

	public JPanel regolePanel(){
		JPanel panel = new JPanel();
		JButton button = new JButton("Regole del gioco");
		button.setPreferredSize(new Dimension(200,100));
		panel.add(button);
		class clickButton implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				RegoleGiocoFrame regoleGioco = new RegoleGiocoFrame();
				regoleGioco.setLocationRelativeTo(null);
				regoleGioco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				regoleGioco.setVisible(true);
				GiocoFrame.this.dispose();
			}
			
		}
		
		ActionListener listener = new clickButton();
		button.addActionListener(listener);
		
		return panel;
	} 
	public JPanel esciPanel(){
		JPanel panel = new JPanel();
		JButton button = new JButton("Esci");
		button.setPreferredSize(new Dimension(200,100));
		panel.add(button);
		
		class clickButton implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				GiocoFrame.this.dispose();
			}
			
		}
		
		ActionListener listener = new clickButton();
		button.addActionListener(listener);
		
		return panel;
	}
	
	public JPanel realizzatorePanel(){
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Realizzato da Antonio Martella");
		label.setPreferredSize(new Dimension(150,100));
		label.setFont(new Font("Comic Sans MS" ,Font.ITALIC , 10));
		panel.add(label);
		return panel;
	}

	
}
