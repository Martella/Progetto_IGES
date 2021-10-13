package remote;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ServerCloneException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controllo.ControlloreInterattivo;
import controllo.ControlloreNonInterattivo;
import controllo.TimerMossaComputer;
import elementiScenario.Scenario;
import frame.FineRoundFrame;
import frame.ImpostazioniNuovaPartitaFrame;
import frame.MessaggioFrame;
import robot.Robot;
import robot.RobotCombattente;
import robot.RobotLavoratore;


public class Client2 extends UnicastRemoteObject implements ClientCallbackRemote{
	

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
	
	private static ServerCallbackRemote objCallback;
	private static ServerGestioneStatoRemote objStato;
	
	private static DatiPartita datiPartitaServer;
	
	protected Client2() throws RemoteException {
		super();
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("STRATEGIC ROBOTS");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setJMenuBar(creaMen˘Bar());
	}

	public static void main(String[] args) {

		Logger log = Logger.getLogger("log");
		
		Client2 client = null;
		objCallback = null;
		objStato = null;
		datiPartitaServer = null;
		
		try {
			client = new Client2();
			
			log.info("Sto cercando l'ogetto remoto...");
			Object obj = Naming.lookup("rmi://localhost/ServerRobot");

			
			objCallback = (ServerCallbackRemote) obj;
			objStato = (ServerGestioneStatoRemote) obj;
			
			log.info("ogetto trovato");
			

			
			objCallback.registerForCallBack(client);
			
			datiPartitaServer = objStato.getStatoServer();
			//client.aggiornaDatiPartitaServer();
			/*if (datiPartitaServer == null) {
				client.startNuovaPartita("classic", "controGiocatore", 6);
				
				Robot r = new Robot();
				r.modificaX(10);
				objStato.aggiornaRobot(r);
				//objStato.aggiornaStatoServer(client.getDatiPartitaServer());
			}*/
			//else System.out.println("non vuoto");
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void aggiornaStatoClient(DatiPartita dp) throws RemoteException {
		// TODO Auto-generated method stub
		
		DatiPartita datiPartita = dp;
		
		modalit‡Partita = datiPartita.getModalit‡Partita();
		scenario = datiPartita.getScenario();
		controlloreGiocatore1 = datiPartita.getControlloreInterattivo1();
		controlloreGiocatore2 = datiPartita.getControlloreInterattivo2();
		numeroMossa = datiPartita.getNumeroMossa();
		
		controlloreGiocatore1.modificaFrame(frame);
		controlloreGiocatore1.aggiornaRobot();
		
		controlloreGiocatore2.modificaFrame(frame);
		controlloreGiocatore2.aggiornaRobot();
		
		frame.add(scenario);
		frame.setVisible(true);
		
		KeyListener listenerCambioGiocatore = new CambioGiocatore();
		frame.addKeyListener(listenerCambioGiocatore);

	}

	public void aggiornaDatiPartitaServer(){
		datiPartitaServer= new DatiPartita();
		
		datiPartitaServer.modificaModalit‡Partita(modalit‡Partita);
		datiPartitaServer.modificaNumeroMossa(numeroMossa);
		datiPartitaServer.modificaScenario(scenario);
		datiPartitaServer.modificaControlloreInterattivo1(controlloreGiocatore1);
		datiPartitaServer.modificaControlloreInterattivo2(controlloreGiocatore2);
		
		MessaggioFrame infoFrame = new MessaggioFrame("dati inviati al server");
		infoFrame.setVisible(true);
		
		//objCallback.aggiornaStatoServer(datiPartita);
		
	}
	
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
		
		aggiornaDatiPartitaServer();
	}
	
	/**
	 * Evento per passare il controllo ad un altro controllore premendo il tasto Invio
	 */
	private class CambioGiocatore implements KeyListener{

		public void keyPressed(KeyEvent e) {
			
			if (e.getKeyCode() == 10){
				
				numeroMossa[0]++;
				
				//ogni 2 mosse finisce il round e viene scelto casualmente il controllore che inizia l'altro round
				if(numeroMossa[0] % 2 == 0){
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
		
		JMenuItem men˘Nuova = new JMenuItem("Nuova Partita");
		
		men˘File.add(men˘Nuova);
		
		men˘Bar.add(men˘File);
		
		ActionListener listenerNuovaPartita = new clickNuovaPartita();		
		men˘Nuova.addActionListener(listenerNuovaPartita);
		
		
		return men˘Bar;
	}
	
	public DatiPartita getDatiPartitaServer() {
		return datiPartitaServer;
	}


}
