package hc2018;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		String inputFile = "example.in";
		
		// Scanner in = new Scanner(System.in);
		Scanner in = new Scanner(new File(inputFile));
		
		// PrintStream out = System.out;
		PrintStream out = new PrintStream(inputFile + ".out");
		
		
		try (Scanner s = in) {
			try (PrintStream o = out) {
				
			}
		}
	}
	
}
