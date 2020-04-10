public class CPU {
    private Clock time;
    private int size;

    public CPU(){
        time = new Clock();
        size = 0;
    }

    public void executionCycle(RAM ram){
        PCB pcb = ram.serveProcess();
        int cpuTime = pcb.getCPUTime();

        // missing method
        pcb.letProcessRunning();




        /*

        At each millisecond:
        check if cpu burst has ended
        check if the io burst has ended
        or a new process enters the ready queue and its time < current process time (interrupt)
        check if WAITING process can be put in ready queue

        while (time.getTime() % 200 == 0){


            time.incTime();
        }
        */
        
    }
    
}