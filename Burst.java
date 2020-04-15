
public class Burst {

	private Bursttype burst_type;
	private int memory;
	private int ioBurst;
	
	public Burst() {
		this.memory = 0;
	}
	
	public Burst(Bursttype burst_type, int mem) {
		this.burst_type =Bursttype burst_type;
		if(burst_type == cpuBurst)
		memory = mem;
		else
		memory = null;
	}

	public int getCpuBurst() {
		return cpuBurst;
	}

	public void setCpuBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getIoBurst() {
		return ioBurst;
	}

	public void setIoBurst(int ioBurst) {
		this.ioBurst = ioBurst;
	}
	
	@Override
	public String toString() {
		return new String("CPUBurst: "+cpuBurst+" Memory: "+memory+" IOBurst: "+ioBurst);
	}
	
	
}
