import java.util.Scanner;

public class Solution {
	
	public static void main(String[] args) {
		try (Scanner s = new Scanner(System.in)) {
			int n = s.nextInt();
			for (int i = 0; i < n; i++) {
				int d = s.nextInt();
				String p = s.next();
				
				if (minimumDamage(p) > d) {
					System.out.println("Case #" + (i+1) + ": IMPOSSIBLE");
				}
				else {
					int nbHack = 0;
					while (totalDamage(p) > d) {
						
						char[] pArr = p.toCharArray();
						
						for (int j = pArr.length - 2; j >= 0; j--) {
							if (pArr[j] == 'C' && pArr[j+1] == 'S') {
								pArr[j] = 'S';
								pArr[j+1] = 'C';
								break;
							}
						}
						p = new String(pArr);
						nbHack++;
					}
					System.out.println("Case #" + (i+1) + ": " + nbHack);
				}
			}
		}
	}
	
	public static int totalDamage(String p) {
		int damage = 0;
		int currentPower = 1;
		for (char c : p.toCharArray()) {
			if (c == 'C')
				currentPower *= 2;
			else
				damage += currentPower;
		}
		return damage;
	}
	
	public static int minimumDamage(String p) {
		int minDamage = 0;
		for (char c : p.toCharArray()) {
			if (c == 'S')
				minDamage++;
		}
		return minDamage;
	}
	
}
