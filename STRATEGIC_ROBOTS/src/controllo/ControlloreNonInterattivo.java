package controllo;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import eccezioni.InsufficientEnergyException;
import elementiScenario.BancoRifornimenti;
import elementiScenario.Ostacolo;
import elementiScenario.Scenario;
import interfacce.Disegnabile;
import interfacce.Energetico;
import robot.Robot;
import robot.RobotCombattente;
import robot.RobotLavoratore;

public class ControlloreNonInterattivo extends Controllore{
	
	private Scenario scenario;
	private String [][] griglia;
	private String tipo;
	private Robot rob;
	private ArrayList<Ostacolo> ostacoloArray;
	private int numeroMosse;
	private int mossaAttuale;

	
	/**
	 * 
	 * @param s scenario su cui controllare i Robot
	 * @param f frame dello scenario
	 */
	public ControlloreNonInterattivo(Scenario s, JFrame f){
		super(s, f);
		scenario = s;
	}
	
	/**
	 * Inizia a controllare un Robot 
	 * @param o ArrayList contenenti gli Ostacoli dello scenario
	 * @param b ArrayList contenenti i BancoRifornimeti dello scenario
	 */
	public void start( ArrayList<Ostacolo> o, ArrayList<BancoRifornimenti> b){
		super.start();
	
		ostacoloArray = o;
	}
	
