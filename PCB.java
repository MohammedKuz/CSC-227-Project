//GL
public class PCB {

    //test

    private int PID; //Process ID
    private String PState; //Process State 
    private int PCounter; //Process Counter (not sure)
    private double PrSize; //Programe Size
    private double execTime; //Expected Excution Time
    
    //Counter attributes
    private int cpuCounter;
    private int ioCounter;
    private int cpuCounter;
    
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
        //Missing somethings
    }

    public void killProcess(){

    }

    public void terminateProcess(){

    }

    public void  letProcessWait(){

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