
public class CPU extends Thread {
	static RAM ram;
	static IODevice iodev;
	static PCB current_process;
	static int count = 0;

	public CPU() {
		ram = new RAM();
		iodev = new IODevice();
		current_process = null;
	}

	Thread long_term_scheduler = new Thread() {
		public void run() {
			System.out.println("Long term scheduler started");
			ram.loadReadyQueue();
			
			for (int i = 1; i <= 100; i++) {
				Clock.incTime();
			}
			current_process = ram.serveProcess();
			current_process.letProcessReady();
			System.out.println("Served first process");
			while (!ram.jobsQueue.isEmpty() || !ram.readyQueue.isEmpty()) {
				if (Clock.getTime() -100  % 200 == 0) {
					ram.loadReadyQueue();
				}
			}
			System.out.println("Done loading jobs!");
		}
	};

	Thread short_term_scheduler = new Thread() {
		public void run() {
			System.out.println("Short term scheduler started");
			PCB tmp;
			while (true) {
				if (current_process!=null) { //get first process
					if ((tmp = ram.serveProcess())!=null) {
						if (!current_process.getBursts().isEmpty()) {
							if (tmp.getBursts().peek().getRemainingtime() < current_process.getBursts().peek().getRemainingtime()) { //check if next proc has less time than current proc
								if (tmp.getArrivalTime() < current_process.getArrivalTime()) { //check who should execute first
									System.out.println("Switching to PID: "+tmp.getPID());
									current_process.letProcessReady();
									ram.addProcess(current_process);
									current_process = tmp;
								} else {
									ram.addProcess(tmp);
								}
							} else {
								ram.addProcess(tmp);
							}
						} else { //no more bursts, next process is available
							current_process.terminateProcess();
							ram.decMemorySize(current_process);
							System.out.println("------------------------------------------------");
							System.out.println(current_process);
							current_process = tmp;
						}
						
					} else if (current_process==null){ //no more processes, wait for ready queue OR maybe deadlock
						Clock.incTime();
 						if (ram.readyQueue.isEmpty() && ram.jobsQueue.isEmpty() && current_process==null)
							break;
						else
							continue;
					}
					
					if (current_process.getArrivalTime()>=Clock.getTime()-100) { //if not waiting time yet
						Clock.incTime();
						continue;
					} else {
						current_process.setLoadedTime(Clock.getTime()); //time arrived
					}
					//check state
					if (!current_process.getPState().equals(PStates.RUNNING)) {
						current_process.letProcessRunning();
						current_process.incCPUCounter();
					}
					
					if(current_process.getBursts().isEmpty()) { //check to avoid null pointer
						current_process.terminateProcess(); //last process
						ram.decMemorySize(current_process);
						System.out.println("------------------------------------------------");
						System.out.println(current_process);
						break;
					}
					
					//do burst
					current_process.incCPUTime();
					current_process.getBursts().peek().decRemainingtime();
					Clock.incTime();
					
					if (current_process.getBursts().peek().getRemainingtime()==0)
						current_process.popBurst();
				}
			}
		}
	};

}