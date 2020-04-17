
public class CPU extends Thread {
    static Clock time = new Clock();
    static RAM ram;
    static IODevice iodev;
    static PCB current_process;
    static int current_burst;
    
    public CPU() {
        ram = new RAM();
        iodev = new IODevice();
        
        

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
	                if (Clock.getTime() - 100 % 200 == 0) {
	                    ram.loadReadyQueue();
	                }
	            }

	    }
    };
    
    Thread short_term_scheduler = new Thread() {
	    public void run() {
	        System.out.println("Short term started");
	        
	        while (true) {    	
	            if (current_process != null) {
	            	System.out.println("Received process");
	                PCB tmp = ram.serveProcess();
	                if (tmp.getCurrentBurstTime() < current_burst) {
	                    current_process.letProcessReady();
	                    current_process.incPreemtionCounter();
	                    ram.addProcess(current_process);
	                    current_process = tmp;
	                    current_burst = current_process.getCurrentBurstTime();
	                } else {
	                    ram.addProcess(tmp);
	                }
	        
	                // when cpuBurst is done
	                if (current_process.getCurrentBurstTime() == 0) {
	                    current_process.getBursts().pop();
	                    if (current_process.getBursts().isEmpty()) {
	                        current_process.terminateProcess();
	                        // return process to print
	                        System.out.println(current_process);
	                        ram.decMemorySize(current_process);
	                    } else {
	                        iodev.IORequest(current_process);
	                    }
	                }
	
	                if (current_process.getPState() != PStates.RUNNING) {
	                    current_process.letProcessRunning();
	                    current_process.incCPUCounter();
	                }
	                 current_process.getBursts().peek().decRemainingtime();
	                current_process.incCPUTime();
	                Clock.incTime();
	            }
	        }
	    }
    };
    

}