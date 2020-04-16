public class CPU{
    private Clock time;
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
        ram.loadReadyQueue();
        
        //sleep for 100ms
        for (int i=0; i<100;i++){
            Clock.incTime();
        }
        PCB process = ram.serveProcess();
        int burst_time = process.getbursts().peek().getRemainingTime();
        //Start execution
        while (true){
            if (Clock.getTime()-100 % 200 == 0){
                ram.loadReadyQueue(process.getProgSize());
            }
            // while (true){
                // Clock.incTime();            
            // loop
            PCB tmp = ram.serveProcess();
            // check process not null
            
            if(process.getbursts().peek().getRemainingTime() == 0){
                process.getbursts().pop();
                if(process.getbursts().isEmpty()){
                process.terminateprocess();
                // return process to print
                ram.decMemorySize(process);
                }
                else{
                    IODevice.addProcess(process);
                }
                
            }

            if(tmp==null){
                Clock.incTime();
                continue;
            }
            
            
            if (tmp.getbursts().peek().getRemainingTime()<burst_time){ //in case of a preemption
                process.letProcessReady();
                ram.addProcess(process);
                // iodev.IORequest(process);
                process = tmp;
                burst_time = process.getbursts().peek().getRemainingTime();
            } else {
                ram.enqueue(tmp);
            }
            executionCycle(process);
        }
            
            
            
        // }
    }
    public void executionCycle(PCB p){
        if (p.getPStates != PStates.RUNNING)
            p.letProcessRunning();
        p.getbursts().peek().decRemainingTime();
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