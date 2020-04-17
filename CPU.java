
public class CPU extends Thread {
    private Clock time; // this is never used I think it shouldn't be used cause time should be global
                        // not an object in cpu
    static RAM ram;
    static IODevice iodev;
    static PCB current_process;
    static int current_burst;
    private boolean isLongTerm;
    
    public CPU(boolean type) {
        time = new Clock();
        ram = new RAM();
        iodev = new IODevice();
        current_process = null;
        current_burst = 0;
        isLongTerm = type;

    }

    @Override
    public void run() {
        System.out.println("run");
        if (this.isLongTerm == true) {
            ram.loadReadyQueue();

            for (int i = 0; i < 100; i++) {
                Clock.incTime();
            }

            current_process = ram.serveProcess();
            System.out.println(current_process);
            current_burst = current_process.getCurrentBurstTime();

            while (!ram.jobsQueue.isEmpty()) {
                if (Clock.getTime() - 100 % 200 == 0) {
                    ram.loadReadyQueue();
                }
            }
        } else {
            this.executionCycle();
        }
    }

    public void executionCycle() {
        System.out.println("ec");
        while (true) {
            if (current_process != null) {
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

    public void init() {
        System.out.println("init");
        CPU long_term_scheduler = new CPU(true);
        CPU short_term_scheduler = new CPU(false);

        long_term_scheduler.start();
        short_term_scheduler.start();
    }

}