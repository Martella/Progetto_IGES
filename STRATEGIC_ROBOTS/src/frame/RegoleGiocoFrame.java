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
		
		area.append("Sostanzialmente il gioco è caratterizzato da uno scenario in cui sono presenti degli ostacoli, un banco rifornimenti e 2 tipologie di robot che interagiscono tra di loro. \r\n" + 
				"Dopo aver scelto le varie impostazioni della partita, l’utente può iniziare a giocare contro un altro utente sullo stesso computer alternando l’invio delle mosse da compiere.\r\n" + 
				"Ogni utente può controllare una squadra di robot composta da robot combattenti (riconoscibili graficamente da una spada) e robot lavoratori (riconoscibili graficamente da una chiave inglese).\r\n" + 
				"I robot combattenti possono spostarsi all’interno dello scenario e possono attaccare ostacoli (spostandosi nella loro casella) o altri robot facendo diminuire la loro energia. \r\n" + 
				"I robot lavoratori possono spostarsi all’interno dello scenario, possono spostare ostacoli (spostandosi nella loro casella) e possono curare altri robot facendo aumentare la loro energia.\r\n" + 
				"Ogni azione richiede un quantitativo di energia fissato. Se l’energia residua non è sufficiente per compiere un’azione, allora questa non potrà essere eseguita.\r\n" + 
				"Entrambi i robot possono ricaricare la propria energia tramite il banco rifornimenti presente nello scenario.\r\n" + 
				"Il banco rifornimento è indistruttibile, non può essere spostato e ha una riserva di energia per le ricariche. Per interagire col banco rifornimenti bisogna recarsi vicino con un robot e premere la barra spaziatrice.\r\n" + 
				"\r\n" + 
				"Il gioco è organizzato in round. In ogni round viene scelto casualmente un robot che può essere controllato dall’utente. Il robot controllato è evidenziato.\r\n" + 
				"Ogni utente può effettuare una sola mossa durante il round, dopodiché passa il controllo all’avversario.\r\n" + 
				"La mossa termina quando l’utente preme il tasto invio.\r\n" + 
				"Quando il giocatore esegue una delle seguenti azioni non può continuare ad eseguire altre operazioni, di conseguenza è costretto a premere invio per passare il controllo all’altro giocatore:\r\n" + 
				"•	L’utente attacca un robot attraverso il robot combattente controllato.\r\n" + 
				"•	L’utente attacca un ostacolo attraverso il robot combattente controllato.\r\n" + 
				"•	L’utente cura un robot attraverso un robot lavoratore controllato.\r\n" + 
				"•	L’utente ricarica l’energia di un robot controllato tramite il banco dei rifornimenti \r\n" + 
				"\r\n" + 
				"I robot e gli ostacoli che non hanno più energia dopo aver subito un attacco vengono distrutti. La partita termina quando uno dei due giocatori non ha più robot combattenti a disposizione. \r\n" + 
				"");
	
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
