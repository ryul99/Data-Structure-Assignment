import java.util.ArrayList;
import java.util.function.Function;

public class AVLtree<T extends Comparable<T>> {
    private AVLtreeNode<T> root;
    private StringBuilder pr;//print

    public AVLtree() {
        root = new AVLtreeNode<>();
//        root = null;
    }

    public AVLtree(AVLtreeNode<T> root) {
        this.root = root;
    }

    public void insert(T element) {
        root = insert(element, root);
    }

    public AVLtreeNode<T> insert(T element, AVLtreeNode<T> base) {
        if (base == null) {
            base = new AVLtreeNode<>(element, null, null);
        } else if (base.getItem() == null) {
            base.setItem(element);
        } else {
            if (base.getItem().compareTo(element) < 0) {
                base.setRightChild(insert(element, base.getRightChild()));
                if (base.getBalance() == -2) {
                    if (base.getRightChild().getBalance() < 0) {
//                    if(element.compareTo(base.getRightChild().getItem()) > 0 ){
                        base = rotate(base, 1);
                    } else { //if (base.getRightChild().getBalance() > 0) {
                        base.setRightChild(rotate(base.getRightChild(), -1));
                        base = rotate(base, 1);
                    }
                }
//                }
            } else if (base.getItem().compareTo(element) >= 0) {
                base.setLeftChild(insert(element, base.getLeftChild()));
                if (base.getBalance() == 2) {
                    if (base.getLeftChild().getBalance() > 0) {
//                    if ( element.compareTo(base.getLeftChild().getItem()) < 0 ){
                        base = rotate(base, -1);
                    } else {//if (base.getLeftChild().getBalance() < 0) {
                        base.setLeftChild(rotate(base.getLeftChild(), 1));
                        base = rotate(base, -1);
                    }
                }
            }
        }
        AVLtreeNode<T> right = base.getRightChild();
        AVLtreeNode<T> left = base.getLeftChild();
        if (base.getItem() != null)
            base.setHeight(Math.max((left == null ? 0 : left.getHeight()), (right == null ? 0 : right.getHeight())) + 1);
        return base;
    }

    private AVLtreeNode<T> rotate(AVLtreeNode<T> rot, int direction) {
        //if direction == -1, LLrotate(clockwise). if direction == 1, RRrotate(counterclockwise)
        //Every reconnect needs updating height
        if (direction == -1) {
            AVLtreeNode<T> x = rot;
            AVLtreeNode<T> y = rot.getLeftChild();
            x.setLeftChild(y.getRightChild());
            y.setRightChild(x);
            x.setHeight(Math.max((x.getLeftChild() == null ? 0 : x.getLeftChild().getHeight()), (x.getRightChild() == null ? 0 : x.getRightChild().getHeight())) + 1);
            y.setHeight(Math.max((y.getLeftChild() == null ? 0 : y.getLeftChild().getHeight()), (y.getRightChild() == null ? 0 : y.getRightChild().getHeight())) + 1);
            return y;
        }
        if (direction == 1) {
            AVLtreeNode<T> x = rot;
            AVLtreeNode<T> y = rot.getRightChild();
            x.setRightChild(y.getLeftChild());
            y.setLeftChild(x);
            x.setHeight(Math.max((x.getLeftChild() == null ? 0 : x.getLeftChild().getHeight()), (x.getRightChild() == null ? 0 : x.getRightChild().getHeight())) + 1);
            y.setHeight(Math.max((y.getLeftChild() == null ? 0 : y.getLeftChild().getHeight()), (y.getRightChild() == null ? 0 : y.getRightChild().getHeight())) + 1);
            return y;
        }
        return null;
    }

    public AVLtreeNode<T> getRoot() {
        return root;
    }

    public T search(Function<T, Integer> comparator) {
//        int check = 0;
        AVLtreeNode<T> curr = root;
        while (curr != null) {
            if (comparator.apply(curr.getItem()) > 0) {
                curr = curr.getLeftChild();
            } else if (comparator.apply(curr.getItem()) < 0) {
                curr = curr.getRightChild();
            } else {
                return curr.getItem();
            }
        }
        return null;
    }

    public ArrayList<T> preorder() {
        ArrayList<T> out = new ArrayList<>();
        if (root.getItem() == null)
            return null;
        return preorder(root, out);
    }

    private ArrayList<T> preorder(AVLtreeNode<T> rot, ArrayList<T> out) {
        if (rot == null)
            return out;
        out.add(rot.getItem());
        out = preorder(rot.getLeftChild(), out);
        out = preorder(rot.getRightChild(), out);
        return out;
    }
}
