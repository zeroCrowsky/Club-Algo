package rq_p2_trouble_sort;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	
	public static void main(String[] args) {
		try (Scanner s = new Scanner(new BufferedInputStream(System.in))) {
			
			int n = s.nextInt();
			for (int i = 0; i < n; i++) {
				int nn = s.nextInt();
				
				List<Integer> v1 = new ArrayList<>(nn / 2 + 1);
				List<Integer> v2 = new ArrayList<>(nn / 2);
				
				for (int j = 0; j < nn; j++)
					(j % 2 == 0 ? v1 : v2).add(s.nextInt());
				
				v1.sort(null);
				v2.sort(null);
				
				int error = -1;
				for (int j = 0; j < nn - 1; j++) {
					boolean jPair = j % 2 == 0;
					if ((jPair ? v1 : v2).get(j / 2) > (jPair ? v2 : v1).get((j + 1) / 2)) {
						error = j;
						break;
					}
				}
				
				System.out.println("Case #" + (i + 1) + ": " + (error == -1 ? "OK" : error));
			}
		}
	}
}
