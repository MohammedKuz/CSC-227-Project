public class PriorityQueue<T,P> {
    private PQNode<T,P> head;
    private int size;

    public PriorityQueue(){
        head = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void enqueue(T data, P priority){
        PQNode<T,P> tmp = new PQNode<T,P>(data, priority);
        if (size==0 || priority < head.priority) {
            tmp.next = head;
            head = tmp;
            size++;
        } else {
            PQNode<T,P> current = head;
            PQNode<T,P> currentPrev;
            for(int i=0;i<size;i++){
                if(tmp.priority < current.priority){
                    currentPrev.next = tmp;
                    tmp.next = current;
                    size++
                    return;
                } else {
                    currentPrev = current;
                    current = current.next;
                }
            }
            currentPrev.next = tmp;
            size++

        }
    }

    // maybe <T,P>
    public PQNode serve(){
        PQNode<T,P> node = head;
        head = head.next;
        size--;
        return node;
    } 

}