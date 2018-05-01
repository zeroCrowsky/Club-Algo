package p2_mysterious_road_signs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * 
 * Actually not a solution
 *
 */
public class Solution {
	
	public static void main(String[] args) {
		
		try (Scanner s = new Scanner(System.in)) {
			int T = s.nextInt();
			for (int t = 0; t < T; t++) {
				int S = s.nextInt();
				
				Sign[] signs = new Sign[S];
				
				for (int i = 0; i < S; i++) {
					signs[i] = new Sign();
					signs[i].D = s.nextInt();
					signs[i].westFace = s.nextInt();
					signs[i].eastFace = s.nextInt();
					signs[i].M = signs[i].D + signs[i].westFace;
					signs[i].N = signs[i].D - signs[i].eastFace;
				}
				
				int maxSize = 1;
				int countSameSize = 0;

				
				for (int i = 0; i < signs.length; i++) {
					int currentCount = 1;

					List<Integer> Ms = new ArrayList<>();
					List<Integer> Ns = new ArrayList<>();
					int M = Integer.MAX_VALUE;
					int N = Integer.MAX_VALUE;

					Ms.add(signs[i].M);
					Ns.add(signs[i].N);
					
					System.err.println(t+" "+signs[i].M+" "+signs[i].N);
					
					for (int j = i + 1; j < signs.length; j++) {
						Sign sign = signs[j];
						
						if (M != Integer.MAX_VALUE && N != Integer.MAX_VALUE) {
							if (sign.M != M && sign.N != N) {
								if (currentCount > maxSize) {
									maxSize = currentCount;
									countSameSize = 1;
								}
								else if (currentCount == maxSize) {
									countSameSize++;
								}
								break;
							}
							else {
								currentCount++;
							}
						}
						else if (M == Integer.MAX_VALUE && N != Integer.MAX_VALUE) {
							if (sign.N == N) {
								Ms.add(sign.M);
							}
							else {
								M = sign.M;
							}
							currentCount++;
						}
						else if (N == Integer.MAX_VALUE && M != Integer.MAX_VALUE) {
							if (sign.M == M) {
								Ns.add(sign.N);
							}
							else {
								N = sign.N;
							}
							currentCount++;
						}
						else if (Ms.size() == 1 && Ns.size() == 1) {
							if (Ms.contains(sign.M)) {
								M = sign.M;
							}
							else
								Ms.add(sign.M);
							if (Ns.contains(sign.N)) {
								N = sign.N;
							}
							else
								Ns.add(sign.N);
							currentCount++;
						}
						else {
							if (!Ms.contains(sign.M)) {
								Ms.add(sign.M);
							}
							if (!Ns.contains(sign.N)) {
								Ns.add(sign.N);
							}
							List<Integer> validM = new ArrayList<>();
							List<Integer> validN = new ArrayList<>();
							for (Integer iM : Ms)
								for (Integer iN : Ns) {
									boolean ok = true;
									for (int ii = j - currentCount; ii <= j; ii++) {
										if (signs[ii].M != iM && signs[ii].N != iN) {
											ok = false;
											break;
										}
									}
									if (ok) {
										validM.add(iM);
										validN.add(iN);
									}
								}

							if (validM.size() == 1)
								M = validM.get(0);
							if (validN.size() == 1)
								N = validN.get(0);
							
							if (validM.isEmpty() || validN.isEmpty()) {
								if (currentCount > maxSize) {
									maxSize = currentCount;
									countSameSize = 1;
								}
								else if (currentCount == maxSize) {
									countSameSize++;
								}
								break;
							}
							else
								currentCount++;
						}
						
						
					}
					if (currentCount > maxSize) {
						maxSize = currentCount;
						countSameSize = 1;
					}
					else if (currentCount == maxSize) {
						countSameSize++;
					}
				}
				
				
				System.out.println("Case #" + (t+1) + ": "+maxSize+" "+countSameSize);
			}
		}
	}
	
	
	
	
	
	
	public static class Sign {
		int D, westFace, eastFace, M, N;
	}
	
	
}
