
public class Burst {

	private int cpuBurst;
	private int memory;
	private int ioBurst;
	private int remainingtime;
	
	public Burst() {
		this.memory = 0;
	}

	public Burst(int cpuBurst, int memory, int ioBurst, int remainingtime) {
		this.cpuBurst = cpuBurst;
		this.memory = memory;
		this.ioBurst = ioBurst;
		this.remainingtime = remainingtime;
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

	public int getRemainingtime() {
		return remainingtime;
	}

	public void decRemainingtime() {
		remainingtime--;
	}

	@Override
	public String toString() {
		return new String("CPUBurst: "+cpuBurst+" Memory: "+memory+" IOBurst: "+ioBurst);
	}


}