	/**
	 * Il Robot controllato effettua delle mosse scelte random se può compiere una mossa
	 */
	public void eseguiMossa(){
		
		final int SOPRA = 0;
		final int DESTRA = 1;
		final int SOTTO = 2;
		final int SINISTRA = 3;

		rob = getRobotControllato();
		tipo = getTipoRobotControllato();
		ArrayList<RobotLavoratore> insiemeRobotLavoratoreArray = getInsiemeRobotLavoratoreeArray();
		ArrayList<RobotCombattente> insiemeRobotCombattenteArray = getInsiemeRobotCombattenteArray();


		Random mossaRandom = new Random();
		
		try{
			if(mossaAttuale <= numeroMosse){
				
				mossaAttuale++;
				
				int mossa;
				mossa = mossaRandom.nextInt(4);
				
				if(getStato() && getStatoMossa()){
					
					boolean spostato = false;
					griglia = new String [scenario.getMaxRighe()][scenario.getMaxColonne()];
					griglia = scenario.getGriglia();
	
					// se premi tasto destro
					if (mossa == DESTRA){					
						if (rob.getY() < scenario.getMaxColonne() - 1 && griglia[rob.getX()][rob.getY()+1].equals("null")){
							spostato = rob.spostamentoY(rob.getY()+1);
							if(spostato) scenario.modificaGrigliaCancella(rob.getX(), rob.getY()-1);
						}
					}
					
					// se premi tasto sinistro
					if (mossa == SINISTRA){					
						if (rob.getY() > 0 && griglia[rob.getX()][rob.getY()-1].equals("null")){
							spostato = rob.spostamentoY(rob.getY()-1);
							if(spostato) scenario.modificaGrigliaCancella(rob.getX(), rob.getY()+1);
						}
					}
					
					// se premi tasto sopra
					if (mossa == SOPRA){					
						if (rob.getX() > 0 && griglia[rob.getX() -1][rob.getY()].equals("null")){
							spostato = rob.spostamentoX(rob.getX()-1);
							if(spostato) scenario.modificaGrigliaCancella(rob.getX()+1, rob.getY());
						}
					}
					
					// se premi tasto sotto
					if (mossa == SOTTO){					
						if (rob.getX() < scenario.getMaxRighe() - 1 && griglia[rob.getX()+1][rob.getY()].equals("null")){
							spostato = rob.spostamentoX(rob.getX()+1);
							if(spostato) scenario.modificaGrigliaCancella(rob.getX()-1, rob.getY());
						}
					}
					
					
					
					if (tipo.equals("robotCombattente") && !spostato){
						RobotCombattente robComb = (RobotCombattente) rob;
						boolean attaccato = false;
	
						// se premi tasto destro
						if (mossa == DESTRA){	
							if (robComb.getY() < scenario.getMaxColonne() - 1 && ! griglia[robComb.getX()][robComb.getY()+1].equals("null") && ! griglia[robComb.getX()][robComb.getY()+1].equals("bancoRifornimenti") ){
								if(robComb.attacca()){
									attaccato = true;
									
									for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
										if ((ostacoloArray.get(ii)).getX()== robComb.getX() && (ostacoloArray.get(ii)).getY() == robComb.getY()+1){
											(ostacoloArray.get(ii)).modificaEnergia((ostacoloArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((ostacoloArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()+1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()+1);
												(ostacoloArray.get(ii)).modificaX(scenario.getMaxRighe()+1);
												(ostacoloArray.get(ii)).modificaY(scenario.getMaxColonne()+1);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++ ){
										if ((insiemeRobotCombattenteArray.get(ii)).getX()== robComb.getX() && (insiemeRobotCombattenteArray.get(ii)).getY() == robComb.getY()+1){
											(insiemeRobotCombattenteArray.get(ii)).modificaEnergia((insiemeRobotCombattenteArray.get(ii)).getEnergia()-( 100 * robComb.getDannoAttacco()/100) );
											if((insiemeRobotCombattenteArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()+1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()+1);
												insiemeRobotCombattenteArray.remove(ii);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++ ){
										if ((insiemeRobotLavoratoreArray.get(ii)).getX()== robComb.getX() && (insiemeRobotLavoratoreArray.get(ii)).getY() == robComb.getY()+1){
											(insiemeRobotLavoratoreArray.get(ii)).modificaEnergia((insiemeRobotLavoratoreArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((insiemeRobotLavoratoreArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()+1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()+1);
												insiemeRobotLavoratoreArray.remove(ii);
											}
										}
									}
	
	
								}
							}
						}
	
						
						// se premi tasto sinistro
						if (mossa == SINISTRA){	
							if (robComb.getY() > 0 && ! griglia[robComb.getX()][robComb.getY()-1].equals("null") && ! griglia[robComb.getX()][robComb.getY()-1].equals("bancoRifornimenti") ){
								if(robComb.attacca()){
									attaccato = true;
									
									for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
										if ((ostacoloArray.get(ii)).getX()== robComb.getX() && (ostacoloArray.get(ii)).getY() == robComb.getY()-1){
											(ostacoloArray.get(ii)).modificaEnergia((ostacoloArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((ostacoloArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()-1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()-1);
												(ostacoloArray.get(ii)).modificaX(scenario.getMaxRighe()+1);
												(ostacoloArray.get(ii)).modificaY(scenario.getMaxColonne()+1);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++ ){
										if ((insiemeRobotCombattenteArray.get(ii)).getX()== robComb.getX() && (insiemeRobotCombattenteArray.get(ii)).getY() == robComb.getY()-1){
											(insiemeRobotCombattenteArray.get(ii)).modificaEnergia((insiemeRobotCombattenteArray.get(ii)).getEnergia()-(100 * robComb.getDannoAttacco()/100));
											if((insiemeRobotCombattenteArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()-1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()-1);
												insiemeRobotCombattenteArray.remove(ii);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++ ){
										if ((insiemeRobotLavoratoreArray.get(ii)).getX()== robComb.getX() && (insiemeRobotLavoratoreArray.get(ii)).getY() == robComb.getY()-1){
											(insiemeRobotLavoratoreArray.get(ii)).modificaEnergia((insiemeRobotLavoratoreArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((insiemeRobotLavoratoreArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX(), robComb.getY()-1);
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX(), robComb.getY()-1);
												insiemeRobotLavoratoreArray.remove(ii);
											}
										}
									}
	
	
								}
							}
						}
						
						// se premi tasto sopra
						if (mossa == SOPRA){	
							if (robComb.getX() > 0 && ! griglia[robComb.getX()-1][robComb.getY()].equals("null") && ! griglia[robComb.getX()-1][robComb.getY()].equals("bancoRifornimenti") ){
								if(robComb.attacca()){
									attaccato = true;
									
									for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
										if ((ostacoloArray.get(ii)).getX()== robComb.getX()-1 && (ostacoloArray.get(ii)).getY() == robComb.getY()){
											(ostacoloArray.get(ii)).modificaEnergia((ostacoloArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((ostacoloArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()-1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()-1, robComb.getY());
												(ostacoloArray.get(ii)).modificaX(scenario.getMaxRighe()+1);
												(ostacoloArray.get(ii)).modificaY(scenario.getMaxColonne()+1);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++ ){
										if ((insiemeRobotCombattenteArray.get(ii)).getX()== robComb.getX()-1 && (insiemeRobotCombattenteArray.get(ii)).getY() == robComb.getY()){
											(insiemeRobotCombattenteArray.get(ii)).modificaEnergia((insiemeRobotCombattenteArray.get(ii)).getEnergia()-( 100 * robComb.getDannoAttacco()/100));
											if((insiemeRobotCombattenteArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()-1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()-1, robComb.getY());
												insiemeRobotCombattenteArray.remove(ii);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++ ){
										if ((insiemeRobotLavoratoreArray.get(ii)).getX()== robComb.getX()-1 && (insiemeRobotLavoratoreArray.get(ii)).getY() == robComb.getY()){
											(insiemeRobotLavoratoreArray.get(ii)).modificaEnergia((insiemeRobotLavoratoreArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((insiemeRobotLavoratoreArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()-1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()-1, robComb.getY());
												insiemeRobotLavoratoreArray.remove(ii);
											}
										}
									}
	
	
								}
							}
						}
	
						// se premi tasto sotto
						if (mossa == SOTTO){	
							if (robComb.getX() < scenario.getMaxRighe() - 1 && ! griglia[robComb.getX()+1][robComb.getY()].equals("null") && ! griglia[robComb.getX()+1][robComb.getY()].equals("bancoRifornimenti") ){
								if(robComb.attacca()){
									attaccato = true;
									
									for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
										if ((ostacoloArray.get(ii)).getX()== robComb.getX()+1 && (ostacoloArray.get(ii)).getY() == robComb.getY()){
											(ostacoloArray.get(ii)).modificaEnergia((ostacoloArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((ostacoloArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()+1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()+1, robComb.getY());
												(ostacoloArray.get(ii)).modificaX(scenario.getMaxRighe()+1);
												(ostacoloArray.get(ii)).modificaY(scenario.getMaxColonne()+1);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++ ){
										if ((insiemeRobotCombattenteArray.get(ii)).getX()== robComb.getX()+1 && (insiemeRobotCombattenteArray.get(ii)).getY() == robComb.getY()){
											(insiemeRobotCombattenteArray.get(ii)).modificaEnergia((insiemeRobotCombattenteArray.get(ii)).getEnergia()-( 100 * robComb.getDannoAttacco()/100));
											if((insiemeRobotCombattenteArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()+1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()+1, robComb.getY());
												insiemeRobotCombattenteArray.remove(ii);
											}
										}
									}
									
									for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++ ){
										if ((insiemeRobotLavoratoreArray.get(ii)).getX()== robComb.getX()+1 && (insiemeRobotLavoratoreArray.get(ii)).getY() == robComb.getY()){
											(insiemeRobotLavoratoreArray.get(ii)).modificaEnergia((insiemeRobotLavoratoreArray.get(ii)).getEnergia()-robComb.getDannoAttacco());
											if((insiemeRobotLavoratoreArray.get(ii)).getEnergia() > 0){
												scenario.modificaGrigliaRepaintCasella(robComb.getX()+1, robComb.getY());
											}
											else{
												scenario.modificaGrigliaCancella(robComb.getX()+1, robComb.getY());
												insiemeRobotLavoratoreArray.remove(ii);
											}
										}
									}
	
	
								}
							}
						}
						
						if (attaccato) modificaStatoMossa(false);
						
					}
					
					
					if (tipo.equals("robotLavoratore") && !spostato){
						RobotLavoratore robLav = (RobotLavoratore) rob;
						boolean curato = false;
						
						// se premi tasto destro
						if (mossa == DESTRA){												
							if (robLav.getY() < scenario.getMaxColonne() - 2 && griglia[robLav.getX()][robLav.getY()+1].equals("ostacolo")&& griglia[robLav.getX()][robLav.getY()+2].equals("null")){								
								for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
									if((ostacoloArray.get(ii)).getX()== robLav.getX() && (ostacoloArray.get(ii)).getY() == robLav.getY()+1){
										spostato = true;
										(ostacoloArray.get(ii)).modificaY((ostacoloArray.get(ii)).getY()+1);
										scenario.modificaGrigliaAggiungiOggetto((Disegnabile)ostacoloArray.get(ii), "ostacolo");
										scenario.modificaGrigliaAggiungiEnergia((Energetico)ostacoloArray.get(ii));
									}
								}	
								if (spostato){
									scenario.modificaGrigliaCancella(robLav.getX(), robLav.getY());
									robLav.spostamentoY(robLav.getY()+1);
									scenario.modificaGrigliaAggiungiOggetto((Disegnabile)robLav, tipo);
								}
							}
							
							if (robLav.getY() < scenario.getMaxColonne() - 1 && (griglia[robLav.getX()][robLav.getY()+1].equals("robotCombattente") || griglia[robLav.getX()][robLav.getY()+1].equals("robotLavoratore") )){								
								
								for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++){
									if( (insiemeRobotCombattenteArray.get(ii)).getX() == robLav.getX() && (insiemeRobotCombattenteArray.get(ii)).getY() == robLav.getY() + 1){
										robLav.curaRobot(insiemeRobotCombattenteArray.get(ii));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotCombattenteArray.get(ii).getX(), insiemeRobotCombattenteArray.get(ii).getY());
										curato = true;
									}
								}
								
								for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++){
									if( (insiemeRobotLavoratoreArray.get(ii)).getX() == robLav.getX() && (insiemeRobotLavoratoreArray.get(ii)).getY() == robLav.getY() + 1){
										robLav.curaRobot(insiemeRobotLavoratoreArray.get(ii));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotLavoratoreArray.get(ii).getX(), insiemeRobotLavoratoreArray.get(ii).getY());
										curato = true;
									}
								}
							}
						}
						
						// se premi tasto sinistro
						if (mossa == SINISTRA){												
							if (robLav.getY() > 1 && griglia[robLav.getX()][robLav.getY()-1].equals("ostacolo")&& griglia[robLav.getX()][robLav.getY()-2].equals("null")){								
								for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
									if((ostacoloArray.get(ii)).getX()== robLav.getX() && (ostacoloArray.get(ii)).getY() == robLav.getY()-1){
										spostato = true;
										(ostacoloArray.get(ii)).modificaY((ostacoloArray.get(ii)).getY()-1);
										scenario.modificaGrigliaAggiungiOggetto((Disegnabile)ostacoloArray.get(ii), "ostacolo");
										scenario.modificaGrigliaAggiungiEnergia((Energetico)ostacoloArray.get(ii));
									}
								}		
								if (spostato){
									scenario.modificaGrigliaCancella(robLav.getX(), robLav.getY());
									robLav.spostamentoY(robLav.getY()-1);
									scenario.modificaGrigliaAggiungiOggetto((Disegnabile)robLav, tipo);
								}
							}
							
							if (robLav.getY() > 0 && (griglia[robLav.getX()][robLav.getY()-1].equals("robotCombattente") || griglia[robLav.getX()][robLav.getY()-1].equals("robotLavoratore") )){								
								
								for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++){
									if( (insiemeRobotCombattenteArray.get(ii)).getX() == robLav.getX() && (insiemeRobotCombattenteArray.get(ii)).getY() == robLav.getY() - 1){
										robLav.curaRobot(insiemeRobotCombattenteArray.get(ii));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotCombattenteArray.get(ii).getX(), insiemeRobotCombattenteArray.get(ii).getY());
										curato = true;
									}
								}
								
								for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++){
									if( (insiemeRobotLavoratoreArray.get(ii)).getX() == robLav.getX() && (insiemeRobotLavoratoreArray.get(ii)).getY() == robLav.getY() - 1){
										robLav.curaRobot(insiemeRobotLavoratoreArray.get(ii));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotLavoratoreArray.get(ii).getX(), insiemeRobotLavoratoreArray.get(ii).getY());
										curato = true;
									}
								}
							}
	
						}
						
						// se premi tasto sopra
						if (mossa == SOPRA){												
							if (robLav.getX() > 1 && griglia[robLav.getX()-1][robLav.getY()].equals("ostacolo")&& griglia[robLav.getX()-2][robLav.getY()].equals("null")){								
								for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
									if((ostacoloArray.get(ii)).getX()== robLav.getX()-1 && (ostacoloArray.get(ii)).getY() == robLav.getY()){
										spostato = true;
										(ostacoloArray.get(ii)).modificaX((ostacoloArray.get(ii)).getX()-1);
										scenario.modificaGrigliaAggiungiOggetto((Disegnabile)ostacoloArray.get(ii), "ostacolo");
										scenario.modificaGrigliaAggiungiEnergia((Energetico)ostacoloArray.get(ii));
									}
								}
								if(spostato){
									scenario.modificaGrigliaCancella(robLav.getX(), robLav.getY());
									robLav.spostamentoX(robLav.getX()-1);
									scenario.modificaGrigliaAggiungiOggetto((Disegnabile)robLav, tipo);
								}
							}
							
							if (robLav.getX() > 0 && (griglia[robLav.getX() - 1][robLav.getY()].equals("robotCombattente") || griglia[robLav.getX()-1][robLav.getY()].equals("robotLavoratore") )){								
								
								for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++){
									if( (insiemeRobotCombattenteArray.get(ii)).getX() == robLav.getX()-1 && (insiemeRobotCombattenteArray.get(ii)).getY() == robLav.getY()){
										robLav.curaRobot(insiemeRobotCombattenteArray.get(ii));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotCombattenteArray.get(ii).getX(), insiemeRobotCombattenteArray.get(ii).getY());
										curato = true;
									}
								}
								
								for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++){
									if( (insiemeRobotLavoratoreArray.get(ii)).getX() == robLav.getX()-1 && (insiemeRobotLavoratoreArray.get(ii)).getY() == robLav.getY() ){
										robLav.curaRobot(insiemeRobotLavoratoreArray.get(ii));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotLavoratoreArray.get(ii).getX(), insiemeRobotLavoratoreArray.get(ii).getY());
										curato = true;
									}
								}
							}
	
						} 
	
						// se premi tasto sotto
						if (mossa == SOTTO){												
							if (robLav.getX() < scenario.getMaxRighe() - 2 && griglia[robLav.getX()+1][robLav.getY()].equals("ostacolo")&& griglia[robLav.getX()+2][robLav.getY()].equals("null")){								
								for(int ii = 0; ii < ostacoloArray.size(); ii++ ){
									if((ostacoloArray.get(ii)).getX()== robLav.getX()+1 && (ostacoloArray.get(ii)).getY() == robLav.getY()){
										spostato = true;
										(ostacoloArray.get(ii)).modificaX((ostacoloArray.get(ii)).getX()+1);
										scenario.modificaGrigliaAggiungiOggetto((Disegnabile)ostacoloArray.get(ii), "ostacolo");
										scenario.modificaGrigliaAggiungiEnergia((Energetico)ostacoloArray.get(ii));
									}
								}	
								if (spostato){
									scenario.modificaGrigliaCancella(robLav.getX(), robLav.getY());
									robLav.spostamentoX(robLav.getX()+1);
									scenario.modificaGrigliaAggiungiOggetto((Disegnabile)robLav, tipo);
								}
							}
							
							if (robLav.getX() < scenario.getMaxRighe()-1 && (griglia[robLav.getX() + 1][robLav.getY()].equals("robotCombattente") || griglia[robLav.getX()+1][robLav.getY()].equals("robotLavoratore") )){								
								
								for(int ii = 0; ii < insiemeRobotCombattenteArray.size(); ii++){
									if( (insiemeRobotCombattenteArray.get(ii)).getX() == robLav.getX()+1 && (insiemeRobotCombattenteArray.get(ii)).getY() == robLav.getY()){
										robLav.curaRobot(insiemeRobotCombattenteArray.get(ii));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotCombattenteArray.get(ii).getX(), insiemeRobotCombattenteArray.get(ii).getY());
										curato = true;
									}
								}
								
								for(int ii = 0; ii < insiemeRobotLavoratoreArray.size(); ii++){
									if( (insiemeRobotLavoratoreArray.get(ii)).getX() == robLav.getX()+1 && (insiemeRobotLavoratoreArray.get(ii)).getY() == robLav.getY() ){
										robLav.curaRobot(insiemeRobotLavoratoreArray.get(ii));
										scenario.modificaGrigliaRepaintCasella(insiemeRobotLavoratoreArray.get(ii).getX(), insiemeRobotLavoratoreArray.get(ii).getY());
										curato = true;
									}
								}
							}
							
							if(curato)modificaStatoMossa(false);
				
							
						}
	
					}
					
					scenario.modificaGrigliaSelezionaCasella(rob.getX(), rob.getY());
					scenario.modificaGrigliaAggiungiOggetto((Disegnabile)rob, tipo);
					scenario.modificaGrigliaAggiungiEnergia((Energetico)rob);
				}
				
				else scenario.modificaGrigliaDeSelezionaCasella(rob.getX(), rob.getY());
			}
			else  disattiva();
			
		}catch (InsufficientEnergyException e1){
			//non fai niente perche il controllore non è interattivo
		}

	}
	
	/**
	 * Attiva il Controllore
	 */
	public void attiva(){
		super.attiva();
		mossaAttuale = 1;
		Random r = new Random();
		numeroMosse = r.nextInt(10);
	}
	
	
		

}
