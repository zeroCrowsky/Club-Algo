package hc2018;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
	
	int r, c, startBonus, t;

	
	List<Vehicle> vehicles = new ArrayList<>();
	
	List<Ride> rides = new ArrayList<>();
	
	
	
	
	public Map(Scanner s) {
		r = s.nextInt();
		c = s.nextInt();
		int f = s.nextInt();
		for (int i = 0; i < f; i++)
			vehicles.add(new Vehicle(i));
		
		int n = s.nextInt();
		
		startBonus = s.nextInt();
		
		t = s.nextInt();
		
		for (int i = 0; i < n; i++) {
			rides.add(new Ride(i, s));
		}
	}
	
	
	
	
	public void toOutput(PrintStream out) {
		for (Vehicle v : vehicles)
			v.toOutput(out);
	}
	
	
	
	
	
	@SuppressWarnings("cast")
	public void greedy() {
		for (int tick = 0; tick < t; tick++) {
			if (tick % 100 == 0)
				System.out.println("Tick " + tick + "/" + t + " ...");
			float[][] rent = new float[vehicles.size()][rides.size()];
			int[] rentV = new int[vehicles.size()];
			int[] rentR = new int[rides.size()];
			for (Vehicle v : vehicles) {
				if (v.currentRide != null) {
					if (v.r == v.currentRide.endR && v.c == v.currentRide.endC) {
						v.currentRide = null;
						v.goToRideStart = false;
					}
				}
				if (v.currentRide != null) {
					rentV[v.id] = -1;
					continue;
				}
				for (int rId = 0; rId < rides.size(); rId++) {
					Ride r = rides.get(rId);
					
					
					int distToStart = dist(v.r, v.c, r.startR, r.startC);
					int attente = Math.max(r.startT - (distToStart + tick), 0);
					boolean bonus = distToStart + tick <= r.startT;
					
					boolean beforeEnd = tick + distToStart + attente + r.length < r.endT;
					// metropolis
					
					rent[v.id][rId] = ((bonus ? startBonus : 0) + r.length)
							/
							(float) ((r.length + attente * 10 + distToStart));
					
					// high_bonus
					/* rent[v.id][rId] = ((bonus ? startBonus * 15 : 0) + r.length)
							/
							(float) ((r.length + attente + distToStart));
					*/
					
					
					if (!beforeEnd)
						rent[v.id][rId] = -1;
				}
			}
			
			int maxVehicle = 0, maxRide = 0; float max = -1;
			do {
				max = -1;
				for (int i = 0; i < vehicles.size(); i++) {
					if (rentV[i] == -1)
						continue;
					for (int j = 0; j < rides.size(); j++) {
						if (rentR[j] == -1)
							continue;
						if (rent[i][j] > max) {
							max = rent[i][j];
							maxVehicle = i;
							maxRide = j;
						}
					}
				}
				if (max > 0) {
					vehicles.get(maxVehicle).affectRide(rides.get(maxRide));
					rentV[maxVehicle] = -1;
					rentR[maxRide] = -1;
				}
				
			} while(max > 0);
			
			
			for (int i = rides.size() - 1; i >= 0; i--) {
				if (rentR[i] == -1) {
					rides.remove(i);
				}
			}
			
			
			
			// avancer véhicules
			
			for (Vehicle v : vehicles) {
				
				if (v.currentRide != null) {
					Ride ride = v.currentRide;
					if (v.goToRideStart) {
						// go to start
						if (v.r > ride.startR)
							v.r--;
						else if (v.r < ride.startR)
							v.r++;
						else if (v.c > ride.startC)
							v.c--;
						else if (v.c < ride.startC)
							v.c++;
						
						if (v.r == ride.startR && v.c == ride.startC)
							v.goToRideStart = false;
						
					}
					else {
						// go to end
						if (v.r > ride.endR)
							v.r--;
						else if (v.r < ride.endR)
							v.r++;
						else if (v.c > ride.endC)
							v.c--;
						else if (v.c < ride.endC)
							v.c++;
					}
				}
			}
			
		}
	}
	
	
	
	
	
	public static int dist(int sR, int sC, int eR, int eC) {
		return Math.abs(sR - eR) + Math.abs(sC - eC);
	}
	
	
	
	
	
	
	
	
}
