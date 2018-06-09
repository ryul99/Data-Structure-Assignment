public class LPair<Long, U extends Comparable<U>> implements Comparable<LPair<Long, U>> {
    private final Long first;
    private final U second;

    public LPair(Long first, U second) {
        this.first = first;
        this.second = second;
    }

    public Long first() {
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

    @Override
    public int compareTo(LPair<Long, U> other) {
        long compare = (long) first - (long) other.first;
        if (compare > 0)
            return 1;
        else if (compare < 0)
            return -1;
        return second.compareTo(other.second);
    }


    public boolean equals(LPair<Long, U> other) {
        if (this.first.equals(other.first) && this.second.equals(other.second))
            return true;
        return false;
    }
}
