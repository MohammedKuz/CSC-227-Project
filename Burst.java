public class Burst {
    
    private burstType burst_type;
	private int memory;
	private int remainingtime;
	
	public Burst() {
		this.memory = 0;
    }
    
	public Burst(burstType burst_type, int memory, int remainingtime) {
		this.burst_type = burst_type;
		this.remainingtime = remainingtime;
		if (burst_type == burstType.CPUBurst)
			this.memory = memory;
    }

    public void decRemainingtime() {
		remainingtime--;
    }
    
    //Setters and Getters
	public burstType getBurst_type() {  return burst_type;  }
	public void setBurst_type(burstType burst_type) {
		this.burst_type = burst_type;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getMemory() {  return memory;  }

	public int getRemainingtime() {  return remainingtime;  } 

}