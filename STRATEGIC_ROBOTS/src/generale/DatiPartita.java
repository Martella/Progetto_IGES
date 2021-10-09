package generale;

import controllo.ControlloreInterattivo;
import elementiScenario.Scenario;

public class DatiPartita {

	private String modalit‡Partita;
	private Scenario scenario;
	private int [] numeroMossa;
	private ControlloreInterattivo controlloreInterattivo1;
	private ControlloreInterattivo controlloreInterattivo2;
	
	public DatiPartita() {};
	
	public void modificaModalit‡Partita(String m){
		modalit‡Partita = m;
	}
	
	public void modificaNumeroMossa(int [] n){
		numeroMossa = n;
	}
	
	public void modificaScenario(Scenario s){
		scenario = s;
	}
	
	public void modificaControlloreInterattivo1(ControlloreInterattivo contInt){
		controlloreInterattivo1 = contInt;
	}

	public void modificaControlloreInterattivo2(ControlloreInterattivo contInt){
		controlloreInterattivo2 = contInt;
	}

	public String getModalit‡Partita(){
		return modalit‡Partita;
	}
	
	public int [] getNumeroMossa(){
		return numeroMossa;
	}
	
	public Scenario getScenario(){
		return scenario;
	}
	
	public ControlloreInterattivo getControlloreInterattivo1(){
		return controlloreInterattivo1;
	}

	public ControlloreInterattivo getControlloreInterattivo2(){
		return controlloreInterattivo2;
	}
	
}
