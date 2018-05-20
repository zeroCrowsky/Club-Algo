package hc2018;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
	
	public int id, r = 0, c = 0;
	
	public List<Ride> rides = new ArrayList<>();
	
	public Ride currentRide = null;
	public boolean goToRideStart = false;
	
	public Vehicle(int id) {
		this.id = id;
	}
	
	
	public void affectRide(Ride r) {
		currentRide = r;
		rides.add(r);
		goToRideStart = true;
	}
	
	
	public void toOutput(PrintStream out) {
		out.print(rides.size());
		for (Ride r : rides)
			out.print(" " + r.id);
		out.println();
	}
	
}
