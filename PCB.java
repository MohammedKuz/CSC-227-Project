import java.util.LinkedList;

public class PCB {

    private int PID; //Process ID
    private int arrivalTime; //Arrival time for process
    private PStates PState; //Process state 
    private LinkedList<Burst> bursts; //Linked list of bursts
    
    //Counter attributes
    private int CPUCounter; //Number of time it was in the CPU (RUNNING state)
    private int IOCounter; //Number of times it preformed an IO (WAITING state)
    private int memoryCounter; //Number of time it was waiting for memory
    private int preemptionCounter; //Nubmber of times its preempted
    
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
    }
    
    public void addBurst(Bursttype burst_type, int memory, int remainingtime) {
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

    public void letProcessRunning() {
		this.setPState(PStates.RUNNING);
	}

    public void  letProcessWait(){
        this.setPState(PStates.WAITING);

        this.memoryCounter++;
    }

    public void killProcess(){
        this.setPState(PStates.KILLED);
        
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

    public int getProgSize() {
    	int progSize = 0;
    	for (Burst b : bursts) {
    		progSize+=b.getMemory();
    	}
    	return progSize;
    }
    
    public int getCPUCounter(){  return CPUCounter;  }

    public int getIOCounter(){  return IOCounter;  }

    public int getMemoryCounter(){  return memoryCounter;  }

    public int getPreemptionCounter(){  return preemptionCounter;  }

    public int getLoadedTime(){  return loadedTime;  }

    public int getCPUTime(){  return CPUTime;  }

    public int getIOTime(){  return IOTime;  }

    public int getFinishTime(){  return finishTime;  }

	public int getArrivalTime() { return arrivalTime; }
}