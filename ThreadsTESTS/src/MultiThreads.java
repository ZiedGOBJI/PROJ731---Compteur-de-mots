import java.io.IOException;
import java.util.List;

public class MultiThreads implements Runnable {
	private List<String> liste;	
	
	
	
	/*
	 * trame : 
	 * on prend le fichier de base on le slice en plusieurs partie selon le nombre de mot
	 * pour chaque partie on cr�er un thread et on le start pour faire le map 
	 * 
	 * puis partie reduce � travailler
	 */
	
	
	public MultiThreads(List<String> liste) {
		super();
		this.liste = liste;
		
		
	}

	@Override
	public void run() {
		
		try {
			MapFunction.mapGenerator(liste);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	
		
	
		


	


	
}
			    



