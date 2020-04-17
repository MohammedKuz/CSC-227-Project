
public class CPU extends Thread {
	static RAM ram;
	static IODevice iodev;
	static PCB current_process;
	static int current_burst;
	static boolean flag;

	public CPU() {
		ram = new RAM();
		iodev = new IODevice();
		flag = true;
	}

	Thread long_term_scheduler = new Thread() {
		public void run() {
			ram.loadReadyQueue();
	            for (int i = 0; i < 100; i++) {
	            	Clock.incTime();
	            }

			current_process = ram.serveProcess();
			System.out.println("Served process");

			while (!ram.jobsQueue.isEmpty()) {
//				System.out.println(Clock.getTime()+" time");
				if (Clock.getTime()-100 % 200 == 0) {
					ram.loadReadyQueue();
				}
//				Clock.incTime();
				
			}
			flag = false;
		}
	};

	Thread short_term_scheduler = new Thread() {
		public void run() {
			System.out.println("Short term started");

			while (true) {
				if (current_process != null) {
						PCB tmp = ram.serveProcess();
						if (tmp!=null) { //theres more in ready queue
							if (!current_process.getBursts().isEmpty()) { //theres more bursts
								if (tmp.getCurrentBurstTime() < current_process.getCurrentBurstTime()) { //tmp burst time less than current time
									if (tmp.getArrivalTime() <= current_process.getArrivalTime()) { // tmp is before current
										ram.addProcess(current_process);
										current_process = tmp;
									} else {
										ram.addProcess(tmp);
									}
								} else {
								}
							} else {
								current_process = tmp;
								
							}
							
							
						} else {
							Clock.incTime();
						}	

	
						// when cpuBurst is done
						if (current_process.getCurrentBurstTime() == 0) {
							System.out.println("pop burst");
							if (!current_process.getBursts().isEmpty()){
								current_process.getBursts().pop();
								System.out.println(current_process.getCurrentBurstTime());
							} else {
								break;
							}
						}
						if (current_process.getBursts().isEmpty()) {
							System.out.println("Terminated");
							current_process.terminateProcess();
							// return process to print
							System.out.println(current_process);
							ram.decMemorySize(current_process);
						} else {
//							iodev.IORequest(current_process);
						}
	
						if ((current_process.getPState() == PStates.READY) || (current_process.getPState() == PStates.WAITING)) {
							System.out.println(current_process.getPState());
							System.out.println(current_process.getPState() != PStates.RUNNING);
							System.out.println("Set to running "+current_process.getPID());
							current_process.letProcessRunning();
//							System.out.println(current_process.getPState());
						} else if (current_process.getPState() != PStates.RUNNING) {
							continue;
						}
						current_process.getBursts().peek().decRemainingtime();
						current_process.incCPUTime();
						Clock.incTime();
						System.out.println(Clock.getTime());
//					} else {
////						io request
//					}
				}
			}
		}
	};

}