package controllo;

import java.util.TimerTask;

public class TimerMossaComputer extends TimerTask{

	private ControlloreNonInterattivo controllore;
	
	public TimerMossaComputer(ControlloreNonInterattivo c){
		controllore = c;
	}

	public void run() {
		controllore.eseguiMossa();
	}

	
}
