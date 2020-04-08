public class PQNode<T,P>{

    T data;
    P priority;

    PQNode<T,P> next;

    PQNode() {
        data=null;
        priority=null;
    }

    PQNode(T data, P priority){
        this.data = data;
        this.priority = priority;
        next = null;
    }

}