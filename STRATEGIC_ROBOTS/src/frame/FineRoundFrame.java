package frame;

import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FineRoundFrame extends JFrame{

	final int FRAME_WIDTH = 100;
	final int FRAME_HEIGHT = 150;
	
	public FineRoundFrame(int i){
		int numeroRound = i;
		
		setSize(FRAME_WIDTH, FRAME_WIDTH);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		Panel panel = new Panel();
		Label label = new Label("Fine del " + numeroRound + "° Round");
		
		panel.add(label);
		panel.add(creaButton());
		
		add(panel);
	}
	
	public JButton creaButton(){
		
		JButton button = new JButton("Prossimo Round");

		
		class clickButton implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				FineRoundFrame.this.dispose();				
			}			
		}
		
		ActionListener listener = new clickButton();
		button.addActionListener(listener);
		
		return button;
	}
}
