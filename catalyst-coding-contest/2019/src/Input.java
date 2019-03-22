import java.awt.Point;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Input {
	
	// public data from input
	public int WX, WY;
	public int startX, startY;

	public List<Cmd> cmds = new ArrayList<>();
	
	public List<Point> path = new ArrayList<>();

	public double alienHealth;
	public double alienSpeed;

	public int[] alienStartTicks;
	
	public double towerDamage;
	public double towerRange;
	
	public int towerPrice;
	public int budget;
	
	
	public Input(InputStream in) {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(in);

		WX = s.nextInt();
		WY = s.nextInt();
		startX = s.nextInt();
		startY = s.nextInt();
		
		s.nextLine();
		String line = s.nextLine();
		String[] spl = line.split(" ");
		
		for (int i = 0; i < spl.length; i+=2) {
			Cmd c = new Cmd();
			c.cmd = spl[i].toCharArray()[0];
			c.n = Integer.parseInt(spl[i+1]);
			cmds.add(c);
		}
		
		int x = startX, y = startY;
		path.add(new Point(x, y));
		
		Direction currD = Direction.E;
		
		for (Input.Cmd cmd : cmds) {
			if(cmd.cmd == 'T') {
				for (int i = 0; i < cmd.n; i++)
					currD = currD.next();
			}
			else if (cmd.cmd == 'F') {
				for (int i = 0; i < cmd.n; i++) {
					x += currD.xInc;
					y += currD.yInc;
					path.add(new Point(x, y));
				}
			}
		}
		
		
		// aliens

		alienHealth = Double.parseDouble(s.next());
		alienSpeed = Double.parseDouble(s.next());
		
		alienStartTicks = new int[s.nextInt()];
		
		for (int i = 0; i < alienStartTicks.length; i++) {
			alienStartTicks[i] = s.nextInt();
		}

		towerDamage = Double.parseDouble(s.next());
		towerRange = Double.parseDouble(s.next());
		towerPrice = s.nextInt();
		budget = s.nextInt();
		
		
	}
	
	
	
	static class Query {
		int tick;
		int alien;
	}
	
	
	
	static class Cmd {
		char cmd;
		int n;
	}
	
	

	
	static enum Direction {
		N(0, -1), E(1, 0), S(0, 1), W(-1, 0);
		
		int xInc, yInc;
		
		private Direction(int x, int y) {
			xInc = x;
			yInc = y;
		}
		
		Direction next() {
			int i = ordinal() + 1;
			i %= 4;
			return values()[i];
		}
	}
	
}




