import java.util.ArrayList;
import java.util.function.Function;

public class HashTable<K extends Comparable<K>, V extends Comparable<V>> {
    //    private AVLtree<LinkedList<IndexNode>>[] hashtable = new AVLtree[100];//0base index...
    private AVLtree<Pair<K, LinkedList<V>>>[] hashtable = new AVLtree[100];

    private Function<K, Integer> hasher;

    public HashTable(Function<K, Integer> hasher) {
        this.hasher = hasher;
        for (int i = 0; i < 100; i++)
            hashtable[i] = new AVLtree<>();
    }

//    public int hashFunc(String substring) {//k == 6, so substring's length is 6
//        int hashcode = 0;
//        for (int i = 0; i < 6; i++) {
//            hashcode += substring.charAt(i);
//        }
//        hashcode = hashcode % 100;
//        return hashcode;
//    }

    public void insert(K key, V value) {
        int hashcode = hasher.apply(key);
        LinkedList<V> in = new LinkedList<>();
        in.insert(value);
        Pair<K, LinkedList<V>> inn = new Pair<>(key, in);
        if (hashtable[hashcode].getRoot().getItem() == null)
            hashtable[hashcode].getRoot().setItem(inn);
        else if (hashtable[hashcode].search(p -> p.first().compareTo(key)) == null) {
            hashtable[hashcode].insert(inn);
        } else {
            Pair<K, LinkedList<V>> a = hashtable[hashcode].search(p -> p.first().compareTo(key));
            a.second().insert(value);
        }
    }

    public String print(int idx) {//@ command
        StringBuilder re = new StringBuilder("");
        AVLtree<Pair<K, LinkedList<V>>> tree = hashtable[idx];
        ArrayList<Pair<K, LinkedList<V>>> list = tree.preorder();
        if(list == null)
            return "EMPTY";
        if (list.isEmpty())
            return "EMPTY";
        for (int i = 0; i < list.size(); i++) {
            if (i != 0)
                re.append(" ");
            re.append(list.get(i).first());
        }
        return re.toString();
    }

    public ArrayList<V> search(K key) {//? command
//        ArrayList<V> out = new ArrayList<>();
        int hashcode = hasher.apply(key);
        if(hashtable[hashcode].getRoot().getItem() == null)
            return null;
        else if(hashtable[hashcode].search(p -> p.first().compareTo(key)) == null)
            return null;
        else {
            Pair<K, LinkedList<V>> e = hashtable[hashcode].search(p -> p.first().compareTo(key));
            if (e == null)
                return null;
            ArrayList<V> out = new ArrayList<>();
            for(int i = 0; i < e.second().getNumberOfItem(); i++)
                out.add(e.second().get(i));
            return out;
        }
    }
}
