import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * <p>
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를
 * 유지하는 데이터베이스이다.
 */
public class MovieDB {
    MyLinkedList<MovieList> list;

    public MovieDB() {
        // FIXME implement this...done
        list = new MyLinkedList<>();
        // HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한
        // MyLinkedList 타입의 멤버 변수를 초기화 한다.
    }

    public void insert(MovieDBItem item) {

        String gen = item.getGenre();
        String tit = item.getTitle();
        Genre genNode = new Genre(gen);
        Node<MovieList> previous = list.Head();
        if (list.isEmpty()) {
            MovieList mv = new MovieList(genNode);
            mv.add(tit);
            list.putGenre(mv, previous);
        } else {
            MovieList curmovlist = null;
            boolean check = false;
            // FIXME implement this...done
            // Insert the given item to the MovieDB.

                for (MyLinkedListIterator<MovieList> lst = new MyLinkedListIterator<>(list); lst.hasNext(); ) {
                    curmovlist = lst.next();
                    if (genNode.equals(curmovlist.Head())) {
                            curmovlist.add(tit);
                            check = true;
                            break;
                    } else if (genNode.compareTo(curmovlist.Head()) < 0) {
                        MovieList mv = new MovieList(genNode);
                        mv.add(tit);
                        list.putGenre(mv, previous);
                        check = true;
                        break;
                    }
                    previous = lst.getCurr();
                }
                if (!check) {
                    MovieList mv = new MovieList(genNode);
                    mv.add(tit);
                    list.putGenre(mv, previous);
                }

        }
        // Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
        String gen = item.getGenre();
        String tit = item.getTitle();
        Genre genNode = new Genre(gen);
        if (!list.isEmpty()) {
            MovieList curmovlist = null;
            for (MyLinkedListIterator<MovieList> lst = new MyLinkedListIterator<>(list); lst.hasNext();) {
                curmovlist = lst.next();
                if (genNode.equals(curmovlist.Head())) {
                    if (curmovlist.isEmpty())
                        lst.remove();
                    else {
                        String searIte = null;
                        for (MovieListIterator ite = new MovieListIterator(curmovlist); ite.hasNext();) {
                            searIte = ite.next();
                            if (searIte.equals(tit))
                                ite.remove();
                        }
                    }
                    break;
                } else if (genNode.compareTo(curmovlist.Head()) < 0) {
                    return;
                }
                if (lst.getCurr().getItem().isEmpty())
                    lst.remove();
            }
        }

        // Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());
    }

    public MyLinkedList<MovieDBItem> search(String term) {//term is title
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
        MyLinkedList<MovieDBItem> results = new MyLinkedList<>();
        String tit = term;
        MovieList curmovlist = null;

        for (MyLinkedListIterator<MovieList> lst = new MyLinkedListIterator<>(list); lst.hasNext(); ) {
            curmovlist = lst.next();
            if (!curmovlist.isEmpty()) {
                String gen = curmovlist.Head().getItem();
                String searIte = null;
                for (MovieListIterator ite = new MovieListIterator(curmovlist); ite.hasNext(); ) {
                    searIte = ite.next();
                    if (searIte == null) {
                        if (tit == null)
                            results.add(new MovieDBItem(gen, null));
                    } else if (searIte.contains(tit)) {
                        results.add(new MovieDBItem(gen, searIte));
                    }
                }
            }
        }
        // Printing search results is the responsibility of SearchCmd class.
        // So you must not use System.out in this method to achieve specs of the assignment.

        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);

        // FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
        // This code is supplied for avoiding compilation error.

        return results;
    }

    public MyLinkedList<MovieDBItem> items() {//for print
        // FIXME implement this
        // Search the given term from the MovieDatabase.
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

        // Printing movie items is the responsibility of PrintCmd class.
        // So you must not use System.out in this method to achieve specs of the assignment.

        // Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        //System.err.printf("[trace] MovieDB: ITEMS\n");

        // FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
        // This code is supplied for avoiding compilation error.
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        if (!list.isEmpty()) {
            MovieList curmovlist = null;

            for (MyLinkedListIterator<MovieList> lst = new MyLinkedListIterator<>(list); lst.hasNext();) {
                curmovlist = lst.next();
                if (!curmovlist.isEmpty()) {
                    String gen = curmovlist.Head().getItem();
                    String searIte = null;
                    for (MovieListIterator ite = new MovieListIterator(curmovlist); ite.hasNext();) {
                        searIte = ite.next();
                        results.add(new MovieDBItem(gen, searIte));
                    }
                }
            }
        }
        return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
    public Genre(String name) {
        super(name);
        //throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public int compareTo(Genre o) {
        return this.getItem().compareTo(o.getItem());
        //throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getItem() == null) ? 0 : getItem().hashCode());
        return result;
        //throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Genre other = (Genre) obj;
        if (this.getItem() == null) {
            if (other.getItem() != null)
                return false;
        } else if (!this.getItem().equals(other.getItem()))
            return false;
        return true;
        //throw new UnsupportedOperationException("not implemented yet");
    }
}

class MovieList implements ListInterface<String> {
    Genre head;
    int numItems;

    public MovieList() {
        head = new Genre(null);
        numItems = 0;
    }

    public MovieList(Genre in) {
        head = in;
        head.setNext(null);
        numItems = 0;
    }

    public Genre Head() {
        return head;
    }

    @Override
    public Iterator<String> iterator() {
        return new MovieListIterator(this);
        //throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean isEmpty() {
        return head.getNext() == null;
        //throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public int size() {
        return numItems;
        //throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void add(String item) {
        Node<String> curr = head;
        while (curr.getNext() != null && item.compareTo(curr.getNext().getItem()) >= 0) {
            if(item.compareTo(curr.getNext().getItem()) == 0)
                return;
            curr = curr.getNext();
        }
        Node<String> ne = new Node<String>(item, curr.getNext());
        curr.setNext(ne);
        numItems += 1;
        return;
        //throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public String first() {
        return head.getNext().getItem();
        //throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void removeAll() {
        head.setNext(null);
        return;
        //throw new UnsupportedOperationException("not implemented yet");
    }

}

class MovieListIterator implements Iterator<String> {
    private MovieList list;
    private Node<String> curr;
    private Node<String> prev;

    public MovieListIterator(MovieList list) {
        this.list = list;
        this.curr = list.Head();
        this.prev = null;
    }

    @Override
    public boolean hasNext() {
        return curr.getNext() != null;
    }

    @Override
    public String next() {
        if (!hasNext())
            throw new NoSuchElementException();

        prev = curr;
        curr = curr.getNext();

        return curr.getItem();
    }

    @Override
    public void remove() {
        if (prev == null)
            throw new IllegalStateException("next() should be called first");
        if (curr == null)
            throw new NoSuchElementException();
        prev.removeNext();
        list.numItems -= 1;
        curr = prev;
        prev = null;
        return;
    }
}