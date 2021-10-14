package controllo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import elementiScenario.Scenario;
import frame.FineRoundFrame;
import frame.ImpostazioniNuovaPartitaFrame;
import frame.MessaggioFrame;
import remote.DatiPartita;
import robot.RobotCombattente;
import robot.RobotLavoratore;

/**
 * Questa classe crea una nuova partita */
public class GameManager {
	
	private String tipoScenario;
	private String modalit‡Partita;
	private JFrame frame;
	private final int FRAME_WIDTH = 1366;
	private final int FRAME_HEIGHT = 730;
	private Scenario scenario;
	private ControlloreInterattivo controlloreGiocatore1;
	private ControlloreInterattivo controlloreGiocatore2;
	private ControlloreNonInterattivo controlloreComputer;
	private int [] numeroMossa ;


	public GameManager(){

		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("STRATEGIC ROBOTS");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setJMenuBar(creaMen˘Bar());
		
	}
	

	/**
	 * Crea una nuova partita in base ai parametri ricevuti.
	 * Crea uno scenario e lo aggiunge al frame.
	 * @param s rappresenta il tipo di scenario da creare
	 * @param m rappresenta la modalit‡ della partita
	 * @param n rappresenta il numero dei Robot per squadra
	 */
	public void startNuovaPartita(String s, String m, int n){
		
		tipoScenario = s;
		modalit‡Partita = m;
		int numRobots = n;
		
		
		scenario = new Scenario(tipoScenario);
		controlloreGiocatore1  = new ControlloreInterattivo(scenario, frame);
		controlloreGiocatore2  = new ControlloreInterattivo(scenario, frame);
		controlloreComputer  = new ControlloreNonInterattivo(scenario, frame);
		
		
		for(int i = 1; i <= numRobots/2; i++) controlloreGiocatore1.aggiungiRobot(new RobotCombattente(Color.ORANGE));
		for(int i = 1; i <= numRobots/2; i++) controlloreGiocatore1.aggiungiRobot(new RobotLavoratore(Color.ORANGE));
		controlloreGiocatore1.start(scenario.getArrayListOstacoli(), scenario.getArrayListBancoRifornimenti());

		
		if (modalit‡Partita.equals("controGiocatore")){
			for(int i = 1; i <= numRobots/2; i++) controlloreGiocatore2.aggiungiRobot(new RobotCombattente(Color.GREEN));
			for(int i = 1; i <= numRobots/2; i++) controlloreGiocatore2.aggiungiRobot(new RobotLavoratore(Color.GREEN));
			controlloreGiocatore2.start(scenario.getArrayListOstacoli(), scenario.getArrayListBancoRifornimenti());
		}

		else if (modalit‡Partita.equals("controComputer")){
			for(int i = 1; i <= numRobots/2; i++) controlloreComputer.aggiungiRobot(new RobotCombattente(Color.GREEN));
			for(int i = 1; i <= numRobots/2; i++) controlloreComputer.aggiungiRobot(new RobotLavoratore(Color.GREEN));
			controlloreComputer.start(scenario.getArrayListOstacoli(), scenario.getArrayListBancoRifornimenti());
			Timer timer = new Timer();
			TimerMossaComputer mossaComputer = new TimerMossaComputer(controlloreComputer);
			timer.schedule(mossaComputer, 2000, 500);
		}
		
		//se la dichiaravo come varabile non me la faceva modificare nell'evento
		numeroMossa = new int [1];
		numeroMossa[0] = 0;
		
		Random mossaRandom = new Random();
		
		if(mossaRandom.nextInt(2)== 0)controlloreGiocatore1.attiva();
		else if (modalit‡Partita.equals("controGiocatore")) controlloreGiocatore2.attiva();
		else if (modalit‡Partita.equals("controComputer")) controlloreComputer.attiva();
		

		frame.add(scenario);
		frame.setVisible(true);
		
		KeyListener listenerCambioGiocatore = new CambioGiocatore();
		frame.addKeyListener(listenerCambioGiocatore);

	}
	
	
	
	
	/**
	 * Evento per passare il controllo ad un altro controllore premendo il tasto Invio
	 */
	private class CambioGiocatore implements KeyListener{

