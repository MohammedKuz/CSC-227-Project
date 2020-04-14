public class PriorityQueue {
    private PQNode head;
    private int size;

    public PriorityQueue(){
        head = null;
        size = 0;
    }
    
    public int length() {
    	return size;
    }
    
    public boolean isEmpty(){
        return size==0;
    }

    public void enqueue(PCB data, int priority){
        PQNode tmp = new PQNode(data, priority);
        if (size==0 || priority < head.priority) {
            tmp.next = head;
            head = tmp;
            size++;
        } else {
            PQNode current = head;
            PQNode currentPrev = null;
            for(int i=0;i<size;i++){
                if(tmp.priority < current.priority){
                    currentPrev.next = tmp;
                    tmp.next = current;
                    size++;
                    return;
                } else {
                    currentPrev = current;
                    current = current.next;
                }
            }
            currentPrev.next = tmp;
            size++;
        }
    }

    // maybe <T,P>
    public PQNode serve(){
        PQNode node = head;
        head = head.next;
        size--;
        return node;
    } 

}