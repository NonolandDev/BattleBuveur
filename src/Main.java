import javax.swing.SwingUtilities;

import fr.ltdg.Window;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Window();
			}
		});
	}

}
