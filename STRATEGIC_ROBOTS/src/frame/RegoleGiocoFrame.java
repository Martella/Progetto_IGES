package frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RegoleGiocoFrame extends JFrame{

	private final int FRAME_WIDTH = 500;
	private final int FRAME_HEIGHT = 500;
	
	public RegoleGiocoFrame(){
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		setTitle("Regole del gioco");
		creaPanelPrincipale();		
	}
	
	public void creaPanelPrincipale(){
		JPanel panelPrincipale = new JPanel();
		panelPrincipale.setLayout(new BorderLayout());

		JScrollPane scroll = new JScrollPane(creaJTexArea());
		panelPrincipale.add(scroll, BorderLayout.CENTER);
		panelPrincipale.add(creaBottone(), BorderLayout.SOUTH);
		add(panelPrincipale);
	}
	
	public JTextArea creaJTexArea(){
		JTextArea area = new JTextArea();
		
		area.append("\n-Il gioco avviene per round.\n\n-In ogni round, ciascun robot esegue una sola mossa in base alla scelta del suo \n controllore.\n\n"
				+ "-Una mossa di un robot può consistere in un’azione effettiva oppure nella decisione di\n non compiere alcuna azione.\n\n-Premendo il tasto "
				+ "INVIO si passa la mossa all’avversario.\n\n-L’ordine di esecuzione in ogni round è stabilito in maniera casuale assumendo \nequi-probabilità.\n\n"
				+ "-Un robot ha una riserva di energia e può compiere una serie di azioni tra cui spostarsi.\n Per spostarsi o compiere un’altra azione un robot consuma "
				+ "energia, ciò avviene\n premendo i tasti direzionali.\n\n-Ogni azione richiede un"
				+ "quantitativo di energia fissato.\n Se l’energia residua non è sufficiente per compiere"
				+ "un’azione, allora questa non potrà\n essere eseguita.\n\n-Esistono due tipologie di robot: lavoratori e combattenti.\n\n1. Lavoratori. Possono ricaricare l'energia degli altri robot;\n"
				+ "Combattenti. Possono combattere con altri robot e distruggere ostacoli;\n"
				+ "\n-Un ostacolo ha un valore di resistenza (bisogna superare questo valore per demolirlo)\n "
				+ "\n\n-Un banco rifornimento ha una riserva di energia (per le ricariche); Il banco di rifornimento è indistruttibile e non può essere spostato.\n"
				+ " Per interagire col banco rifornimenti bisogna recarsi vicino con un robot e premere la\n barra spaziatrice."
				+ "\n\n-Perde la squadra che non più Robot Combattenti a disposizione.\n\n");
	
		area.setEditable(false);

		
		return area;
	}
	
	public JButton creaBottone(){
		JButton button = new JButton("Torna al menù");
		
		class clickButton implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				
				GiocoFrame start = new GiocoFrame();
				start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				start.setLocationRelativeTo(null);
				start.setVisible(true);
				
				RegoleGiocoFrame.this.dispose();
				
			}
			
		}
		
		ActionListener listener = new clickButton();
		button.addActionListener(listener);

		return button;
	}

}
