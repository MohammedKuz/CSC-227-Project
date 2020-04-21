import java.util.*;

class ArrivalComparator implements Comparator<PCB>{


	@Override
	public int compare(PCB o1, PCB o2) {
		
		return o1.getArrivalTime() - o2.getArrivalTime();
	}
	
}
