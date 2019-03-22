import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	static String[] args;





	public static void main(String[] a) throws IOException {
		args = a;

		launch(a);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/*
		String[] files = {
				"example.in",
				"charleston_road.in",
				"rue_de_londres.in",
				"opera.in",
				"lets_go_higher.in",
		}; */


		//for (String inputFile : files) {}

		//String inputFile = "example.in";
		//String inputFile = "charleston_road.in";
		//String inputFile = "rue_de_londres.in";
		String inputFile = "opera.in";
		//String inputFile = "lets_go_higher.in";
		
		System.out.println("Running for input '" + inputFile + "' ...");
		// Scanner in = new Scanner(System.in);
		Scanner in = new Scanner(new File(inputFile));
		
		File outDir = new File(inputFile + ".dat");
		if (!outDir.exists())
			outDir.mkdirs();

		Building m;

		try (Scanner s = in) {
			m = new Building(s, inputFile);
			m.view = new View(m, primaryStage);
			Thread t = new Thread(() -> {
				m.fillWithRouters();
			}, "WORKING A LOT !!!!!");
			t.start();
		}
		
		
	}

}
