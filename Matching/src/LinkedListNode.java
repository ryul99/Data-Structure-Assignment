public class LinkedListNode<T> {
    private T item;
    private LinkedListNode<T> next;

    public LinkedListNode(){
        next = null;
        item = null;
    }

    public LinkedListNode(T item, LinkedListNode next){
        this.item = item;
        this.next = next;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public void setNext(LinkedListNode<T> next) {
        this.next = next;
    }

    public T getItem() {
        return item;
    }

    public LinkedListNode<T> getNext() {
        return next;
    }
}
