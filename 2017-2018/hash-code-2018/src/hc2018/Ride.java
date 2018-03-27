package hc2018;

import java.util.Scanner;

public class Ride {
	
	int id;
	
	int startR, startC, startT, endR, endC, endT;
	
	final int length;
	
	
	public Ride(int id, Scanner s) {
		this.id = id;
		startR = s.nextInt();
		startC = s.nextInt();
		endR = s.nextInt();
		endC = s.nextInt();
		startT = s.nextInt();
		endT = s.nextInt();
		
		length = length();
	}
	
	
	
	private int length() {
		return Map.dist(startR, startC, endR, endC);
	}
	
}
