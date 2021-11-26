import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingTask implements Runnable{
	/*
	 Pour retourner le r�sultat des t�ches (tasks).
	 task : contient les instructions et les retours.
	 thread : un constructeur qui dirige le set d'instructions ind�pendamment.
	 */

	List<String> texte = new ArrayList<>();
	Map<String, Integer> mappedTxt = new HashMap<>();

	// Ce bool�en va �tre vu et modifi� par plusieurs threads, cela explique le caract�re volatile de la variable
	private volatile boolean done = false; 


	public MappingTask(List<String> texte) {
		this.texte = texte;
	}

	@Override
	public void run() {

		System.out.println("d�but thread");


		try {
			mappedTxt = MapFunction.mapGenerator(texte);
		} catch (IOException e) {
			e.printStackTrace();
		}


		System.out.println("fin thread");

		done = true; // ce qui atteste que le thread est termin�

		synchronized(this) {
			// quand le thread est fini, on r�veille le thread en attente (**)
			this.notifyAll();
		}

	}


	public  Map<String, Integer> getMappedText() {

		/* pour savoir si notre thread a fini de s'�xecuter avant de renvoyer le r�sultat,
		si le thread a fini on return le r�sultat, sinon on attend qu'il finisse.
		*/
		while (!done) {
			// Pour prot�ger l'acc�s � notre objet Task on applique un synchronize sur les blocs wait et notify  
			synchronized(this){ 
				try {
					 // le thread attend
					this.wait();

				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
// (**) thread se r�veille ici 
		}
		return mappedTxt;	
	}


}
