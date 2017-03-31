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
		
		Journey j = in.journeys.get(0);

		String h1 = in.hyperloopClosestTo(j.ville1);
		String h2 = in.hyperloopClosestTo(j.ville2);

		int h1index = in.hyperloopLine.indexOf(h1);
		int h2index = in.hyperloopLine.indexOf(h2);

		int start = Math.min(h1index, h2index);
		int end = Math.max(h1index, h2index);
		
		double hyperloopTime = 0;
		
		for (int i = start; i < end; i++) {
			hyperloopTime += in.timeHyperLoopSimple(in.hyperloopLine.get(i), in.hyperloopLine.get(i + 1));
		}

		double timeStart = in.tempsVoiture(j.ville1, h1);
		double timeEnd = in.tempsVoiture(j.ville2, h2);
		
		System.out.println(Math.round(hyperloopTime + timeEnd + timeStart));
		
		
		
		
		
		
	}
	
}
