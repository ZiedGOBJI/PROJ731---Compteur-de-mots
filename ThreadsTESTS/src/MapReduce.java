import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapReduce {
	final static String outputFilePath = "results.txt";

	static File file = new File(outputFilePath);

	static BufferedWriter bf = null;

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException{ 

		String path = "big.txt";
		
		List<List<String>> slicedTxt = Slice.sliceTxt(path); // le texte d�coup�e en sous-listes
		
		int nbThread = slicedTxt.size();
		ArrayList<Map<String, Integer>> mappedTxtList = new ArrayList<>(); // liste des textes mapp�s
		Map<String, Integer> results = new HashMap<String, Integer>();
		
		ArrayList<MappingTask> taches = new ArrayList<>();


		// EXECUTION DU MAPPAGE

		// cr�er un tableau de tache
		for(List<String> texte : slicedTxt) {
			MappingTask tache = new MappingTask(texte);
			taches.add(tache);
			
		}
		
		
		
		// ex�cuter toutes les t�ches en m�me temps
		for (MappingTask tache : taches) {
			Thread thread = new Thread(tache, "thread");
			thread.start();
			
		}
		
			
		// on r�cup�re tout 
		for (MappingTask tache : taches) {
			tache.getMappedText();
			mappedTxtList.add(tache.getMappedText());
		}
				

			
			
			
		

		System.out.println(mappedTxtList); // On a bien stock� les donn�es de notre mapping dans une liste qu'on pourra utiliser dans notre Reduce


		// EXECUTION DU REDUCE

		for(Map<String, Integer> map : mappedTxtList) {

			map.forEach((k, v) -> results.merge(k, v, Integer::sum));

		}

		System.out.println(results);

		// Ecriture du r�sultat dans un fichier texte
		bf = new BufferedWriter(new FileWriter(file));
		bf.write(results.toString());
		bf.flush();
		bf.close();


	}
}






