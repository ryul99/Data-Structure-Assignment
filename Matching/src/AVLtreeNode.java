public class AVLtreeNode<T> {
    private int balance;
    private int height;
    private AVLtreeNode<T> leftChilid;
    private AVLtreeNode<T> rightChild;
    private T item;

    public AVLtreeNode() {
    }

    public AVLtreeNode(T item, AVLtreeNode<T> leftChilid, AVLtreeNode<T> rightChild) {
        this.item = item;
        this.leftChilid = leftChilid;
        this.rightChild = rightChild;
        height = Math.max((leftChilid == null ? 0 : leftChilid.getHeight()), (rightChild == null ? 0 : rightChild.getHeight())) + 1;
        balance = (leftChilid == null ? 0 : leftChilid.getHeight()) - (rightChild == null ? 0 : rightChild.getHeight());
    }

    public void setItem(T item) {
        this.item = item;
    }

    public void setLeftChilid(AVLtreeNode<T> leftChilid) {
        this.leftChilid = leftChilid;
    }

    public void setRightChild(AVLtreeNode<T> rightChild) {
        this.rightChild = rightChild;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public T getItem() {
        return item;
    }

    public AVLtreeNode<T> getLeftChilid() {
        return leftChilid;
    }

    public AVLtreeNode<T> getRightChild() {
        return rightChild;
    }

    public int getBalance() {
        return balance;
    }

    public int getHeight() {
        return height;
    }
}
