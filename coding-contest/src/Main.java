
public class Main {
	
	public static void main(String[] args) {
		double dist = (new InputL1(System.in).distance());
		
		double time = dist / 250 + 200;
		System.out.println(Math.round(time));
	}
	
}
