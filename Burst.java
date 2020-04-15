
public class Burst {

	private Bursttype burst_type;
	private int memory;
	
	public Burst() {
		this.memory = 0;
	}
	
	public Burst(Bursttype b, int mem) {
		this.burst_type = b ;
		if(burst_type == Bursttype.cpuBurst)
		memory = mem;
		else
		memory = 0;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public Bursttype getBurst_type() {
		return burst_type;
	}

	public void setBurst_type(Bursttype burst_type) {
		this.burst_type = burst_type;
	}

	@Override
	public String toString() {
		return "Burst{" +
				"burst_type=" + burst_type +
				", memory=" + memory +
				'}';
	}


}