		public void keyPressed(KeyEvent e) {
			
			if (e.getKeyCode() == 10){
				
				numeroMossa[0]++;
				
				
				//ogni 2 mosse finisce il round e viene scelto casualmente il controllore che inizia l'altro round
				// decommentare per inserire i round
				/*if(numeroMossa[0] % 2 == 0){
					FineRoundFrame fine = new FineRoundFrame(numeroMossa[0] / 2);
					fine.setVisible(true);

					Random mossaRandom = new Random();
					if(mossaRandom.nextInt(2)== 0){
						controlloreGiocatore1.attiva();
						if (modalit‡Partita.equals("controGiocatore")) controlloreGiocatore2.disattiva();
						if (modalit‡Partita.equals("controComputer")) controlloreComputer.disattiva();
					}
					else if (modalit‡Partita.equals("controGiocatore")){
						controlloreGiocatore2.attiva();
						controlloreGiocatore1.disattiva();
					}
					else if (modalit‡Partita.equals("controComputer")){
						controlloreComputer.attiva();
						controlloreGiocatore1.disattiva();
					}
				}
				*/
				if(controlloreGiocatore1.getStato()){
					controlloreGiocatore1.disattiva();
					if (modalit‡Partita.equals("controGiocatore")){
						controlloreGiocatore2.cambiaRobot();
						controlloreGiocatore2.attiva();
					}
					else if (modalit‡Partita.equals("controComputer")){
						controlloreComputer.cambiaRobot();
						controlloreComputer.attiva();
					}
				}
				else if (modalit‡Partita.equals("controGiocatore")){
					controlloreGiocatore2.disattiva();
					controlloreGiocatore1.cambiaRobot();
					controlloreGiocatore1.attiva();
				}
				
				else if (modalit‡Partita.equals("controComputer")){
					controlloreComputer.disattiva();
					controlloreGiocatore1.cambiaRobot();
					controlloreGiocatore1.attiva();
				}

			}				

		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
		
	}

			
	private class clickNuovaPartita implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ImpostazioniNuovaPartitaFrame nuovaPartita = new ImpostazioniNuovaPartitaFrame();
			nuovaPartita.setLocationRelativeTo(null);
			nuovaPartita.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			nuovaPartita.setVisible(true);
			frame.setVisible(false);
		}
					
	}
	
	public JMenuBar creaMen˘Bar(){
		
		JMenuBar men˘Bar = new JMenuBar();
		
		JMenu men˘File = new JMenu("Opzioni");
		
		JMenuItem men˘Salva = new JMenuItem("Salva Partita");
		JMenuItem men˘Nuova = new JMenuItem("Nuova Partita");
		
		men˘File.add(men˘Salva);
		men˘File.add(men˘Nuova);
		
		men˘Bar.add(men˘File);
		
		ActionListener listenerNuovaPartita = new clickNuovaPartita();		
		men˘Nuova.addActionListener(listenerNuovaPartita);
		
		ActionListener listenerSalvaPartita = new clickSalvaPartita();		
		men˘Salva.addActionListener(listenerSalvaPartita);
		
		return men˘Bar;
	}
	
	/**
	 * Evento che salva un oggetto DatiPartita contenente i dati della partita in un file. 
	 */
	private class clickSalvaPartita implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			DatiPartita datiPartitaSalvata = new DatiPartita();
			
			datiPartitaSalvata.modificaModalit‡Partita(modalit‡Partita);
			datiPartitaSalvata.modificaNumeroMossa(numeroMossa);
			datiPartitaSalvata.modificaScenario(scenario);
			datiPartitaSalvata.modificaControlloreInterattivo1(controlloreGiocatore1);
			datiPartitaSalvata.modificaControlloreInterattivo2(controlloreGiocatore2);


			String mex = new String("Partita salvata");
			
			try {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("salvataggio\\partitaSalvata.dat"));
				out.writeObject(datiPartitaSalvata);			
				out.close();
				
			} catch (FileNotFoundException e1) {
				mex = "Errore nel salvataggio";
				e1.printStackTrace();
			} catch (IOException e2) {
				mex = "Errore nel salvataggio";
				e2.printStackTrace();
			}
			
			MessaggioFrame salvataggioframe = new MessaggioFrame(mex);
			salvataggioframe.setVisible(true);


		}
					
	}
	
	/**
	 * Carica una partita salvata.
	 * @throws FileNotFoundException se avviene qualche errore nel caricamento dell'oggetto DatiPartitaSalvata
	 * @throws IOException se avviene qualche errore nel caricamento dell'oggetto DatiPartitaSalvata
	 * @throws ClassNotFoundException se avviene qualche errore nel caricamento dell'oggetto DatiPartitaSalvata
	 */
	public void startPartitaSalvata() throws FileNotFoundException, IOException, ClassNotFoundException{
		File file = new File("salvataggio\\partitaSalvata.dat");

		if (file.exists()){
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			DatiPartita datiPartitaSalvata = (DatiPartita) in.readObject();
			in.close();
			
			modalit‡Partita = datiPartitaSalvata.getModalit‡Partita();
			scenario = datiPartitaSalvata.getScenario();
			controlloreGiocatore1 = datiPartitaSalvata.getControlloreInterattivo1();
			controlloreGiocatore2 = datiPartitaSalvata.getControlloreInterattivo2();
			numeroMossa = datiPartitaSalvata.getNumeroMossa();
			
			controlloreGiocatore1.aggiornaRobot();
			controlloreGiocatore1.modificaFrame(frame);
			
			controlloreGiocatore2.aggiornaRobot();
			controlloreGiocatore2.modificaFrame(frame);

			controlloreGiocatore1.aggiungiKeyListner();
			controlloreGiocatore2.aggiungiKeyListner();
			



			frame.add(scenario);
			frame.setVisible(true);
			
			KeyListener listenerCambioGiocatore = new CambioGiocatore();
			frame.addKeyListener(listenerCambioGiocatore);
		} else {
			MessaggioFrame messaggio = new MessaggioFrame("Nessuna partita trovata!", 170, 100);
			messaggio.setVisible(true);
		}
		
	}
	

}
