
public class RAM {
    // priority => burst_time (int)
    // data => PCB data
    
    PriorityQueue readyQueue; 
    final static int maxRAM = 680;
    private static int currentRamUsed;

    RAM(){
        readyQueue = new PriorityQueue(); 
        currentRamUsed = 0;
    }

    public boolean canAdd(int size){
        return currentRamUsed + size <= maxRAM*0.85;
    }
    
    public int getRamUsed(){
        return currentRamUsed;
    }


    public boolean addProcess(PCB process){
        if (canAdd(process.getPrgSize())){
            readyQueue.enqueue(process, process.getCPUTime());
            currentRamUsed += process.getPrgSize();
            return true;
        } else {
            // can we add waiting queue ?
            return false;
        }
    }

    public PCB serveProcess(){
        if (currentRamUsed != 0){
            PCB process = readyQueue.serve().data;
            currentRamUsed -= process.getPrgSize();

            return process;
        } else {
            return null;
        }
    }
    
}
