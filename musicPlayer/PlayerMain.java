package musicPlayer;

import javax.swing.JFrame;

public class PlayerMain extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		GUI guı2 = new GUI();
		
		PlayerMain guı = new PlayerMain();
		
		guı.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guı.setVisible(true);
		guı.setResizable(false);
		guı.setSize(450, 350);
		guı.setTitle("Music Player");
		guı.setJMenuBar(GUI.bar);
		guı.setAlwaysOnTop(true);
		guı.add(guı2);
		
	}
}
