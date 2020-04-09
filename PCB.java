//GL
public class PCB {

    
    private int PID; //Process ID
    private PStates PState; //Process State 
    private int PCounter; //Process Counter (not sure)
    private double PrSize; //Programe Size
    private double execTime; //Expected Excution Time
    
    //Counter attributes
    private int cpuCounter;
    private int ioCounter;
    private int memoryCounter;
    
    //Time attributes
    public int numOfInstructions;
    public int IOTime;
    public int CPUTime;
    public int currentIO;

    public boolean cpuBound;

    public PCB(int PID){
        this.PID = PID;
        this.setPState(PStates.WAITING);
        this.cpuBound = false;
        this.PrSize = Math.random() * (204800 - 5120 + 1) + 5120; //(not sure)
        this.cpuCounter = 0;
        this.ioCounter = 0;
        this.memoryCounter = 0;
        //Missing somethings
    }


    public void letProcessReady(){
        this.setPState(PStates.READY);
        //Add to RAM usage
            
    }

    public void  letProcessWait(){
        this.setPState(PStates.WAITING);
        //Subtract from RAM usage
        //Add to job Queue
        this.memoryCounter++;

    }

    public void killProcess(){
        this.setPState(PStates.KILLED);
        //Subtract from RAM usage
        
    }

    public void terminateProcess(){
        this.setPState(PStates.TERMINATED);
        //Stop all clocks

    }


    //Setters and Getters 
    public PStates getPState(){
        return this.PState;
    }

    public void setPState(PStates newState){
        this.PState = newState;
    }


    public int getPID() {
		return this.PID;
	}

	public void setPID(int PID) {
		this.PID= PID;
	}


	public double getPrgSize() {
		return this.PrSize;
	}

	public void setPrgSize(double PrSize) {
		if(PrSize >= 5120 && PrSize <= 204800)
			this.PrSize = PrSize;
		else 
			System.out.printf("%d is not within limit of 5120KB to 204800KB.\n", PrSize);
	}


	public double getExecTime() {
		return execTime;
	}

	public void setExecTime(double execTime) {
		//Not sure how to do it
	}
    
    
	public int getNumOfInstructions() {
		return this.numOfInstructions;
	}

}