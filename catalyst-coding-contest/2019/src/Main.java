import java.awt.Point;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static Input in;
	static int tick;
	static int[][] covering;
	
	public static void main(String[] args) {

		
		in = new Input(System.in);
		
		// compute and print here
		
		try (PrintStream out = new PrintStream("out.out")) {
			
			
			
			
			covering = new int[in.WX][in.WY];

			for (Point p : in.path) {
				covering[p.x][p.y] = -1;
			}
			
			int tRange = (int)Math.ceil(in.towerRange);
			
			for (Point p : in.path) {
				
				for (int x = Math.max(0, p.x - tRange); x < in.WX && x < p.x + tRange; x++) {
					for (int y = Math.max(0, p.y - tRange); y < in.WY && y < p.y + tRange; y++) {
						if (covering[x][y] >= 0 && p.distance(x, y) <= in.towerRange)
							covering[x][y]++;
					}
				}
			}
			
			int nbTower = in.budget / in.towerPrice;

			List<TowerStatus> towers = new ArrayList<>(); 
			
			for (int i = 0; i < nbTower; i++) {
				int bestCovering = 0;
				int bestX = 0, bestY = 0;
				for (int x = 0; x < in.WX; x++) {
					for (int y = 0; y < in.WY; y++) {
						if (covering[x][y] > bestCovering) {
							bestCovering = covering[x][y];
							bestX = x;
							bestY = y;
						}
					}
				}
				
				if (bestCovering > 0) {
					TowerStatus ts = new TowerStatus(i, bestX, bestY);
					towers.add(ts);
					covering[bestX][bestY] = -2;
					Point pt = ts.pos();
					for (int x = Math.max(0, pt.x - tRange); x < in.WX && x < pt.x + tRange; x++) {
						for (int y = Math.max(0, pt.y - tRange); y < in.WY && y < pt.y + tRange; y++) {
							if (covering[x][y] == -1 && pt.distance(x, y) <= in.towerRange)
								covering[x][y] = -3;
							
						}
					}
					
					
					for (int x = 0; x < in.WX; x++) {
						for (int y = 0; y < in.WY; y++) {
							if (covering[x][y] > 0)
								covering[x][y] = 0;
						}
					}
					for (Point p : in.path) {
						//if (covering[p.x][p.y] != -1)
						//	continue;
						for (int x = Math.max(0, p.x - tRange); x < in.WX && x < p.x + tRange; x++) {
							for (int y = Math.max(0, p.y - tRange); y < in.WY && y < p.y + tRange; y++) {
								if (covering[x][y] >= 0 && p.distance(x, y) <= in.towerRange)
									covering[x][y]++;
							}
						}
					}
					
					
					
				}
				else
					break;
			}

			for (int i = 0; i < towers.size() && i < nbTower; i++) {
				TowerStatus ts = towers.get(i);
				covering[ts.x][ts.y] = -2;
				System.out.println(ts.x + " " + ts.y);
			}
			
			
			outputImage("map.ppm");
			
			
			
			List<Alien> aliens = new ArrayList<>();
			for (int i = 0; i < in.alienStartTicks.length; i++) {
				aliens.add(new Alien(i));
			}
			
			System.err.println("PathLength: " + in.path.size());
			
			for (tick = 0;; tick++) {
				
				// check alien reached the end
				for (Alien a : aliens) {
					if (a.reachedEnd()) {
						for (Alien aa : aliens)
							if(aa.alive())
								System.err.println("Alien " + aa.id + "  reachEnd=" + aa.reachedEnd() + " life="+aa.life + " progress=" + aa.progress());
						end(false);
					}
				}
				
				// tower shots
				
				for (TowerStatus t : towers) {
					if (t.locked() && aliens.get(t.target).alive() && t.inRange(aliens.get(t.target).pos())) {
						aliens.get(t.target).tempDamage += in.towerDamage;
					}
					else {
						Alien closest = null;
						for (Alien a : aliens) {
							if (!a.alive())
								continue;
							if (!t.inRange(a.pos()))
								continue;
							
							if (closest == null || t.distance(a.pos()) < t.distance(closest.pos())) {
								closest = a;
							}
							
						}
						
						if (closest == null) {
							t.target = -1;
						}
						else {
							t.target = closest.id;
							closest.tempDamage += in.towerDamage;
						}
					}
				}
				// apply damage
				for (Alien a : aliens) {
					a.life -= a.tempDamage;
					a.tempDamage = 0;
				}
				
				// check if no alien remaining
				boolean end = true;
				for (Alien a : aliens) {
					if (a.life > 0)
						end = false;
				}
				
				if (end)
					end(true);
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	static void end(boolean win) {
		System.out.println(tick);
		System.out.println(win ? "WIN" : "LOSS");
		System.out.flush();
		System.exit(0);
	}
	
	
	
	static class TowerSpot {
		int x, y, covering;
		
		
	}
	
	static class TowerStatus {
		int x, y;
		int id;
		int target = -1;
		TowerStatus(int i, int x, int y) {
			id = i;
			this.x = x;
			this.y = y;
		}
		
		boolean locked() {
			return target >= 0;
		}
		
		boolean inRange(Point p) {
			return distance(p) <= in.towerRange;
		}
		
		double distance(Point p) {
			return p.distance(x, y);
		}
		
		Point pos() {
			return new Point(x, y);
		}
	}
	
	
	static class Alien {
		int id;
		double life;
		double tempDamage = 0;
		
		public Alien(int i) {
			id = i;
			life = in.alienHealth;
		}
		
		double progress() {
			int t = tick - in.alienStartTicks[id];
			if (t < 0)
				return 0;
			return t * in.alienSpeed;
		}
		
		Point pos() {
			return in.path.get((int)progress());
		}
		
		boolean reachedEnd() {
			return alive() && ((int)progress()) >= in.path.size();
		}
		
		boolean hasSpawned() {
			return tick >= in.alienStartTicks[id];
		}
		
		boolean alive() {
			return life > 0 && hasSpawned();
		}
	}
	
	

	public static void outputImage(String filename) {
		try (PrintStream out = new PrintStream(filename)) {
			out.println("P3 " + in.WX + " " + in.WY + " 255");
			
			float cR, cG, cB;
			
			// first image
			// --- Fist layer
			//  0  0  0    = path
			//  1  1  1    = empty
			//  1  0  0    = tower
			for (int y = 0; y < in.WY; y++) {
				for (int x = 0; x < in.WX; x++) {
					if (covering[x][y] == -2) {
						cR = 1;
						cG = cB = 0;
					}
					else if (covering[x][y] == -3) {
						cR = 0;
						cG = cB = 1;
					}
					else if (covering[x][y] == -1)
						cR = cG = cB = 0;
					else {
						cG = 1;
						cR = cB = 1 - covering[x][y]/20f;
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
	
	
	
	
}
