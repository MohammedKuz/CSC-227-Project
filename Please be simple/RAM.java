import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class RAM extends Thread {
	private static Queue<PCB> readyQueue = new PriorityQueue<>(1000, new RemainingTimeComparator());
	private static Queue<PCB> waitingQueue = new PriorityQueue<>(1000, new ArrivalComparator());

	final static int RAM_SIZE = (int) (680 * 0.85); // 1GB - 320 for os , and %85
	final static int ADDITIONAL_RAM_SIZE = (int) (680 * 0.15);
	private static int totalRamUsage;

	private static IODevice device;
	private static Thread cpu;

	RAM(IODevice device) {
		RAM.totalRamUsage = 0;
		RAM.device = device;
		cpu = CPU.currentThread();
	}

	@Override
	public void run() {
		// While OS is running, keep handling IO requests if available
		while (true) {
			try {
				longTermScheduler();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Adding to Queues
	static boolean addToJobQueue(PCB obj) {
		return waitingQueue.add(obj);
	}

	public static boolean addToReadyQueue(PCB obj) {
		return readyQueue.add(obj);
	}

	// Add new process
	void longTermScheduler() throws InterruptedException {


		synchronized (device) {
			// Pause Device temporarly
			Thread.sleep(5);
		}

		// Check for deadlock
		if (isDeadlock()) {
			PCB maxProcess = device.getMaxProcess();
			device.killProcessFromIOQueue(maxProcess);
		}

		synchronized (device) {
			// Start the device again
			device.notify();
		}

		// Add waiting processes
		while (!waitingQueue.isEmpty() && isEnough(waitingQueue.peek().getPrSize())) {
			PCB process = waitingQueue.poll();
//			System.out.println(Clock.getCurrentTime()+" | "+process.getArrivalTime()+" | "+waitingQueue.size()+" | "+readyQueue.size());
			if (process.getArrivalTime() > Clock.getCurrentTime()) {
				synchronized (cpu) {
					cpu.notify();
				}
				waitingQueue.add(process);
				sleep(1);
				continue;
			} else if (process.getLoadedTime() == -1) {
				process.setLoadedTime(Clock.getCurrentTime());
			} else {
				// If not the first time, then increment its memoryCounter
				process.incrementMemoryCounter();
			}

			if (process.getCurrentBurst().getBurst_type().equals(burstType.CPUBurst)) {
				process.setPState(ProcessState.READY);
				readyQueue.add(process);
			} else {
				device.addProcessToDevice(process);
			}
			totalRamUsage += process.getPrSize();
//			sleep(1);
		}
		sleep(200);
	}

	synchronized static void subtractFromUsage(int size) {
		if (totalRamUsage - size < 0) {
			return;
		}
		totalRamUsage -= size;
	}

	synchronized static void handleMemoryValue(PCB process, int memoryValue) {
		int newSize = process.getPrSize() + memoryValue;
		if (newSize < 0) {
			process.killProcess();
			return;
		}
		// Check if enough RAM is available
		if (!addToRamUsage(memoryValue)) {
			process.letProcessWait();
		}
		process.setPrSize(newSize);
	}

	private synchronized static boolean addToRamUsage(int size) {
		if (totalRamUsage + size > RAM_SIZE + ADDITIONAL_RAM_SIZE) {
			return false;
		}
		totalRamUsage += size;
		return true;
	}

	private boolean isEnough(int size) {
		return totalRamUsage + size <= RAM_SIZE;
	}

	private boolean isDeadlock() {
		return !waitingQueue.isEmpty() && readyQueue.isEmpty() && !isEnough(waitingQueue.peek().getPrSize())
				&& !device.isEmpty();
	}

	// This method retrieve but not remove from Queue
	static PCB retrieve() {
		return readyQueue.peek();
	}

	public static Queue<PCB> getreadyQueue() {
		return readyQueue;
	}

	public static void setreadyQueue(PriorityQueue<PCB> readyQueue) {
		RAM.readyQueue = readyQueue;
	}

	static Queue<PCB> getWaitingQ() {
		return waitingQueue;
	}
}
