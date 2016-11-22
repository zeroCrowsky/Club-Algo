import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import datastruct.Datacenter;
import datastruct.Server;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Random r = new Random();
		
		Datacenter datacenter;
		try (InputStream data = new FileInputStream("data.in")) {
			datacenter = new Datacenter(data);
		}
		
		List<Server> servers = Arrays.asList(datacenter.getServers());
		
		servers.sort((s1, s2) -> -Server.compareByProfitability(s1, s2));
		
		// placer les serveurs dans le datacentre
		int currentRow = 0;
		for (Server sv : servers) {
			
			
			boolean put = false;
			int tries = 0;
			do {
				currentRow = r.nextInt(datacenter.getNbRow());
				int c = 0;
				while(!datacenter.canPut(sv, currentRow, c) && c < datacenter.getNbCol())
					c++;
				if (datacenter.canPut(sv, currentRow, c)) {
					datacenter.put(sv, currentRow, c);
					put = true;
				}
				tries++;
			} while(!put && tries < 100);
			
			//currentRow = (currentRow + 1) % datacenter.getNbRow();
		}

		datacenter.display(System.out);
		
		
		servers = servers.stream().filter(Server::isPlaced).collect(Collectors.toList());
		
		
		for (Server sv : servers) {
			sv.group = r.nextInt(datacenter.nbGroup);
		}
		

		datacenter.outputToFile();
		Datacenter.setBest(datacenter);
		int count = 1;
		while(true) {
			
			/*
			 * Solution 1 Swap de deux serveurs de groupes différents
			 */
			/*
			Server s1, s2;
			do {
				s1 = servers.get(r.nextInt(servers.size()));
				s2 = servers.get(r.nextInt(servers.size()));
			} while(s1 == s2);
			
			// swap group
			int gtmp = s1.group;
			s1.group = s2.group;
			s2.group = gtmp;
			*/
			
			/*
			 * Solution 2 : le pire groupe se voit attribué un serveur aléatoire en plus
			 */
			int minGroup = datacenter.getGroupWithMinScore();
			Server sv;
			do {
				sv = servers.get(r.nextInt(servers.size()));
			} while(sv.group == minGroup);
			
			sv.group = minGroup;
			

			datacenter.outputToFile();
			Datacenter.setBest(datacenter);
			
			if (count % 50 == 0) {
				datacenter.restoreStateFromRuntimeBest();
			}
			count++;
		}
		
		
		
		
	}
	
	
	
	
	
	
	
}
