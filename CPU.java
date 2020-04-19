
public class CPU extends Thread {
	static PCB current_process;
	static int count = 0;
	private static int idleTime;
	private static int busyTime;


	public CPU() {
		current_process = null;
		idleTime=0;
		busyTime=0;
	}

	Thread long_term_scheduler = new Thread() {
		public void run() {
			while(true) {
				current_process = RAM.readyQueue.serve().data;
				if(current_process != null) {
					handleCurrentProcess();
				} else {
					try {
						sleep(1);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}

					//This will increment the Clock by 1 millisecond
					Clock.incTime();

					// Increment CPU idle time
					CPU.idleTime++;
				}
			}


			public static void
			System.out.println("Long term scheduler started");
			RAM.loadReadyQueue();
			
			for (int i = 1; i <= 100; i++) {
				Clock.incTime();
			}
			current_process = RAM.serveProcess();
			current_process.letProcessReady();
			System.out.println("Served first process");
			while (!RAM.jobsQueue.isEmpty() || !RAM.readyQueue.isEmpty()) {
				if (Clock.getTime() -100  % 200 == 0) {
					RAM.loadReadyQueue();
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
					if ((tmp = RAM.serveProcess())!=null) {
						System.out.println(Clock.getTime());
						if (!current_process.getBursts().isEmpty()) {
							if (tmp.getBursts().peek().getRemainingtime() < current_process.getBursts().peek().getRemainingtime()) { //check if next proc has less time than current proc
								if (tmp.getArrivalTime() <= Clock.getTime()) { //check who should execute first
									System.out.println("Switching to PID: "+tmp.getPID());
									current_process.letProcessReady();
									RAM.addProcess(current_process);
									current_process = tmp;
								} else {
									RAM.addProcess(tmp);
								}
							} else {
								RAM.addProcess(tmp);
							}
						} else { //no more bursts, next process is available
							current_process.terminateProcess();
							RAM.decMemorySize(current_process);
							System.out.println("------------------------------------------------");
							System.out.println(current_process);
							current_process = tmp;
						}
						
					} else if (current_process==null){ //no more processes, wait for ready queue OR maybe deadlock
						Clock.incTime();
 						if (RAM.readyQueue.isEmpty() && RAM.jobsQueue.isEmpty() && current_process==null)
							break;
						else
							continue;
					}
					
					if (current_process.getArrivalTime()>=Clock.getTime()-100) { //if not waiting time yet
						Clock.incTime();
						continue;
					} else if (current_process.getLoadedTime() <= 0){
						current_process.setLoadedTime(Clock.getTime()); //time arrived
					}
					//check state
					if (!current_process.getPState().equals(PStates.RUNNING)) {
						current_process.letProcessRunning();
					}
					
					if(current_process.getBursts().isEmpty()) { //check to avoid null pointer
						current_process.terminateProcess(); //last process
						RAM.decMemorySize(current_process);
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

	private void handleCurrentProcess() {

	}

}