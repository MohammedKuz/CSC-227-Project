
public class CPU extends Thread {
	private IODevice ioDevice;
	private PCB currentActiveProcess;
	private static int busyTime;
	private static int idleTime;
	static final int TIME = 1;

	CPU(IODevice ioDevice) {
		this.ioDevice = ioDevice;

		this.currentActiveProcess = null;
		CPU.busyTime = 0;
		CPU.idleTime = 0;
	}

	@Override
	public void run() {
//		thread execution point of CPU
		while (true) {
			currentActiveProcess = RAM.getreadyQueue().poll();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (currentActiveProcess != null) {
				handleCurrentProcess();

			} else {
				try {
					synchronized (CPU.currentThread()) {
						Thread.sleep(1);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (Clock.class) {
					Clock.incrementTime();
				}
				CPU.idleTime++;
				if (RAM.getreadyQueue().isEmpty() && RAM.getWaitingQ().isEmpty() && ioDevice.isEmpty())
					OS.stopOS();
			}
		}
	}

	private void handleCurrentProcess() {
		PCB process = this.currentActiveProcess;
		process.setPState(ProcessState.RUNNING);
		Burst currentBurst = process.getCurrentBurst();
		process.incrementCPUCounter();

		while (currentBurst.getRemainingtime() > 0) {

			currentBurst.decRemainingtime();
			process.incrementCPUTotalTime();
			CPU.busyTime++;
			synchronized (Clock.class) {
				Clock.incrementTime();
			}
			PCB tmp = RAM.getreadyQueue().poll();

			if (tmp != null && tmp.getCurrentBurst().getRemainingtime() < currentBurst.getRemainingtime()) {
				process.incPreemptionCounter();
				process.letProcessReady();
				process = tmp;
				currentBurst = process.getCurrentBurst();
				currentActiveProcess = process;
				process.setPState(ProcessState.RUNNING);
			} else {
				if (tmp != null)
					RAM.addToReadyQueue(tmp);

			}

		}
		Burst nextBurst = process.nextBurst();

		if (nextBurst == null) {
			process.terminateProcess();
			System.out.println(process.getPID());
			return;
		}

		Burst cpuBurst = currentBurst;
		int memoryValue = cpuBurst.getMemory();

		if (memoryValue != 0)
			RAM.handleMemoryValue(process, memoryValue);
		if (process.getPState().equals(ProcessState.RUNNING) && nextBurst.getBurst_type().equals(burstType.IOBurst)) {
			// Handle IO Burst
			process.setPState(ProcessState.WAITING);
			ioDevice.addProcessToDevice(this.currentActiveProcess);
		}
		try {
			sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static int getBusyTime() {
		return CPU.busyTime;
	}

	static int getIdleTime() {
		return CPU.idleTime;
	}
}
