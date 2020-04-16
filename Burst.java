
public class Burst {

	private Bursttype burst_type;
	private int memory;
	private int remainingtime;

	public Burst(Bursttype burst_type, int memory, int remainingtime) {
		this.burst_type = burst_type;
		this.remainingtime = remainingtime;
		if (burst_type == Bursttype.cpuBurst)
			this.memory = memory;
	}

	public Bursttype getBurst_type() {
		return burst_type;
	}

	public void setBurst_type(Bursttype burst_type) {
		this.burst_type = burst_type;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getRemainingtime() {
		return remainingtime;
	}

	public void decRemainingtime() {
		remainingtime--;
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
