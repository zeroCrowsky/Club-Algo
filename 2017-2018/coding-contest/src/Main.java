import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Main {
	
	public static void main(String[] args) {
		
		Input in = new Input(System.in);
		
		// compute and print here
		
		Arrays.sort(in.images, (i1, i2) -> Integer.compare(i1.timestamp, i2.timestamp));
		
		List<Output> out = new ArrayList<>();
		
		
		
		
		
		
		for (Image i : in.images) {
			if (!i.asteroide)
				continue;
			
			boolean exist = false;
			for(Output o : out) {
				if (o.ref.get(0).equalsShape(i)) {
					System.err.println(i.timestamp+"=="+o.ref.get(0).timestamp);
					// check
					if (o.checkCanGoInside(i, in.images, in.start, in.end)) {
						exist = true;
						o.ref.add(i);
						// System.err.println("add     "+i.timestamp+" to " + o.minT);
						o.maxT = i.timestamp;
						o.count++;
						if (o.ref.size() == 2) {
							o.rotations = o.ref.get(0).getPossibleRotations(o.ref.get(1));
						}
						
						break;
					}
				}
				else {
					System.err.println(i.timestamp+"=="+o.ref.get(0).timestamp);
				}
			}
			
			if (!exist) {
				Output o = new Output();
				o.minT = o.maxT = i.timestamp;
				o.count = 1;
				o.ref.add(i);
				// System.err.println("add new "+i.timestamp);
				out.add(o);
			}
		}
		
		
		
		for (Output o : out) {
			System.out.println(o.minT + " " + o.maxT + " " + o.count);
			//System.out.println("  " + o.minT+" rotations=" + o.rotations);
			//o.ref.get(0).printAsteroid(System.out);
			//if (o.count > 1) o.ref.get(1).printAsteroid(System.out);
		}
		
		
		
	}
	
	
	
	
	
	
	static class Output {
		List<Image> ref = new ArrayList<>();
		Set<Image.Rotation> rotations;
		int minT, maxT, count;
		
		boolean checkCanGoInside(Image i, Image[] all, int tMin, int tMax) {
			if (ref.size() > 1) {
				int t0 = ref.get(0).timestamp;
				int t1 = ref.get(1).timestamp;
				int interval = t1 - t0;
				if ((i.timestamp - t0) % interval == 0) {
					Set<Image.Rotation> newRotation = ref.get(ref.size()-1).getPossibleRotations(i);
					newRotation.retainAll(new HashSet<>(rotations));
					if (newRotation.isEmpty())
						return false;
					Set<Image.Rotation> newRotation2 = ref.get(ref.size()-2).getPossibleRotations(i);
					Set<Image.Rotation> testRotation2 = rotations.stream()
							.map(r -> r.getDouble())
							.collect(Collectors.toSet());
					newRotation2.retainAll(new HashSet<>(testRotation2));
					if (newRotation2.isEmpty())
						return false;
					
					
					rotations = newRotation;
					return true;
				}
			}
			
			int t0 = ref.get(0).timestamp;
			int t1 = i.timestamp;
			int interval = t1 - t0;
			
			if (t0 - interval >= tMin)
				return false;
			
			boolean ok = true;
			Image previous = i;
			Set<Image.Rotation> tmpRots = new HashSet<>();
			for (Image.Rotation rot : Image.Rotation.values())
				tmpRots.add(rot);
			for (int t = t1 + interval; t <= tMax && ok; t += interval) {
				boolean okok = false;
				for (Image ii : all) {
					if (ii.timestamp == t) {
						okok = true;
						Set<Image.Rotation> r = previous.getPossibleRotations(ii);
						r.retainAll(new HashSet<>(tmpRots));
						if (r.isEmpty()) {
							okok = false;
							break;
						}
						tmpRots = r;
						previous = ii;
					}
				}
				if (!okok)
					ok = false;
			}
			
			return ok;
		}
		
		
	}
	
	
}
