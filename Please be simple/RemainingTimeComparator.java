import java.util.Comparator;

public class RemainingTimeComparator implements Comparator<PCB>{
	
	@Override
	public int compare(PCB o1, PCB o2) {
		
		return o1.getCurrentBurst().getRemainingtime() - o2.getCurrentBurst().getRemainingtime();
	}
}
