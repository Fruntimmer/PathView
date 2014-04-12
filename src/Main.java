import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Main {
	public static void main(String args[]) throws InvocationTargetException, InterruptedException{
		SwingUtilities.invokeAndWait(new Runnable(){
			public void run(){
				EventHandler eventHandler = new EventHandler();
				JFrame main = new GUIMain(eventHandler);
				
			}
		});
	}
}
