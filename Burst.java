
public class Burst {

	private int cpuBurst;
	private int memory;
	private int ioBurst;
	
	public Burst() {
		this.cpuBurst = 0;
		this.memory = 0;
		this.ioBurst = 0;
	}
	
	public Burst(int cpu, int mem, int io) {
		cpuBurst = cpu;
		memory = mem;
		ioBurst = io;
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
