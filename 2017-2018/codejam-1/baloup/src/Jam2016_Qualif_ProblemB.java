import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Jam2016_Qualif_ProblemB {

	public static void main(String[] args) throws Exception {

		try (Scanner s = new Scanner(new File("B-small-practice.in"))) {
			try (PrintStream out = System.out) {// new PrintStream("B-small.out")) {
				
				int t = s.nextInt();
				
				for (int i = 0; i < t; i++) {
					String inStr = s.next().trim();
					
					int count = 0;
					System.out.println(inStr);
					for (int c = inStr.length() - 1; c >= 0; c--) {
						if (inStr.toCharArray()[c] == '+')
							continue;
						
						if (inStr.toCharArray()[0] == '+') {
							inStr = flipN(inStr, 1);
							System.out.println(inStr);
							count++;
						}
						
						inStr = flipN(inStr, c + 1);
						System.out.println(inStr);
						count++;
					}
					
					out.println("Case #"+(i+1)+": "+count);
					
					
					
				}
				
				
				
			}
		}
	}
	
	
	
	public static String flipN(String stack, int n) {
		
		String ret = "";
		
		for (int i = n - 1; i >= 0; i--) {
			if (stack.toCharArray()[i] == '-')
				ret += "+";
			else
				ret += "-";
		}
		
		ret += stack.substring(n);
		
		return ret;
		
	}
	
	

}
