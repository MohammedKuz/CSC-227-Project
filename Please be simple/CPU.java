import java.util.Queue;

public class CPU extends Thread {
	private IODevice ioDevice;
	private PCB currentActiveProcess;
	private static int busyTime;
	private static int idleTime;

	CPU(IODevice ioDevice) {
		this.ioDevice = ioDevice;

		this.currentActiveProcess = null;
		CPU.busyTime = 0;
		CPU.idleTime = 0;
	}

	@Override
	public void run() {
		while (true) {
			currentActiveProcess = RAM.getreadyQueue().poll();
			if (currentActiveProcess != null) {
				handleCurrentProcess();
				
			} else {
				try {
					synchronized (CPU.currentThread()) {
//						this.wait(1); // using this gives high utilization, maybe wrong ?
						Thread.sleep(1);
					}
//					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (Clock.class) {
					Clock.incrementTime();
				}
				System.out.println("Time incremented, " + Clock.getCurrentTime()+" | "+RAM.getreadyQueue().size()+" | "+RAM.getWaitingQ().size());
//				Queue<PCB> tmp = RAM.getWaitingQ();
//				for (PCB p : tmp) {
//					System.out.println(p);
//				}
				
				CPU.idleTime++;
			}
		}
	}

	private void handleCurrentProcess() {
		PCB process = this.currentActiveProcess;
//		System.out.println(process);
		process.setPState(ProcessState.RUNNING);
		Burst currentBurst = process.getCurrentBurst();
		process.incrementCPUCounter();

		while (currentBurst.getRemainingtime() > 0) {
			System.out.println(
					Clock.getCurrentTime()+" | Handling new process " + process.getPID() + " | " + process.getCurrentBurst().getRemainingtime()+" | RQ: "+RAM.getreadyQueue().size()+" | WQ: "+RAM.getWaitingQ().size());
			
			currentBurst.decRemainingtime();
			process.incrementCPUTotalTime();
			CPU.busyTime++;
			try {
				sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (Clock.class) {
				Clock.incrementTime();
			}
//            System.out.println("- - - -Time incremented, "+Clock.getCurrentTime());
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

	}

	static int getBusyTime() {
		return CPU.busyTime;
	}

	static int getIdleTime() {
		return CPU.idleTime;
	}
}
