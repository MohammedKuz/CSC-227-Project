
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
            // can we add waiting queue ?
            return false;
        }
    }

    public PCB serveProcess(){
        if (currentRamUsed != 0){
            PCB process = readyQueue.serve().data;
            currentRamUsed -= process.getProgSize();

            return process;
        } else {
            return null;
        }
    }

    public void loadReadyQueue(){
        while(!jobsQueue.isEmpty()){
            PQNode process = jobsQueue.serve();
            if (canAdd(process.data.getProgSize())){
                readyQueue.enqueue(process.data, process.priority);
            } else {
                jobsQueue.enqueue(process.data, process.priority);
                break;
            }
            
        }
    }
    
}
