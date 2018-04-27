import java.io.InputStream;
import java.util.Scanner;

public class Input {
	
	// public data from input
	public int start, end;
	
	public Image[] images;
	
	
	
	
	
	public Input(InputStream in) {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(in);
		
		start = s.nextInt();
		end = s.nextInt();
		
		images = new Image[s.nextInt()];
		
		for (int i = 0; i < images.length; i++) {
			int t = s.nextInt();
			images[i] = new Image(s.nextInt(), s.nextInt());
			images[i].timestamp = t;
			for (int r = 0; r < images[i].row; r++) {
				for (int c = 0; c < images[i].col; c++) {
					images[i].data[r][c] = s.nextInt();
					if (images[i].data[r][c] > 0) {
						images[i].minR = Math.min(images[i].minR, r);
						images[i].maxR = Math.max(images[i].maxR, r);
						images[i].minC = Math.min(images[i].minC, c);
						images[i].maxC = Math.max(images[i].maxC, c);
						images[i].asteroide = true;
					}
				}
			}
			
			if (images[i].asteroide) {
				images[i].asteroidShape = new boolean[images[i].maxR - images[i].minR + 1][images[i].maxC - images[i].minC + 1];
				for (int r = images[i].minR; r <= images[i].maxR; r++) {
					for (int c = images[i].minC; c <= images[i].maxC; c++) {
						if (images[i].data[r][c] > 0) {
							images[i].asteroidShape[r-images[i].minR][c-images[i].minC] = true;
						}
					}
				}
				
			}
			
		}
		
	}
	
	
	
}
