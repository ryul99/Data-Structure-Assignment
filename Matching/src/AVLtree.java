public class AVLtree<T extends Comparable<? super T>> {
    private AVLtreeNode<T> root;

    public AVLtree() {
        root = new AVLtreeNode();
    }

    public AVLtree(AVLtreeNode<T> root) {
        this.root = root;
    }

    public void insert(T element) {
        AVLtreeNode<T> base = root;
        AVLtreeNode<T> uBase = null;//unbalanced base
        AVLtreeNode<T> uParent = null;//unbalanced base's parent
        int where = 0;//where the uBase is. -1 is uParent's left, 1 is right
        int twhere = 0;//temporary where
        AVLtreeNode<T> tBase = null;//temporary base
        while (true) {
            AVLtreeNode<T> right = base.getRightChild();
            AVLtreeNode<T> left = base.getLeftChilid();
            base.setHeight(base.getHeight() + 1);
            if (base.getItem().compareTo(element) < 0) {
                base.setBalance((left == null ? 0 : left.getHeight()) - ((right == null ? 0 : right.getHeight()) + 1));
                if (2 <= base.getBalance() || -2 >= base.getBalance()) {
                    uBase = base;
                    uParent = tBase;
                    where = twhere;
                }
                if (right == null) {
                    base.setRightChild(new AVLtreeNode(element, null, null));
                    break;
                } else {
                    tBase = base;
                    base = right;
                    twhere = 1;
                }
            } else {
                base.setBalance(((left == null ? 0 : left.getHeight()) + 1) - (right == null ? 0 : right.getHeight()));
                if (2 <= base.getBalance() || -2 >= base.getBalance()) {
                    uBase = base;
                    uParent = tBase;
                }
                if (left == null) {
                    root.setLeftChilid(new AVLtreeNode<>(element, null, null));
                    break;
                } else {
                    tBase = base;
                    base = left;
                    twhere = -1;
                }
            }
        }
        if (uBase.getBalance() <= -2){
            if(uBase.getLeftChilid().getBalance() < 0)
                rotate(uBase, uParent, where, -1);
            if(uBase.getLeftChilid().getBalance() > 0){
                rotate(uBase.getLeftChilid(), uBase, 1, 1 );
                rotate(uParent, uBase, where, -1);
            }
        }
        if(uBase.getBalance() >= 2){
            if(uBase.getRightChild().getBalance() > 0)
                rotate(uBase, uParent, where, 1);
            if(uBase.getRightChild().getBalance() < 0) {
                rotate(uBase.getRightChild(), uBase, -1, -1);
                rotate(uBase, uParent, where, 1);
            }
        }
    }

    private void rotate(AVLtreeNode<T> rot, AVLtreeNode<T> parent, int where, int direction) {//if direction == -1, LLrotate(clockwise). if direciton == 1, RRrotate(counterclockwise)
        if (direction == -1) {
            AVLtreeNode<T> x = rot;
            AVLtreeNode<T> y = rot.getLeftChilid();
            AVLtreeNode<T> z = y.getLeftChilid();
            x.setLeftChilid(y.getRightChild());
            y.setRightChild(x);
            if (parent != null) {
                if (where == -1)
                    parent.setLeftChilid(y);
                if (where == 1)
                    parent.setRightChild(y);
            }
        }
        if (direction == 1) {
            AVLtreeNode<T> x = rot;
            AVLtreeNode<T> y = rot.getRightChild();
            AVLtreeNode<T> z = y.getRightChild();
            x.setRightChild(y.getLeftChilid());
            y.setLeftChilid(x);
            if (parent != null) {
                if (where == -1)
                    parent.setLeftChilid(y);
                if (where == 1)
                    parent.setRightChild(y);
            }
        }
    }
}
