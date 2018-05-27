import java.util.Stack;

public class HashTable {
    private AVLtree<LinkedList<IndexNode>>[] hashtable = new AVLtree[100];//0base index...

    public HashTable() {
        for (int i = 0; i < 100; i++)
            hashtable[i] = new AVLtree<>();
    }

    public int hashFunc(String substring) {//k == 6, so substring's length is 6
        int hashcode = 0;
        for (int i = 0; i < 6; i++) {
            hashcode += substring.charAt(i);
        }
        hashcode = hashcode % 100;
        return hashcode;
    }

    public void insert(String substring, int i, int j) {
        int hashcode = hashFunc(substring);
        LinkedList<IndexNode> in = new LinkedList<>();
        in.setDummyHead(substring);
        in.insert(i, j);
        hashtable[hashcode].insert(in, i, j);
    }

    public String print(int idx) {//@ command
        AVLtree<LinkedList<IndexNode>> tree = hashtable[idx];
        return tree.print(tree.getRoot());
    }
}
