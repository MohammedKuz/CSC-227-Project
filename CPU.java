
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
			current_burst = current_process.getCurrentBurstTime();

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
//					if (current_process.getBursts().peek().getBurst_type().equals(Bursttype.cpuBurst)) {
//						System.out.println("Received process: "+current_process.getCurrentBurstTime());
						PCB tmp = ram.serveProcess();
						if ((tmp!=null) && (tmp.getCurrentBurstTime() < current_burst)) {
							current_process.letProcessReady();
							current_process.incPreemtionCounter();
							ram.addProcess(current_process);
							current_process = tmp;
							current_burst = current_process.getCurrentBurstTime();
						} else {
							if (tmp!=null)
							ram.addProcess(tmp);
						}
	
						// when cpuBurst is done
						if (current_process.getCurrentBurstTime() == 0) {
							System.out.println("pop burst");
							if (!current_process.getBursts().isEmpty()){
								current_process.getBursts().pop();
	//							System.out.println(current_process.getCurrentBurstTime());
								System.out.println(current_process.getPState());
							}
							continue;
							
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
						}
						current_process.getBursts().peek().decRemainingtime();
						current_process.incCPUTime();
						Clock.incTime();
//						System.out.println(Clock.getTime());
//					} else {
////						io request
//					}
				}
			}
		}
	};

}