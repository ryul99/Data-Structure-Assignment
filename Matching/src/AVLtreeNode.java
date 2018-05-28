public class AVLtreeNode<T> {
//    private int balance;
    private int height;
    private AVLtreeNode<T> leftChild;
    private AVLtreeNode<T> rightChild;
    private T item;

    public AVLtreeNode() {
        height = 0;
        leftChild = null;
        rightChild = null;
    }

    public AVLtreeNode(T item, AVLtreeNode<T> leftChild, AVLtreeNode<T> rightChild) {
        this.item = item;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.height = Math.max((leftChild == null ? 0 : leftChild.getHeight()), (rightChild == null ? 0 : rightChild.getHeight())) + 1;
//        balance = (leftChild == null ? 0 : leftChild.getHeight()) - (rightChild == null ? 0 : rightChild.getHeight());
    }

    public void setItem(T item) {
        this.item = item;
    }

    public void setLeftChild(AVLtreeNode<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(AVLtreeNode<T> rightChild) {
        this.rightChild = rightChild;
    }

//    public void setBalance(int balance) {
//        this.balance = balance;
//    }

    public void setHeight(int height) {
        this.height = height;
    }

    public T getItem() {
        return item;
    }

    public AVLtreeNode<T> getLeftChild() {
        return leftChild;
    }

    public AVLtreeNode<T> getRightChild() {
        return rightChild;
    }

    public int getBalance() {
        return ((leftChild == null ? 0 : leftChild.getHeight()) - (rightChild == null ? 0 : rightChild.getHeight()));
    }

    public int getHeight() {
        return height;
    }
}
