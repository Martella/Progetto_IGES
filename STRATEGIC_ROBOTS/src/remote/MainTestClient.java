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


public class MainTestClient extends UnicastRemoteObject implements ClientCallbackRemote{
	

	private String tipoScenario;
	private String modalitàPartita;
	private JFrame frame;
	private final int FRAME_WIDTH = 1366;
	private final int FRAME_HEIGHT = 730;
	private Scenario scenario;
	private ControlloreInterattivo controlloreGiocatore1;
	private ControlloreInterattivo controlloreGiocatore2;
	private int [] numeroMossa ;
	
	private static Logger log;
	private static ServerCallbackRemote objCallback;
	private static ServerGestioneStatoRemote objStato;
	
	private static DatiPartita datiPartitaServer;
	
	private int controlloreAssegnato;
	private Boolean flagMossa;
	
	protected MainTestClient() throws RemoteException {
		super();

		nuovoFrame();
		
		flagMossa = false;
	}

	public static void main(String[] args) {

		log = Logger.getLogger("log");
		
		MainTestClient client = null;
		objCallback = null;
		objStato = null;
		datiPartitaServer = null;
		
		try {
			client = new MainTestClient();
			
			log.info("Sto cercando l'ogetto remoto...");
			Object obj = Naming.lookup("rmi://localhost/ServerRobot");

			
			objCallback = (ServerCallbackRemote) obj;
			objStato = (ServerGestioneStatoRemote) obj;
			
			log.info("ogetto trovato");
			

			
			objCallback.registerForCallBack(client);
			
			//datiPartitaServer = objStato.getStatoServer();
			
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
		
		//DatiPartita datiPartita = dp;
		datiPartitaServer = dp;
		
		//frame.dispose();
		frame.setVisible(false);
		nuovoFrame();
		if(controlloreAssegnato == 1) frame.setTitle("STRATEGIC ROBOTS - giocatore 1 - squadra ARANCIONE");
		else frame.setTitle("STRATEGIC ROBOTS - giocatore 2 - squadra VERDE");
		
		modalitàPartita = datiPartitaServer.getModalitàPartita();
		
		scenario = datiPartitaServer.getScenario();
		controlloreGiocatore1 = datiPartitaServer.getControlloreInterattivo1();
		controlloreGiocatore2 = datiPartitaServer.getControlloreInterattivo2();
		
		//controlloreGiocatore1.modificaScenario(scenario);
		//controlloreGiocatore2.modificaScenario(scenario);
		
		numeroMossa = datiPartitaServer.getNumeroMossa();
		
		controlloreGiocatore1.modificaFrame(frame);
		controlloreGiocatore1.aggiornaRobot();
		
		controlloreGiocatore2.modificaFrame(frame);
		controlloreGiocatore2.aggiornaRobot();
		
		System.out.println("controllore assegnato = " + controlloreAssegnato);
		

		flagMossa = false;
		if (controlloreAssegnato == 1) {
			if (controlloreGiocatore1.getStato()) {
				controlloreGiocatore1.aggiungiKeyListner();
				flagMossa = true;
			}
		} else if (controlloreAssegnato == 2) {
			if (controlloreGiocatore2.getStato()) {
				controlloreGiocatore2.aggiungiKeyListner();
				flagMossa = true;
			}
		}
		

		System.out.println("flag mossa = " + flagMossa);
		
		/*
		if(controlloreAssegnato == 1) {
			controlloreGiocatore1.start(scenario.getArrayListOstacoli(), scenario.getArrayListBancoRifornimenti());
		} else if (controlloreAssegnato == 2) {
			controlloreGiocatore2.start(scenario.getArrayListOstacoli(), scenario.getArrayListBancoRifornimenti());
		} else log.info("problema con il controllore assegnato");
		*/
		
		//scenario.repaintScenario();

		//scenario.repaintScenario();
		frame.add(scenario);
		frame.setVisible(true);
		//scenario.repaintScenario();
		
		KeyListener listenerCambioGiocatore = new CambioGiocatore();
		frame.addKeyListener(listenerCambioGiocatore);

	}

	public void aggiornaDatiPartitaServer() throws RemoteException{
		datiPartitaServer= new DatiPartita();
		
		datiPartitaServer.modificaModalitàPartita(modalitàPartita);
		datiPartitaServer.modificaNumeroMossa(numeroMossa);
		datiPartitaServer.modificaScenario(scenario);
		datiPartitaServer.modificaControlloreInterattivo1(controlloreGiocatore1);
		datiPartitaServer.modificaControlloreInterattivo2(controlloreGiocatore2);
		
		/*MessaggioFrame infoFrame = new MessaggioFrame("dati inviati al server");
		infoFrame.setVisible(true);*/
		
		objStato.aggiornaStatoServer(datiPartitaServer);
		
	}
	
	
	/**
	 * Evento per passare il controllo ad un altro controllore premendo il tasto Invio
	 */
	private class CambioGiocatore implements KeyListener{

		public void keyPressed(KeyEvent e) {
			
			
			if (flagMossa) {
				if (e.getKeyCode() == 10){
					
					System.out.println("ho premuto invio");
					
					numeroMossa[0]++;
					/*
					//ogni 2 mosse finisce il round e viene scelto casualmente il controllore che inizia l'altro round
					if(numeroMossa[0] % 2 == 0){
						FineRoundFrame fine = new FineRoundFrame(numeroMossa[0] / 2);
						fine.setVisible(true);

						Random mossaRandom = new Random();
						if(mossaRandom.nextInt(2)== 0){
							controlloreGiocatore1.attiva();
							controlloreGiocatore2.disattiva();
		
						}
						else if (modalitàPartita.equals("controGiocatore")){
							controlloreGiocatore2.attiva();
							controlloreGiocatore1.disattiva();
						}

					}
					*/
					
					if(controlloreGiocatore1.getStato()){
						controlloreGiocatore1.disattiva();
						controlloreGiocatore2.cambiaRobot();
						controlloreGiocatore2.attiva();
						

					}
					else {
						controlloreGiocatore2.disattiva();
						controlloreGiocatore1.cambiaRobot();
						controlloreGiocatore1.attiva();
					}
					
					try {
						aggiornaDatiPartitaServer();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					


				}
			}

		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
		
	}



	
	private class clickAbbandona implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			// da implementare
			/*
			ImpostazioniNuovaPartitaFrame nuovaPartita = new ImpostazioniNuovaPartitaFrame();
			nuovaPartita.setLocationRelativeTo(null);
			nuovaPartita.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			nuovaPartita.setVisible(true);
			frame.setVisible(false);*/
			
		}
			
	}
	
	
	public JMenuBar creaMenùBar(){
		
		JMenuBar menùBar = new JMenuBar();
		
		JMenu menùFile = new JMenu("Opzioni");
		
		JMenuItem menùAbbandona = new JMenuItem("Abbandona Partita");
		
		menùFile.add(menùAbbandona);
		
		menùBar.add(menùFile);
		
		ActionListener listenerNuovaPartita = new clickAbbandona();		
		menùAbbandona.addActionListener(listenerNuovaPartita);
		
		
		return menùBar;
	}
	
/*	public DatiPartita getDatiPartitaServer() {
		return datiPartitaServer;
	}*/

	@Override
	public void assegnaControllore(int numeroControllore) throws RemoteException {
		controlloreAssegnato = numeroControllore;
	}


	public void nuovoFrame() {
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("STRATEGIC ROBOTS");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setJMenuBar(creaMenùBar());
	}
}
