import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
//This class need modifications
public class IODevice {

    private static PCB currentProcess;
    private static Queue<PCB> waitingIOList;

    public IODevice() {
        currentProcess = null;
        waitingIOList = new ConcurrentLinkedQueue<>();
    }
    
    
    // Methods
    public void run(){
        while(true){
            currentProcess = waitingIOList.poll();

            if(currentProcess != null)
                handleIOR();
            else{
                //Sleep
            }
        }
    }

    private void handleIOR() {
        currentProcess.incIOCounter();
        
    }

    public void IORequest(PCB process) {
        waitingIOList.add(process);
    }

    public void killProcess(PCB process){
        if(waitingIOList.remove(process))
            process.killProcess();
    }

    public boolean isEmpty(){
        return waitingIOList.isEmpty();
    }

    public PCB getProcess(){
        return currentProcess;
    }
}