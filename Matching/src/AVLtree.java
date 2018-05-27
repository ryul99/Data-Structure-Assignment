//public class AVLtree<T extends Comparable<? super T>> {
public class AVLtree<T extends LinkedList> {
    private AVLtreeNode<T> root;
    private int numberOfItems;
    private StringBuilder pr;//print

    public AVLtree() {
        root = new AVLtreeNode();
        numberOfItems = 0;
    }

    public AVLtree(AVLtreeNode<T> root) {
        this.root = root;
        numberOfItems = 0;
    }

    public void insert(T element, int i, int j) {
        root = insert(element, i, j, root);
    }

    public AVLtreeNode insert(T element, int i, int j, AVLtreeNode<T> base) {
        numberOfItems++;
//        AVLtreeNode<T> uBase = null;//unbalanced base
//        AVLtreeNode<T> uParent = null;//unbalanced base's parent
//        AVLtreeNode<T> tBase = null;//temporary base
//        int where = 0;//where the uBase is. -1 is uParent's left, 1 is right
//        int twhere = 0;//temporary where
        if (base == null) {
            AVLtreeNode<T> a = new AVLtreeNode<T>(element, null, null);
            a.getItem().insert(i, j);
            base = a;
        } else if (base.getItem() == null) {
            base.setItem(element);
        } else {
            AVLtreeNode<T> right = base.getRightChild();
            AVLtreeNode<T> left = base.getLeftChild();
//            while (true) {
//                right = base.getRightChild();
//                left = base.getLeftChild();
            if (base.getItem().compareTo(element) < 0) {
//                    base.setHeight(Math.max((left == null ? 0 : left.getHeight()), ((right == null ? 0 : right.getHeight()) + 1)) + 1);
//                    base.setBalance((left == null ? 0 : left.getHeight()) - ((right == null ? 0 : right.getHeight()) + 1));
//                    base.setRightChild(insert(element, i, j, right));
//                    if (2 <= base.getBalance() || -2 >= base.getBalance()) {
//                        uBase = base;
//                        uParent = tBase;
//                        where = twhere;
////                    }
//                if (right == null) {
//                    base.setRightChild(new AVLtreeNode(element, null, null));
////                        break;
////                        return base;
//                }
//                else {
//                        tBase = base;
//                        base = right;
//                        twhere = 1;
                base.setRightChild(insert(element, i, j, right));
//                }
            } else if (base.getItem().compareTo(element) > 0) {
//                    base.setHeight(Math.max(((left == null ? 0 : left.getHeight()) + 1), (right == null ? 0 : right.getHeight())) + 1);
//                    base.setBalance(((left == null ? 0 : left.getHeight()) + 1) - (right == null ? 0 : right.getHeight()));
//                    base.setLeftChild(insert(element, i, j, left));
//                    if (2 <= base.getBalance() || -2 >= base.getBalance()) {
//                        uBase = base;
//                        uParent = tBase;
//                        where = twhere;
//                    }
//                if (left == null) {
//                    base.setLeftChild(new AVLtreeNode<>(element, null, null));
////                        break;
////                        return base;
//                } else {
//                        tBase = base;
//                        base = left;
//                        twhere = -1;
                base.setLeftChild(insert(element, i, j, left));
//                }
            } else {
                base.getItem().insert(i, j);
//                    break;
//                    return base;
            }
//            }
            if (base != null) {
                //use else if because changing can effect testing
                if (base.getBalance() >= 2) {
                    if (base.getLeftChild().getBalance() > 0)
//                    if ( element.compareTo(base.getLeftChild().getItem()) < 0 )
                        base = rotate(base, -1);
                    else {//if (base.getLeftChild().getBalance() < 0) {
                        base.setLeftChild(rotate(base.getLeftChild(), 1));
                        base = rotate(base, -1);
                    }
                } else if (base.getBalance() <= -2) {
                    if (base.getRightChild().getBalance() < 0)
//                    if(element.compareTo(base.getRightChild().getItem()) > 0 )
                        base = rotate(base, 1);
                    else { //if (base.getRightChild().getBalance() > 0) {
                        base.setRightChild(rotate(base.getRightChild(), -1));
                        base = rotate(base, 1);
                    }
                }
            }
        }
        return base;
    }

