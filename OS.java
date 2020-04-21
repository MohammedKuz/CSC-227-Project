import java.util.LinkedList;

public class OS {
	private static LinkedList<PCB> finishedProcesses = new LinkedList<>();
	private static int size = 0;
	private static IODevice io;
	private static RAM ram;
	private static CPU cpu;

	public static void main(String[] args) {
		io = new IODevice();
		ram = new RAM(io);
		cpu = new CPU(io);

		//FileHandler.genRandomFile(1000);
		for (PCB p : FileHandler.readFile()) {
			size++;
			RAM.addToJobQueue(p);
		}
		cpu.start();
		ram.start();
		io.start();
		
		System.out.println("Processing");
	}

	static void stopOS() {
		System.out.println("stopping");

		FileHandler.writeFile(finishedProcesses);

		for (PCB p : finishedProcesses) {
			System.out.println(p + "\n----------------------");
		}
		System.out.println("Finished all the processes!");
		double busyTime = (double) CPU.getBusyTime();
		double idleTime = (double) CPU.getIdleTime();

		System.out.println("CPU Utilization %" + Math.round((busyTime / (idleTime + busyTime)) * 100));
		System.exit(0);
	}

	static void addFinishedProcess(PCB process) {
		finishedProcesses.add(process);
	}

	static boolean isFullyFinished() {
		return OS.size == OS.finishedProcesses.size();
	}
}
