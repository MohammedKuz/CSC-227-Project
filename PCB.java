//GL
public class PCB {

    
    private int PID; //Process ID
    private String PState; //Process State 
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
        this.PState = "new";
        this.cpuBound = false;
        this.PrSize = Math.random() * (204800 - 5120 + 1) + 5120; //(not sure)
        this.cpuCounter = 0;
        this.ioCounter = 0;
        this.memoryCounter = 0;
        //Missing somethings
    }

    public void killProcess(){
        
        
        this.setPState("KILLED");
        //Subtract from RAM usage
        
        
    }

    public void terminateProcess(){
        this.PState = "TERMINATED";
        //Stop all clocks

    }

    public void  letProcessWait(){
        this.setPState("WAITING");
        //Subtract from RAM usage
        //Add to job Queue
        this.memoryCounter++;
        
    }
    
    public void letProcessReady(){
        this.setPState("READY");
        //Add to RAM usage
        
    }

    

    //Setters and Getters 
    public String getPState(){
        return this.PState;
    }

    public void setPState(String PState){
        this.PState = PState;
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