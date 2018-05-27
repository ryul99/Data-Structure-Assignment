public class Pair<T extends Comparable<T>, U extends Comparable<U>> implements Comparable<Pair<T, U>>{
    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }

    public String toString() {
        StringBuilder re = new StringBuilder("(");
        re.append(first);
        re.append(", ");
        re.append(second);
        re.append(")");
        return re.toString();
    }

    public int compareTo(Pair<T, U> other) {
        int compare = first.compareTo(other.first);
        if (compare != 0)
            return compare;
        return second.compareTo(other.second);
    }
}
