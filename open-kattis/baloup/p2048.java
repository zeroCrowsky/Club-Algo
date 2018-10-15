import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class p2048 {
	static Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	
	public static void main(String[] args) {
		Integer[][] game = new Integer[4][4];
		
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++)
				game[r][c] = in.nextInt();
		
		int dir = in.nextInt();
		
		if (dir == 0) { // left
			for (int i = 0; i < 4; i++) {
				reverseArray(game[i]);
				game[i] = computeLine(game[i]);
				reverseArray(game[i]);
			}
		}
		else if (dir == 1) { // up
			for (int i = 0; i < 4; i++) {
				Integer[] col = new Integer[4];
				for (int r = 0; r < 4; r++) col[r] = game[3-r][i];
				col = computeLine(col);
				for (int r = 0; r < 4; r++) game[3-r][i] = col[r];
			}
		}
		else if (dir == 2) { // right
			for (int i = 0; i < 4; i++) {
				game[i] = computeLine(game[i]);
			}
		}
		else { // down
			for (int i = 0; i < 4; i++) {
				Integer[] col = new Integer[4];
				for (int r = 0; r < 4; r++) col[r] = game[r][i];
				col = computeLine(col);
				for (int r = 0; r < 4; r++) game[r][i] = col[r];
			}
		}
		

		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 4; c++) {
				if (c > 0)
					System.out.print(' ');
				System.out.print(game[r][c]);
			}
			System.out.println();
		}
		
		
		
	}
	
	
	
	
	// move from first arg to last arg
	public static Integer[] computeLine(Integer[] in) {
		List<Integer> l = new ArrayList<>(4);
		for (Integer i : in)
			if (i != 0)
				l.add(i);
		List<Integer> o = new ArrayList<>(4);
		
		while(!l.isEmpty()) {
			if (l.size() >= 2 && l.get(l.size() - 1).equals(l.get(l.size() - 2))) {
				o.add(0, l.remove(l.size() - 1) * 2);
				l.remove(l.size() - 1);
			}
			else {
				o.add(0, l.remove(l.size() - 1));
			}
		}
		
		while(o.size() < 4)
			o.add(0, 0);
		
		return o.toArray(new Integer[4]);
	}
	
	
	
	
	public static void reverseArray(Integer[] arr) {
		for(int i = 0; i < arr.length / 2; i++)
		{
			Integer temp = arr[i];
		    arr[i] = arr[arr.length - i - 1];
		    arr[arr.length - i - 1] = temp;
		}
	}

}
