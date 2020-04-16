
public class Burst {
	
	private Bursttype burst_type;
	private int memory;
	private int remainingtime;
	
	public Burst() {
		this.memory = 0;
	}
	
	public Burst(Bursttype burst_type, int memory, int remainingtime) {
		this.burst_type = burst_type;
		this.remainingtime = remainingtime;
		if (burst_type == Bursttype.cpuBurst)
			this.memory = memory;
	}

	public Bursttype getBurst_type() {
		return burst_type;
	}

	public int getCpuBurst() {
		return cpuBurst;
	}

	public void setBurst_type(Bursttype burst_type) {
		this.burst_type = burst_type;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getMemory() {
		return memory;
	}

	public int getRemainingtime() {
		return remainingtime;
	}

	public void decRemainingtime() {
		remainingtime--;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	
	@Override
	public String toString() {
		return "Burst{" +
				"burst_type=" + burst_type +
				", memory=" + memory +
				", remainingtime=" + remainingtime +
				'}';
	}
	
	
}
