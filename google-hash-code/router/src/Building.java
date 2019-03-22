import java.awt.Point;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Building {
	
	int H, W, R, Pb, Pr, B;

	char[][] inputMap;
	boolean[][] coverable;
	boolean[][] covered;
	boolean[][] routers;
	int[][] bDist;
	int[][] routerCovering;
	
	
	List<Point> backboneCells = new ArrayList<Point>();
	List<Point> routersCells = new ArrayList<Point>();
	
	public int moneySpend = 0;
	
	public boolean loopRunning = false;
	
	public int currentScore = 0;
	
	
	
	String inputFileName;
	
	View view;

	public Building(Scanner in, String f) {
		inputFileName = f;
		
		H = in.nextInt();
		W = in.nextInt();
		R = in.nextInt();
		Pb = in.nextInt();
		Pr = in.nextInt();
		B = in.nextInt();

		covered = new boolean[H][W];
		routerCovering = new int[H][W];
		
		bDist = new int[H][W];
		for (int i = 0; i < H; i++)
			Arrays.fill(bDist[i], -1);
		addBackbone(in.nextInt(), in.nextInt());

		coverable = new boolean[H][W];
		inputMap = new char[H][];
		for (int r = 0; r < H; r++) {
			inputMap[r] = in.next().toCharArray();
			for (int c = 0; c < inputMap[r].length; c++) {
				coverable[r][c] = inputMap[r][c] == '.';
			}
		}
		routers = new boolean[H][W];
		
		updateRouterCovering();
		
		// outputImage("start");
		
		if (view != null)
			view.update();
	}
	
	long lastUpdateCall = System.currentTimeMillis();

	public void fillWithRouters() {
		loopRunning = true;
		
		for(int i = 0; loopRunning; i++) {
			System.out.println("Iteration " + i + "...");
			
			if (!placeBestRouterWithBackbone())
				break;
			

			if (view != null) {
				if (lastUpdateCall < System.currentTimeMillis() - 1000) {
					view.update();
					lastUpdateCall = System.currentTimeMillis();
				}
			}
				
			
		}
		
		loopRunning = false;

		view.update();
		
		updateScore(); // to be sure
		
		// outputImage("score_" + currentScore);
		// outputFile("score_" + currentScore);
		
		System.out.println("Computing ended with score: " + currentScore);
		
	}
	
	
	private void updateScore() {
		int s = B - moneySpend;
		
		for (int r = 0; r < H; r++) {
			for (int c = 0; c < W; c++) {
				if (coverable[r][c] && covered[r][c])
					s += 1000;
			}
		}
		
		currentScore = s;
	}
	
	
	
	
	public synchronized boolean placeBestRouterWithBackbone() {
		int bestR = -1, bestC = -1;
		long bestScore = Long.MIN_VALUE;
		
		for (int r = 0; r < H; r++) {
			for (int c = 0; c < W; c++) {
				if (!coverable[r][c])
					continue;
				int cost = bDist[r][c] * Pb + Pr;
				if (cost + moneySpend > B)
					continue;
				long score = 1000 * routerCovering[r][c] - cost;
				if (score > bestScore) {
					bestScore = score;
					bestR = r;
					bestC = c;
				}
			}
		}
		
		if (bestR == -1 || bestScore < 0) {
			System.err.println("Can't found any router or the best router remove points from the score.");
			return false;
		}
		
		return placeRouterWithBackbone(bestR, bestC);
	}
	
	
	public synchronized boolean placeRouterWithBackbone(int r, int c) {

		int cost = bDist[r][c] * Pb + Pr;
		
		if (cost + moneySpend > B) {
			System.err.println("Can't place router because it cost more than the remaining budget. pos{r="+r+";c="+c+"}"
				+ "- budget{alreadySpend="+moneySpend+";remaining="+(B - moneySpend)+";currCost="+cost+"} ");
			return false;
		}
		int covering = routerCovering[r][c];
		long score = 1000 * covering - cost;
		
		routersCells.add(new Point(r, c));
		computeRouterCovering(r, c, true);
		routers[r][c] = true;
		
		moneySpend += Pr; // only router cost, bb cost added in addBackbone()
		
		addBackbone(r, c);
		
		updateRouterCovering();
		
		currentScore += score;
		
		System.out.println("Router placed with backbones : pos{r="+r+";c="+c+"} "
				+ "- score{add="+score+";new="+currentScore+"} "
				+ "- budget{currCost="+cost+";totalSpend="+moneySpend+";remaining="+(B - moneySpend)+"} "
				+ "- covering="+covering);
		
		return true;
		
	}
	
	public synchronized void removeRouter(int r, int c) {
		if (!routers[r][c])
			return;
		
		routers[r][c] = false;
		routersCells.remove(new Point(r, c));
		
		
		// reset all covering map
		covered = new boolean[H][W];
		// refill all covering map
		for (Point p : routersCells) {
			computeRouterCovering((int)p.getX(), (int)p.getY(), true);
		}
		
		updateRouterCovering();
		
		moneySpend -= Pr;
		
		updateScore();
		
		System.out.println("Router removed : pos{r="+r+";c="+c+"}");
	}
	
	
	public synchronized void addBackbone(int bR, int bC) {

		Queue<Runnable> queue = new LinkedList<>();
		
		if (bDist[bR][bC] != -1) { // there is already one backbone in the map
			
			moneySpend += bDist[bR][bC] * Pb;
			
			List<Point> bbSubList = new ArrayList<>();
			int currD = bDist[bR][bC];
			while (currD > 0) {
				bDist[bR][bC] = 0;
				bbSubList.add(new Point(bR, bC));
				final int fR = bR, fC = bC;
				queue.offer(() -> dijkstraBDist(queue, fR, fC, 0));
				
				int nextD = currD - 1;
				boolean tColl = bR<=0, bColl = bR+1>=H;
				boolean lColl = bC<=0, rColl = bC+1>=W;
				
				if (!tColl && !rColl && bDist[bR-1][bC+1] == nextD) { bR--; bC++; }
				else if (!bColl && !rColl && bDist[bR+1][bC+1] == nextD) { bR++; bC++; }
				else if (!tColl && !lColl && bDist[bR-1][bC-1] == nextD) { bR--; bC--; }
				else if (!bColl && !lColl && bDist[bR+1][bC-1] == nextD) { bR++; bC--; }
				else if (!tColl && bDist[bR-1][bC] == nextD) { bR--; }
				else if (!lColl && bDist[bR][bC-1] == nextD) { bC--; }
				else if (!bColl && bDist[bR+1][bC] == nextD) { bR++; }
				else if (!rColl && bDist[bR][bC+1] == nextD) { bR--; }
				currD = nextD;
			}
			Collections.reverse(bbSubList);
			backboneCells.addAll(bbSubList);
			
		}
		else { // this is the first backbone cell added (the one in the input
			bDist[bR][bC] = 0;
			final int fR = bR, fC = bC;
			queue.offer(() -> dijkstraBDist(queue, fR, fC, 0));
		}
		
		
		while (!queue.isEmpty())
			queue.poll().run();
		
	}
	
	private void dijkstraBDist(Queue<Runnable> q, int pR, int pC, int v) {
			
		for (int r = pR - 1; r <= pR + 1; r++) {
			for (int c = pC - 1; c <= pC + 1; c++) {
				if (c < 0 || r < 0 || c >= W || r >= H || (c == pC && r == pR))
					continue; // ignore current point
				int R = r, C = c;
				if (bDist[r][c] == -1 || bDist[r][c] > v + 1) {
					bDist[r][c] = v + 1;
					q.offer(() -> dijkstraBDist(q, R, C, v + 1));
				}
			}
		}
	}
	
	
	
	

	public void updateRouterCovering() {
		for (int r = 0; r < H; r++) {
			for (int c = 0; c < W; c++) {
				if (!coverable[r][c])
					continue;
				routerCovering[r][c] = computeRouterCovering(r, c, false);
			}
		}
	}
	
	
	public int computeRouterCovering(int r0, int c0, boolean applyCovering) {
		int count = checkCovered(r0, c0, applyCovering) ? 0 : 1;
		int minCb = c0, maxCb = c0, minCt = c0, maxCt = c0;

		// left
		for (int c = c0 - 1; c >= c0 - R; c--) {
			if (!coverable[r0][c])
				break;
			minCb = minCt = c;
			if (!checkCovered(r0, c, applyCovering))
				count++;
		}
		// right
		for (int c = c0 + 1; c <= c0 + R; c++) {
			if (!coverable[r0][c])
				break;
			maxCb = maxCt = c;
			if (!checkCovered(r0, c, applyCovering))
				count++;
		}
		
		// top
		for (int r = r0 - 1; r >= r0 - R; r--) {
			if (!coverable[r][c0])
				break;
			if (!checkCovered(r, c0, applyCovering))
				count++;
			// top left
			for (int c = c0 - 1; c >= minCt; c--) {
				if (!coverable[r][c]) {
					minCt = c + 1;
					break;
				}
				if (!checkCovered(r, c, applyCovering))
					count++;
			}
			// top right
			for (int c = c0 + 1; c <= maxCt; c++) {
				if (!coverable[r][c]) {
					maxCt = c - 1;
					break;
				}
				if (!checkCovered(r, c, applyCovering))
					count++;
			}
		}
		
		// bottom
		for (int r = r0 + 1; r <= r0 + R; r++) {
			if (!coverable[r][c0])
				break;
			if (!checkCovered(r, c0, applyCovering))
				count++;
			// bottom left
			for (int c = c0 - 1; c >= minCb; c--) {
				if (!coverable[r][c]) {
					minCb = c + 1;
					break;
				}
				if (!checkCovered(r, c, applyCovering))
					count++;
			}
			// bottom right
			for (int c = c0 + 1; c <= maxCb; c++) {
				if (!coverable[r][c]) {
					maxCb = c - 1;
					break;
				}
				if (!checkCovered(r, c, applyCovering))
					count++;
			}
		}
		
		return count;
		
	}
	
	
	private boolean checkCovered(int r, int c, boolean coverIfCoverable) {
		boolean v = covered[r][c];
		if (!v && coverIfCoverable)
			covered[r][c] = true;
		return v;
	}


	
	
	
	
	public synchronized void outputFile(String suffix) {
		String fileName = inputFileName + ".dat/" + inputFileName + "." + suffix + ".out";
		try (PrintStream o = new PrintStream(fileName)) {
			o.println(backboneCells.size());
			for (Point bb : backboneCells)
				o.println(bb.x + " " + bb.y);
			o.println(routersCells.size());
			for (Point r : routersCells)
				o.println(r.x + " " + r.y);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public synchronized void outputImage(String suffix) {
		String fileName = inputFileName + ".dat/" + inputFileName + "." + suffix + ".ppm";
		try (PrintStream out = new PrintStream(fileName)) {
			out.println("P3 " + W + " " + (3*H) + " 255");
			
			float cR, cG, cB;
			
			// first image
			// --- Fist layer
			//  0  0  0    = wall
			// .6 .6 .6    = empty
			//  0  1  0    = router
			// .5  1 .5    = covered target
			//  1  1  1    = non covered target
			// --- second layer (4th value is alpha, default 1)
			//  0  0  1 .5 = backbone
			for (int r = 0; r < H; r++) {
				for (int c = 0; c < W; c++) {
					if (inputMap[r][c] == '#')
						cR = cG = cB = 0;
					else if (inputMap[r][c] == '-')
						cR = cG = cB = .6f;
					else if (routers[r][c]) {
						cR = cB = 0; cG = 1;
					}
					else if (covered[r][c]) {
						cR = cB = .5f; cG = 1;
					}
					else
						cR = cG = cB = 1;
					
					if (bDist[r][c] == 0) {
						cR = applyAlpha(cR, 0, .5f);
						cG = applyAlpha(cG, 0, .5f);
						cB = applyAlpha(cB, 1, .5f);
					}
					out.println(outColor(cR, cG, cB));
				}
			}
			
			
			int maxCovering = 4 * R + 4 * R * R + 1;
			for (int r = 0; r < H; r++) {
				for (int c = 0; c < W; c++) {
					if (!coverable[r][c])
						cR = cG = cB = 0;
					else {
						int covering = routerCovering[r][c];
						float alpha = covering / (float)maxCovering;
						cG = applyAlpha(1, .5f, alpha);
						cR = cB = applyAlpha(1, 0, alpha);
					}
					out.println(outColor(cR, cG, cB));
				}
			}
			
			
			int maxDistance = Math.max(W, H) / 2;
			for (int r = 0; r < H; r++) {
				for (int c = 0; c < W; c++) {
					if (!coverable[r][c])
						cR = cG = cB = 0;
					else {
						if (covered[r][c])
							cR = cG = cB = .6f;
						else
							cR = cG = cB = 1;
						
						int distance = bDist[r][c];
						if (distance == 0) { // bb
							cR = applyAlpha(cR, 0, .5f);
							cG = applyAlpha(cG, 0, .5f);
							cB = applyAlpha(cB, 1, .5f);
						}
						else {
							float alpha = Math.max(0, .5f - distance / (float)maxDistance * .5f);
							cR = applyAlpha(cR, 0, alpha);
							cG = applyAlpha(cG, 1, alpha);
							cB = applyAlpha(cB, 1, alpha);
						}
					}
						
					out.println(outColor(cR, cG, cB));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String outColor(float r, float g, float b) {
		return Math.round(r * 255) + " " + Math.round(g * 255) + " " + Math.round(b * 255);
	}
	
	static float applyAlpha(float base, float color, float opacity) {
		return base + opacity * (color - base);
	}
	
	
	
	
	
	public static void displayIntMatrix(int[][] m) {
		for (int r = 0; r < m.length; r++) {
			System.out.println(Arrays.toString(m[r]));
		}
	}
	
	public static void displayBooleanMatrix(boolean[][] m) {
		for (int r = 0; r < m.length; r++) {
			for (int c = 0; c < m[r].length; c++) {
				System.out.print(m[r][c] ? '#' : '.');
			}
			System.out.println();
		}
	}
	
	
	
}
