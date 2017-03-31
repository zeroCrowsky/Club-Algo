import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		
		Input in = new Input(System.in);
		
		
		/*
		for (String hyp1 : in.villes.keySet()) {
			for (String hyp2 : in.villes.keySet()) {
				if (hyp1.equals(hyp2))
					continue;
				int nbFasterInHyperloop = 0;
				
				for (Journey j : in.journeys) {
					String h1 = in.closestTo(j.ville1, hyp1, hyp2);
					String h2 = in.closestTo(j.ville2, hyp1, hyp2);
					
					double tHyperloop = in.timeHyperLoopSimple(h1, h2);

					double tStart = in.tempsVoiture(j.ville1, h1);
					double tEnd = in.tempsVoiture(j.ville2, h2);
					
					if (tHyperloop + tStart + tEnd < j.carTime) {
						nbFasterInHyperloop++;
					}
					
				}
				
				if (nbFasterInHyperloop >= in.n)
					System.out.println(hyp1 + " " + hyp2);
				
				
			}
		}*/
		
		
		/*
		
		Random r = new Random();
		for (;;) {
			int nb = r.nextInt(30);
			List<String> hyperloop = new ArrayList<>();
			for (int i = 0; i < nb; i++) {
				
			}
		}
		
		*/
		
		
		
		
		for (int nbStop = 2; nbStop <= 100; nbStop++) {
			
			List<String> hyperloop = new ArrayList<>();
			for (int i = 0; i < nbStop; i++) hyperloop.add(null);
			
			doLoop(nbStop, 0, in, hyperloop, () -> {
				
				
				
				
				int nbFasterInHyperloop = 0;
				
				for (Journey j : in.journeys) {
					
					String h1 = in.hyperloopClosestTo(j.ville1, hyperloop);
					String h2 = in.hyperloopClosestTo(j.ville2, hyperloop);

					int h1index = hyperloop.indexOf(h1);
					int h2index = hyperloop.indexOf(h2);

					int start = Math.min(h1index, h2index);
					int end = Math.max(h1index, h2index);
					
					double hyperloopTime = 0;
					
					for (int i = start; i < end; i++) {
						hyperloopTime += in.timeHyperLoopSimple(hyperloop.get(i), hyperloop.get(i + 1));
					}

					double timeStart = in.tempsVoiture(j.ville1, h1);
					double timeEnd = in.tempsVoiture(j.ville2, h2);
					
					if (hyperloopTime + timeEnd + timeStart < j.carTime)
						nbFasterInHyperloop++;
					
					
				}
				
				if (nbFasterInHyperloop >= in.n) {
					System.out.print(hyperloop.size());
					for (String h : hyperloop) {
						System.out.print(" " + h);
					}
					System.out.println();
					System.out.println("   nbfaster = " + nbFasterInHyperloop);

					double hyperloopDist = 0;
					
					for (int i = 0; i < hyperloop.size() - 1; i++) {
						hyperloopDist += in.distance(hyperloop.get(i), hyperloop.get(i + 1));
					}
					
					System.out.println("   hyperloopDist = " + hyperloopDist);
				}
				
				
				
			});
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	public static void doLoop(int deepTotal, int deepCurrent, Input in, List<String> hyperloop, Runnable r) {
		
		for (String ville : in.villes.keySet()) {
			
			if (hyperloop.contains(ville))
				continue;
			
			hyperloop.set(deepCurrent, ville);
			
			double hyperloopDist = 0;
			
			for (int i = 0; i < deepCurrent; i++) {
				hyperloopDist += in.distance(hyperloop.get(i), hyperloop.get(i + 1));
			}
			
			if (hyperloopDist >= in.d)
				continue;
			
			if (deepCurrent < deepTotal - 1) {
				doLoop(deepTotal, deepCurrent+1, in, hyperloop, r);
			}
			else
				r.run();
		}
		
		hyperloop.set(deepCurrent, null);
		
	}
	
	
	
	
	
	
	
	
}
