
public class RAM {
    // priority => burst_time (int)
    // data => PCB data
    
    PriorityQueue jobsQueue;
    PriorityQueue readyQueue; 
    final static int maxRAM = 680;
    private static int currentRamUsed;

    RAM(){
        readyQueue = new PriorityQueue();
        jobsQueue = FileHandler.readFile(); 
        currentRamUsed = 0;
    }

    public boolean canAdd(int size){
        return currentRamUsed + size <= maxRAM*0.85;
    }
    
    public int getRamUsed(){
        return currentRamUsed;
    }


    public boolean addProcess(PCB process){
        if (canAdd(process.getProgSize())){
            readyQueue.enqueue(process, process.getBursts().peek().getCpuBurst());
            currentRamUsed += process.getProgSize();
            return true;
        } else {
            return false;
        }
    }

    public void decMemorySize(PCB p){
        currentRamUsed -= process.getMemoryUsed;
    }

    public PCB serveProcess(){
        if (currentRamUsed != 0){
            PCB process = readyQueue.serve().data;
            // currentRamUsed -= process.getProgSize();

            return process;
        } else {
            return null;
        }
    }

    public void loadReadyQueue(int currProcSize){
        int extra_size = 0;
        if (currProcSize>0){
            extra_size = currProcSize;
        }
        while(!jobsQueue.isEmpty()){
            PQNode process = jobsQueue.serve();
            if (canAdd(process.data.getProgSize() + extra_size)){
                process.setLoadedTime(Clock.getTime());
                readyQueue.enqueue(process.data, process.priority);
                // currentRamUsed
                Clock.incTime();
            } else {
                // maybe set state to waiting
                process.data.incMemoryCounter();
                jobsQueue.enqueue(process.data, process.priority);
                break;
            }
            
        }
    }
    
}
