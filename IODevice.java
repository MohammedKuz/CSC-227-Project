import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
//This class need modifications
public class IODevice extends Thread {

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

        while (currentProcess.getBursts().peek().getRemainingtime() > 0) {
            currentProcess.incIOTime();
            currentProcess.getBursts().peek().decRemainingtime();
            try {
                //Wait for x millisecond
                sleep(1);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
            currentProcess.getBursts().removeFirst();
            if(currentProcess.getBursts().isEmpty())
                currentProcess.terminateProcess();
            else
            currentProcess.letProcessReady();
            RAM.addProcess(currentProcess);
    }

    public static void IORequest(PCB process) {
        waitingIOList.add(process);
    }

    // This is will be used in a case of a deadlock
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
