public class LinkedList<T> implements Comparable<LinkedList<T>> {
    private LinkedListNode<String> dummyHead;//store key substring
    private int numberOfItem;

    public LinkedList() {
        dummyHead = new LinkedListNode<>();
        numberOfItem = 0;
    }

    public void insert(int i, int j) {
        LinkedListNode curr = dummyHead;
        IndexNode ele = new IndexNode(i, j);
        LinkedListNode<IndexNode> item = new LinkedListNode<>(ele, null);
        while (curr.getNext() != null)
            curr = curr.getNext();
        curr.setNext(item);
        numberOfItem++;
    }

    public boolean isEmpty() {
        return numberOfItem == 0;
    }

    public LinkedListNode<String> getDummyHead() {
        return dummyHead;
    }

    public void setDummyHead(String dummyHeadItem) {
        this.dummyHead.setItem(dummyHeadItem);
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    @Override
    public int compareTo(LinkedList<T> o) {
        return this.dummyHead.getItem().compareTo(o.getDummyHead().getItem());
    }

    @Override
    public String toString() {
        return dummyHead.getItem();
    }
}
