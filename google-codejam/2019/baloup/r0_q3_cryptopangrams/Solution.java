package r0_q3_cryptopangrams;

import java.io.BufferedInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
	static final boolean PARALLEL = false;
	
	static final Scanner in = new Scanner(new BufferedInputStream(System.in));
	
	public static void main(String[] args) {
		List<Case> c = IntStream.rangeClosed(1, in.nextInt()).mapToObj(i -> new Case()).collect(Collectors.toList());
		List<String> results = (PARALLEL ? c.parallelStream() : c.stream()).map(Case::run).collect(Collectors.toList());
		for (int i = 0; i < results.size(); i++) System.out.println("Case #" + (i+1) + ": " + results.get(i));
	}
	
	static class Case {
		// input variables
		BigInteger N;
		int L;
		BigInteger[] ciphertext;
		
		Case() {
			// read input
			N = in.nextBigInteger();
			L = in.nextInt();
			ciphertext = new BigInteger[L];
			for (int i = 0; i < L; i++) {
				ciphertext[i] = in.nextBigInteger();
			}
		}
		
		String run() {
			TreeSet<BigInteger> allPrimes = new TreeSet<>();
			@SuppressWarnings("unchecked")
			List<BigInteger>[] pairsOfPrime = new List[L];
			BigInteger[] orderedPrimes = new BigInteger[L+1];
			char[] output = new char[L+1];
			
			// find all prime numbers
			for (int i = 0; i < L; i++) {
				List<BigInteger> pr = getPrimeFactors(ciphertext[i]);
				allPrimes.addAll(pr);
				pairsOfPrime[i] = pr;
			}
			
			// affect prime numbers to their positions in phrase
			boolean allLettersPlaced = false;
			while (!allLettersPlaced) {
				allLettersPlaced = true;
				for (int i = 0; i < L + 1; i++) {
					if (orderedPrimes[i] != null)
						continue;
					
					if (i == 0) {
						if (nbDiff(pairsOfPrime[i]) == 1) {
							orderedPrimes[i] = pairsOfPrime[i].remove(0);
						}
						else
							allLettersPlaced = false;
					}
					else if (i == L) {
						if (nbDiff(pairsOfPrime[i - 1]) == 1) {
							orderedPrimes[i] = pairsOfPrime[i - 1].remove(0);
						}
						else
							allLettersPlaced = false;
					}
					else {
						List<BigInteger> pair1 = pairsOfPrime[i - 1], pair2 = pairsOfPrime[i];
						if (nbDiff(pair1) == 1) { // pair1 has only one letter (or 2 identical)
							orderedPrimes[i] = pair1.remove(0);
							pair2.remove(orderedPrimes[i]);
						}
						else if (nbDiff(pair2) == 1) { // pair2 has only one letter (or 2 identical)
							orderedPrimes[i] = pair2.remove(0);
							pair1.remove(orderedPrimes[i]);
						}
						else {
							List<BigInteger> subPair1 = new ArrayList<>(pair1);
							subPair1.retainAll(pair2); // contains only common letter(s)
							if (subPair1.size() == 1) {
								BigInteger n = subPair1.get(0);
								orderedPrimes[i] = n;
								pair1.remove(n);
								pair2.remove(n);
							}
							else
								allLettersPlaced = false;
						}
						
					}
					
				}
			}
			
			Map<BigInteger, Character> primeToChar = new TreeMap<>();
			char l = 'A';
			for (BigInteger pr : allPrimes) {
				primeToChar.put(pr, l);
				l++;
			}
			
			for (int i = 0; i < L + 1; i++) {
				output[i] = primeToChar.get(orderedPrimes[i]);
			}
			
			return new String(output);
		}
	}
	
	public static List<BigInteger> getPrimeFactors(BigInteger n) {
        List<BigInteger> factors = new ArrayList<>(2);
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(n.divide(i)) <= 0; i = i.add(BigInteger.ONE)) {
            while (n.remainder(i).equals(BigInteger.ZERO)) {
                factors.add(i);
                n = n.divide(i);
                factors.add(n); // these two lines were added because we
                return factors; // know that we have only 2 prime numbers
            }
        }
        if (n.compareTo(BigInteger.ONE) > 0) {
            factors.add(n);
        }
        return factors;
    }
	
	public static <T> int nbDiff(List<T> els) {
		if (els.size() <= 1)
			return els.size();
		return (els.get(0).equals(els.get(1))) ? 1 : 2;
	}
	
	
	
}
