import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {







	public static void main(String[] args) throws IOException {

		String[] files = {
				"example.in",
				"charleston_road.in",
				"rue_de_londres.in",
				"opera.in",
				"lets_go_higher.in",
		};


		for (String inputFile : files) {
			System.out.println("Running for input '" + inputFile + "' ...");
			// Scanner in = new Scanner(System.in);
			Scanner in = new Scanner(new File(inputFile));

			// PrintStream out = System.out;
			PrintStream out = new PrintStream(inputFile + ".out");


			try (Scanner s = in) {
				try (PrintStream o = out) {
					Building m = new Building(s, inputFile);
					m.greedy();
					m.toOutput(o);
				}
			}
			System.out.println("Output printed to '" + inputFile + ".out'");

		}
	}

}
