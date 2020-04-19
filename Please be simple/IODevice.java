import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class IODevice extends Thread {

    private static PCB currentProcess;
    private static Queue<PCB> waitingList; //PCB in waiting state

    public IODevice() {
        currentProcess = null;
        waitingList = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        //While OS is running, keep handling IO requests if available
        while(true) {
        	currentProcess = waitingList.poll();

            if(currentProcess != null) {
                handleIORequest();
            } else {
                //sleep for x millisecond
                try {
                    sleep(1);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void handleIORequest() {
        currentProcess.incrementIOCounter();
        while(currentProcess.getCurrentBurst().getRemainingtime() > 0) {
            currentProcess.incrementIOTime();
            currentProcess.getCurrentBurst().decRemainingtime();
            try {
//                Wait for x millisecond
                sleep(1);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(currentProcess.nextBurst() == null) {
            currentProcess.terminateProcess();
            return;
        }
        currentProcess.letProcessReady();
    }

    public void addProcessToDevice(PCB process) {
    	waitingList.add(process);
    }

    void killProcessFromIOQueue(PCB process) {
        if(waitingList.remove(process)) {
            process.killProcess();
        }
    }
    //used for deadlock
    PCB getMaxProcess() {
        Object[] list = waitingList.toArray();
        PCB maxPCB = (PCB) list[0];
        for(int i = 1; i < list.length; i++) {
            PCB currentPCB = (PCB) list[i];
            if(currentPCB.getPrSize() > maxPCB.getPrSize())
                maxPCB = currentPCB;
        }

        return maxPCB;
    }

    boolean isEmpty() { return waitingList.isEmpty(); }
    PCB getCurrentProcess() { return currentProcess; }
    Queue<PCB> getWaitingList() { return waitingList; }

}