    private AVLtreeNode<T> rotate(AVLtreeNode<T> rot, int direction) {
        //if direction == -1, LLrotate(clockwise). if direction == 1, RRrotate(counterclockwise)
        //Every reconnect needs updating height
        if (direction == -1) {
            AVLtreeNode<T> x = rot;
            AVLtreeNode<T> y = rot.getLeftChild();
            AVLtreeNode<T> z = y.getLeftChild();
            x.setLeftChild(y.getRightChild());
            x.setHeight(Math.max((x.getLeftChild() == null ? 0 : x.getLeftChild().getHeight()), (x.getRightChild() == null ? 0 : x.getRightChild().getHeight())) + 1);
            y.setRightChild(x);
            y.setHeight(Math.max((y.getLeftChild() == null ? 0 : y.getLeftChild().getHeight()), (y.getRightChild() == null ? 0 : y.getRightChild().getHeight())) + 1);
//            if (parent != null) {
//                if (where == -1)
//                    parent.setLeftChild(y);
//                if (where == 1)
//                    parent.setRightChild(y);
//                parent.setHeight(Math.max((parent.getLeftChild() == null ? 0 : parent.getLeftChild().getHeight()), (parent.getRightChild() == null ? 0 : parent.getRightChild().getHeight())) + 1);
//            }
            return y;
        }
        if (direction == 1) {
            AVLtreeNode<T> x = rot;
            AVLtreeNode<T> y = rot.getRightChild();
            AVLtreeNode<T> z = y.getRightChild();
            x.setRightChild(y.getLeftChild());
            x.setHeight(Math.max((x.getLeftChild() == null ? 0 : x.getLeftChild().getHeight()), (x.getRightChild() == null ? 0 : x.getRightChild().getHeight())) + 1);
            y.setLeftChild(x);
            y.setHeight(Math.max((y.getLeftChild() == null ? 0 : y.getLeftChild().getHeight()), (y.getRightChild() == null ? 0 : y.getRightChild().getHeight())) + 1);
//            if (parent != null) {
//                if (where == -1)
//                    parent.setLeftChild(y);
//                if (where == 1)
//                    parent.setRightChild(y);
//                parent.setHeight(Math.max((parent.getLeftChild() == null ? 0 : parent.getLeftChild().getHeight()), (parent.getRightChild() == null ? 0 : parent.getRightChild().getHeight())) + 1);
//            }
            return y;
        }
        return null;
    }

    public AVLtreeNode<T> getRoot() {
        return root;
    }

    public T search(T input) {//input's length is 6
        int check = 0;
        AVLtreeNode<T> curr = root;
        while (curr != null) {
            if (curr.getItem().compareTo(input) > 0) {
                curr = curr.getLeftChild();
            } else if (curr.getItem().compareTo(input) < 0) {
                curr = curr.getRightChild();
            } else {
                return curr.getItem();
            }
        }
        return null;
    }

    public String print(AVLtreeNode<LinkedList<IndexNode>> roo) {
        StringBuilder re = new StringBuilder("");
        String ret = print(roo, re);
        if (ret.length() == 0)
            return "EMPTY";
        return ret;
    }

    private String print(AVLtreeNode<LinkedList<IndexNode>> rot, StringBuilder re) {
        if (rot != null && rot.getItem() != null) {
            if (re.length() != 0)
                re.append(" ");
            re.append(rot.getItem().toString());
            print(rot.getLeftChild(), re);
            print(rot.getRightChild(), re);
            return re.toString();
        }
        return "";
    }
}
