import java.util.Queue;

public class PCB {

	private int PID; // Process ID
	private int arrivalTime; // Process arrival time
	private int loadedTime; // When it was loaded in Ready queue
	private ProcessState PState; // Process State
	public Queue<Burst> burstQueue; // Queue of bursts
	private int PrSize; // Full size of program MB
	private Burst currentBurst; // The current burst in PCB

	// Counter attributes
	private int CPUCounter; // Number of time it was in the CPU (RUNNING state)
	private int IOCounter; // Number of times it preformed an IO (WAITING state)
	private int memoryCounter; // Number of times this process requested memory allocation
	private int preemptionCounter;// Number of times it was preempted

	// Time attributes (All in MS)
	private int IOTime; // Total time it was executed in IO
	private int CPUTotalTime; // Total time it was executing in CPU
	private int finishTime; // Time this process TERMINATED or KILLED

	PCB(int PID, int arrivalTime, int PrSize, Queue<Burst> burstQueue) {
		this.PID = PID;
		this.loadedTime = -1;
		this.arrivalTime = arrivalTime;

		this.CPUCounter = 0;
		this.IOCounter = 0;
		this.IOTime = 0;
		this.memoryCounter = 0;
		this.CPUTotalTime = 0;
		this.finishTime = 0;

		this.PrSize = PrSize;
		this.PState = ProcessState.WAITING;

		// Set first element of the queue as currentBurst
		this.currentBurst = burstQueue.poll();

		// Put the rest of the queue in burstQueue for future use
		this.burstQueue = burstQueue;
	}

	public String toString() {
		return ("Process ID: " + this.PID + "\nProgram name: " + this.PID
				+ "\nWhen it was loaded into the ready queue: " + this.loadedTime
				+ "\nNumber of times it was in the CPU: " + this.CPUCounter + "\nTotal time spent in the CPU: "
				+ this.CPUTotalTime + "\nNumber of times it performed an IO: " + this.IOCounter
				+ "\nTotal time spent in performing IO: " + this.IOTime
				+ "\nNumber of times it was waiting for memory: " + this.memoryCounter
				+ "\nNumber of times its preempted (stopped execution because another process replaced it): "
				+ this.preemptionCounter + "\nTime it terminated or was killed: " + (this.finishTime)
				+ "\nIts final state: Killed or Terminated: " + this.PState);
	}

	// This method will be used for PriorityQueue prioritization comparator
	public int compareTo(Object obj) {
		return this.currentBurst.getRemainingtime() - ((PCB) obj).currentBurst.getRemainingtime();
	}

	Burst nextBurst() {
		this.currentBurst = this.burstQueue.poll();
		return this.currentBurst;
	}

	// Prosess states
	void killProcess() {
		this.setfinishTime(Clock.getCurrentTime());
		this.setPState(ProcessState.KILLED);
		RAM.subtractFromUsage(this.PrSize);
		OS.addFinishedProcess(this);
		if (OS.isFullyFinished())
			OS.stopOS();
	}

	void terminateProcess() {
		this.setfinishTime(Clock.getCurrentTime());
		this.setPState(ProcessState.TERMINATED);
		RAM.subtractFromUsage(this.PrSize);
		OS.addFinishedProcess(this);
		if (OS.isFullyFinished())
			OS.stopOS();
	}

	void letProcessWait() {
		this.setPState(ProcessState.WAITING);
		this.incrementMemoryCounter();
		RAM.subtractFromUsage(this.PrSize);
		RAM.addToJobQueue(this);
	}

	// dont need it
	void letProcessReady() {
		this.setPState(ProcessState.READY);
		RAM.addToReadyQueue(this);
	}

	// Incrementaions
	void incrementCPUCounter() {
		this.CPUCounter++;
	}

	void incrementIOCounter() {
		this.IOCounter++;
	}

	void incrementMemoryCounter() {
		this.memoryCounter++;
	}

	void incrementIOTime() {
		this.IOTime++;
	}

	void incrementCPUTotalTime() {
		this.CPUTotalTime++;
	}

	void incPreemptionCounter() {
		this.preemptionCounter++;
	}

	// Getters and Setters
	int getPID() {
		return PID;
	}

	int getArrivalTime() {
		return this.arrivalTime;
	}

	void setLoadedTime(int loadedTime) {
		this.loadedTime = loadedTime;
	}

	ProcessState getPState() {
		return PState;
	}

	void setPState(ProcessState PState) {
		this.PState = PState;
	}

	int getPrSize() {
		return PrSize;
	}

	void setPrSize(int PrSize) {
		this.PrSize = PrSize;
	}

	Burst getCurrentBurst() {
		return this.currentBurst;
	}

	int getCPUCounter() {
		return CPUCounter;
	}

	int getIOCounter() {
		return IOCounter;
	}

	int getMemoryCounter() {
		return memoryCounter;
	}

	int getIOTime() {
		return IOTime;
	}

	int getCPUTotalTime() {
		return CPUTotalTime;
	}

	int getLoadedTime() {
		return loadedTime;
	}

	int getfinishTime() {
		return finishTime;
	}

	void setfinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
}