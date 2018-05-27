public class LinkedList<T extends Comparable<T>> implements Comparable<LinkedList<T>> {
    private LinkedListNode<T> dummyHead;//store key substring
    private LinkedListNode<T> tail;
    private int numberOfItem;

    public LinkedList() {
        dummyHead = new LinkedListNode<>();
        tail = dummyHead;
        numberOfItem = 0;
    }

    public void insert(T input) {
        LinkedListNode<T> in = new LinkedListNode<T>(input, null);
        tail.setNext(in);
        tail = in;
        numberOfItem++;
    }

    public boolean isEmpty() {
        return numberOfItem == 0;
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    @Override
    public int compareTo(LinkedList<T> o) {
        return dummyHead.getNext().getItem().compareTo(o.dummyHead.getNext().getItem());
    }

//    @Override
//    public String toString() {
//        return dummyHead.getItem();
//    }

    public T get(int idx) {
        LinkedListNode<T> re = dummyHead.getNext();
        for(int i = 0; i < idx ; i++)
            re = re.getNext();
        return re.getItem();
    }
}
