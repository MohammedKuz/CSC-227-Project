public class PQNode{

    PCB data;
    int priority;

    PQNode next;

    PQNode() {
        data=null;
        priority=0;
    }

    PQNode(PCB data, int priority){
        this.data = data;
        this.priority = priority;
        next = null;
    }

}