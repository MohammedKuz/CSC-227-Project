
public class CPU extends Thread{
    private Clock time; //this is never used I think it shouldn't be used cause time should be global not an object in cpu
    private RAM ram;
    private IODevice iodev;


    public CPU(){
        time = new Clock();
        ram = new RAM();
        iodev = new IODevice();
    }
    
    @Override
    public void run() {
        //Execution cycle
        ram.loadReadyQueue(); //this method needs int parameter
        
        //sleep for 100ms
        for (int i=0; i<100;i++){
            Clock.incTime();
        }
        PCB process = ram.serveProcess();
        int burst_time = process.getCurrentBurstTime();
        //Start execution
        while (true){
            if (Clock.getTime()-100 % 200 == 0){
                ram.loadReadyQueue(process.getCurrentMemory());
            }
            // while (true){
                // Clock.incTime();            
            // loop
            PCB tmp = ram.serveProcess();
            // check process not null
            


            if(tmp==null){
                Clock.incTime();
                continue;
            }

            //in case of a preemption
            if (tmp.getCurrentBurstTime()<burst_time){
                process.letProcessReady();
                process.incPreemtionCounter();
                ram.addProcess(process);
                process = tmp;
                burst_time = process.getCurrentBurstTime();
            } else {
                ram.addProcess(tmp);
            }

            //when cpuBurst is done
            if(process.getCurrentBurstTime() == 0){
                process.getBursts().pop();
                if(process.getBursts().isEmpty()){
                    process.terminateProcess();
                    // return process to print
                    ram.decMemorySize(process);
                }
                else{
                    iodev.IORequest(process);
                }
            }

            executionCycle(process);
        }
            
            
            
        // }
    }
    public void executionCycle(PCB p){
        if (p.getPState() != PStates.RUNNING) {
            p.letProcessRunning();
            p.incCPUCounter();
        }
        p.getBursts().peek().decRemainingtime();
        p.incCPUTime();
        Clock.incTime();
    }
        
        // }

        
            // finish io burst
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
           
            // process.letProcessRunning();
            // if(process.getCPUTime() != 0){
            //     process.incCPUCounter();
            // }else{
            //     process.incIOCounter();
            // }

            
            
        // }
    // }
        // missing method
//        pcb.letProcessRunning();




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