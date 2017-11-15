import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class WERTYU {
	
	static Map<Character, Character> ch = new HashMap<>();
	
	static {
		ch.put('1', '`');
		ch.put('2', '1');
		ch.put('3', '2');
		ch.put('4', '3');
		ch.put('5', '4');
		ch.put('6', '5');
		ch.put('7', '6');
		ch.put('8', '7');
		ch.put('9', '8');
		ch.put('0', '9');
		ch.put('-', '0');
		ch.put('=', '-');
		
		ch.put('W', 'Q');
		ch.put('E', 'W');
		ch.put('R', 'E');
		ch.put('T', 'R');
		ch.put('Y', 'T');
		ch.put('U', 'Y');
		ch.put('I', 'U');
		ch.put('O', 'I');
		ch.put('P', 'O');
		ch.put('[', 'P');
		ch.put(']', '[');
		ch.put('\\', ']');
				
		ch.put('S', 'A');
		ch.put('D', 'S');
		ch.put('F', 'D');
		ch.put('G', 'F');
		ch.put('H', 'G');
		ch.put('J', 'H');
		ch.put('K', 'J');
		ch.put('L', 'K');
		ch.put(';', 'L');
		ch.put('\'', ';');
		
		ch.put('X', 'Z');
		ch.put('C', 'X');
		ch.put('V', 'C');
		ch.put('B', 'V');
		ch.put('N', 'B');
		ch.put('M', 'N');
		ch.put(',', 'M');
		ch.put('.', ',');
		ch.put('/', '.');
		
		ch.put(' ', ' ');
	}
	
	
	
	
	
	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			
			for (;;) {
				
				String s = in.nextLine();
				
				char[] chs = s.toCharArray();
				
				for (int i = 0; i < chs.length; i++) {
					chs[i] = ch.get(chs[i]);
				}
				
				
				
				System.out.println(new String(chs));
				
				
				
			}
			
		} catch (NoSuchElementException e) {
			
		}
	}
}
