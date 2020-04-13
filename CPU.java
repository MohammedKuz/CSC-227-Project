public class CPU {
    private Clock time;
    private RAM ram;

    public CPU(){
        time = new Clock();
        ram = new RAM();
    }

    public void executionCycle(){
        ram.loadReadyQueue();

        //sleep for 100ms
        for (int i=0; i<100;i++){
            Clock.incTime();
        }

        while (true){
            Clock.incTime();
            if (Clock.getTime()-100 % 200 == 0)){
                ram.loadReadyQueue();
            }
            

            
            
            
        }
    }
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