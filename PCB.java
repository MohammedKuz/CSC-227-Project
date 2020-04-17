import java.util.LinkedList;

public class PCB {

    private int PID; //Process ID
    private int arrivalTime; //Arrival time for process
    private PStates PState; //Process state 
    private LinkedList<Burst> bursts; //Linked list of bursts
    private int memoryUsed; //total memory used 

    //Counter attributes
    private int CPUCounter; //Number of time it was in the CPU (RUNNING state)
    private int IOCounter; //Number of times it preformed an IO (WAITING state)
    private int memoryCounter; //Number of time it was waiting for memory
    private int preemptionCounter; //Number of times it's preempted 

    //Time attributes (All in MS)
    private int loadedTime; //When it was loaded into the ready queue
    private int CPUTime; //Total time spent in the CPU
    private int IOTime; //Total time spent in preforming IO
    private int finishTime; //Time it TERMINATED or KILLED


    public PCB(int PID, int arrTime){
        this.PID = PID;
        this.arrivalTime = arrTime;
        this.setPState(PStates.WAITING);
        this.bursts = new LinkedList<Burst>();
        this.memoryUsed = 0;

        this.CPUCounter = 0;
        this.IOCounter = 0;
        this.memoryCounter = 0;
        this.preemptionCounter = 0;

        this.loadedTime = -1;
        this.CPUTime = 0;
        this.IOTime = 0;
        this.finishTime = 0;
    }

    //Methods
    public void addBurst(Burst b) {
        bursts.add(b);
        memoryUsed += b.getMemory();
    }
    
    public void addBurst(Bursttype burst_type, int memory, int remainingtime) {
        memoryUsed += memory;
    	bursts.add(new Burst(burst_type, memory, remainingtime));
    }
    
    public LinkedList<Burst> getBursts() {
    	return bursts;
    }
    
    public Burst popBurst() {
    	return bursts.pop();
    }
    
    public void letProcessReady(){
        this.setPState(PStates.READY);
    }

    public void  letProcessWaitforio(){
        this.setPState(PStates.WAITING);
        this.incIOCounter();
    }

    public void  letProcessWaitformemory(){
        this.setPState(PStates.WAITING);
        this.incMemoryCounter();
    }

    public void killProcess(){
        this.setPState(PStates.KILLED);
        this.CPUTime = 
        finishTime = Clock.getTime();
    }

    public void terminateProcess(){
        this.setPState(PStates.TERMINATED);


        finishTime = Clock.getTime();
    }

    //Incrementations
    public void incCPUCounter(){ CPUCounter++; }
    public void incIOCounter(){ IOCounter++; }
    public void incMemoryCounter(){ memoryCounter++; }
    public void incPreemtionCounter(){ preemptionCounter++; }
    public void incCPUTime(){ CPUCounter++; }
    public void incIOTime(){ IOTime++; }

    //Setters and Getters 
    public int getPID(){  return PID;  }

    public void setPState(PStates PState){
        this.PState = PState;
    }

    public PStates getPState(){  return this.PState;  }

    public int getMemoryUsed() {  return memoryUsed;  }

    public int getCurrentMemory() { return bursts.peek().getMemory(); }
    
    public int getCPUCounter(){  return CPUCounter;  }

    public int getIOCounter(){  return IOCounter;  }

    public int getMemoryCounter(){  return memoryCounter;  }

    public int getPreemptionCounter(){  return preemptionCounter;  }

    public int getCurrentBurstTime(){ return this.getBursts().peek().getRemainingtime(); }
    
    public void setLoadedTime(int time){ this.loadedTime = time; }
    
    public int getLoadedTime(){  return loadedTime;  }

    public int getCPUTime(){  return CPUTime;  }

    public int getIOTime(){  return IOTime;  }

    public int getFinishTime(){  return finishTime;  }

	public void letProcessRunning() {
        this.setPState(PStates.RUNNING);
        this.incCPUCounter();
	}


    public int getArrivalTime() { return arrivalTime; }
    
    @Override
    public String toString() {
        return ("Process ID: " +this.PID
        + "\nProgram name: " +this.PID
        + "\nWhen it was loaded into the ready queue: " +this.loadedTime
        + "\nNumber of times it was in the CPU: " +this.CPUCounter  
        + "\nTotal time spent in the CPU: " +this.CPUTime
        + "\nNumber of times it performed an IO: " +this.IOCounter
        + "\nTotal time spent in performing IO: "+this.IOTime
        + "\nNumber of times it was waiting for memory: "+this.memoryCounter  
        + "\nNumber of times its preempted (stopped execution because another process replaced it): " +this.preemptionCounter
        + "\nTime it terminated or was killed: "+ (this.CPUTime - this.arrivalTime)
        + "\nIts final state: Killed or Terminated: "+this.PState
        + "\nCPU Utilization: N/A");
    }
}