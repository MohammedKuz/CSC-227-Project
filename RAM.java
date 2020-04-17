
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

    // add a process to ReadyQ
    public boolean addProcess(PCB process){
        if (canAdd(process.getCurrentMemory())){
            readyQueue.enqueue(process, process.getCurrentBurstTime());
            currentRamUsed += process.getCurrentMemory();
            return true;
        } else {
            return false;
        }
    }

    public void decMemorySize(PCB p){
        currentRamUsed -= p.getMemoryUsed();
    }

    public PCB serveProcess(){
        
        if (currentRamUsed != 0){
        
            PCB process = readyQueue.serve().data;
            // currentRamUsed -= process.getCurrentMemory();

            return process;
        } else {
            return null;
        }
    }

    public void loadReadyQueue(){
        while(!jobsQueue.isEmpty()){
            PQNode process = jobsQueue.serve();
            if (canAdd(process.data.getCurrentMemory())){
                process.data.setLoadedTime(Clock.getTime());
                readyQueue.enqueue(process.data, process.priority);
                currentRamUsed += process.data.getCurrentMemory();
            } else {
                // maybe set state to waiting
                process.data.incMemoryCounter();
                jobsQueue.enqueue(process.data, process.priority);
                break;
            }
            
        }
    }
    
}
