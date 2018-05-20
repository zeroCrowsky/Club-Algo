import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

// https://open.kattis.com/problems/tight
public class tight {
	
	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			for(;;) {
				int k = in.nextInt(), n = in.nextInt();
				
				if (k == 0) {
					System.out.println("100.0");
					continue;
				}
				
				double divizor = Math.pow(k+1, n);
				
				double[][] dyn = new double[n+1][k+1];
				
				// init first line
				for (int j = 0; j <= k; j++) {
					dyn[1][j] = 1;
				}
				for (int i = 2; i <= n; i++) {
					dyn[i][0] = dyn[i-1][0] + dyn[i-1][1];
					dyn[i][k] = dyn[i-1][k-1] + dyn[i-1][k];
					for (int j = 1; j < k; j++) {
						dyn[i][j] = dyn[i-1][j-1] + dyn[i-1][j] + dyn[i-1][j+1];
					}
				}
				
				double r = 0;
				for (int j = 0; j <= k; j++) {
					r += dyn[n][j];
				}
				
				System.out.println(new BigDecimal(r / divizor * 100).toPlainString());
			}
			
		} catch (NoSuchElementException e) {
			// expected
		}
	}
	
	
	
}
