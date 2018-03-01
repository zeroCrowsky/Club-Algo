package hc2018;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
				// "a_example.in",
				//"b_should_be_easy.in",
				//"c_no_hurry.in", 
		String[] files = {
				//"e_high_bonus.in",
				"d_metropolis.in",
				};
		
		
		for (String inputFile : files) {
			System.out.println("Running for input '" + inputFile + "' ...");
			// Scanner in = new Scanner(System.in);
			Scanner in = new Scanner(new File(inputFile));
			
			// PrintStream out = System.out;
			PrintStream out = new PrintStream(inputFile + ".out");
			
			
			try (Scanner s = in) {
				try (PrintStream o = out) {
					Map m = new Map(s);
					m.greedy();
					m.toOutput(o);
				}
			}
			System.out.println("Output printed to '" + inputFile + ".out'");
			
		}
		
	}
	
}
