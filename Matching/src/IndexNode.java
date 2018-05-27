public class IndexNode {
    int i;//String i
    int j;//substring of String i's starting point. 1 base index

    public IndexNode() {
        i = 0;
        j = 0;
    }

    public IndexNode(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {//return String as (i, j)
        StringBuilder re = new StringBuilder("(");
        re.append(i);
        re.append(", ");
        re.append(j);
        re.append(")");
        return re.toString();
    }
}
