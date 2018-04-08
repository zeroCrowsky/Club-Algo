package p2_trouble_sort;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Solution {
	
	public static void main(String[] args) {
		try (Scanner s = new Scanner(new BufferedInputStream(System.in))) {
			
			int n = s.nextInt();
			for (int i = 0; i < n; i++) {
				int nn = s.nextInt();
				int[] v = new int[nn];
				for (int j = 0; j < nn; j++)
					v[j] = s.nextInt();
				
				troubleSort(v);
				
				int error = -1;
				for (int j = 0; j < nn - 1; j++) {
					if (v[j] > v[j + 1]) {
						error = j;
						break;
					}
				}
				
				System.out.println("Case #" + (i + 1) + ": " + (error == -1 ? "OK" : error));
			}
		}
	}
	
	public static void troubleSort(int[] v) {
		boolean ok;
		do {
			ok = true;
			for (int i = 0; i < v.length - 2; i++) {
				if (v[i] > v[i + 2]) {
					int tmp = v[i + 2];
					v[i + 2] = v[i];
					v[i] = tmp;
					ok = false;
				}
			}
		} while (!ok);
	}
}
