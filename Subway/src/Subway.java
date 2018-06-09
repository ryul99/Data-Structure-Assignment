import com.sun.deploy.util.OrderedHashSet;

import java.io.*;
import java.util.*;

public class Subway {
    public static void main(String args[]) {
        HashMap<String, LinkedList<Pair<String, Integer>>> adjacencyList = new HashMap<>();//<Unique, LinkedList<Unique, distance>> / immutable
        HashMap<String, String> match = new HashMap<>();//<Unique, station> / immutable
        HashMap<String, String> revmatch = new HashMap<>();//<station, Unique> / immutable
        HashMap<String, LinkedList<String>> transfer = new HashMap<>();//<station, LinkedLIst<Unique>>
        SortedSet<Pair<Integer, String>> accudis = new TreeSet<>();//<accumulated distance, Unique> / starting point is 1. get real result by minus 1 from result / must reset each case...so make this immutable

        HashMap<String, Pair<Integer, String>> contains;//<Unique, member of accD> / whether accD contains specific Unique or not
        HashMap<String, LinkedList<Pair<String, Integer>>> tempAdList;//<Unique, LinkedList<Unique, distance>> / temporary
        HashSet<String> visited = new HashSet<>();//<Unique> / check whether Unique(station) is visited (visited Unique is in it)
        SortedSet<Pair<Integer, String>> accD;//clone of accudis
        HashMap<String, String> comeFrom = new HashMap<>();//<Unique1, Unique2> / Unique1 is comes from Unique2
        File file = new File(args[0]);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader data = new BufferedReader(new FileReader(file));

            //pre processing
            while (true) {
                String in = data.readLine();
                if (in == "\n")
                    break;
                String[] inarr = in.split(" ");
                match.put(inarr[0], inarr[1]);
                String o = revmatch.put(inarr[1], inarr[0]);
                if (o != null) {//if value of revmatch has replaced == station is transfer station
                    for (String i : transfer.get(inarr[1])) {
                        if (adjacencyList.get(i) == null) {
                            LinkedList<Pair<String, Integer>> a = new LinkedList<>();
                            a.add(new Pair<>(inarr[0], 5));
                            adjacencyList.put(i, a);
                        } else {
                            adjacencyList.get(i).add(new Pair<>(inarr[0], 5));
                        }
                        if (adjacencyList.get(inarr[0]) == null) {
                            LinkedList<Pair<String, Integer>> a = new LinkedList<>();
                            a.add(new Pair<>(i, 5));
                            adjacencyList.put(inarr[0], a);
                        } else {
                            adjacencyList.get(inarr[0]).add(new Pair<>(i, 5));
                        }
                    }
                    transfer.get(inarr[1]).add(inarr[0]);
                } else {
                    LinkedList<String> a = new LinkedList<>();
                    a.add(inarr[0]);
                    transfer.put(inarr[1], a);
                }
            }
            while (true) {
                String in = data.readLine();
                if (in == null)
                    break;
                String[] inarr = in.split(" ");
                if (adjacencyList.get(inarr[0]) == null) {
                    LinkedList<Pair<String, Integer>> a = new LinkedList<>();
                    a.add(new Pair<>(inarr[1], Integer.parseInt(inarr[2])));
                    adjacencyList.put(inarr[0], a);
                } else {
                    adjacencyList.get(inarr[0]).add(new Pair<>(inarr[1], Integer.parseInt(inarr[2])));
                }
            }


            while (true) {//every case
                contains = new HashMap<>();
                visited = new HashSet<>();
                comeFrom = new HashMap<>();
                accD = (TreeSet<Pair<Integer,String>>) ((TreeSet<Pair<Integer,String>>) accudis).clone();
                tempAdList = (HashMap<String, LinkedList<Pair<String, Integer>>>) adjacencyList.clone();

                String in = br.readLine();
                if (in == null)
                    break;
                String[] inarr = in.split(" ");
                String start = revmatch.get(inarr[0]);
                String end = revmatch.get(inarr[1]);
                accD.add(new Pair<>(1, start));
                if (transfer.get(inarr[0]).size() > 1) {//if starting station is transfer station
                    for (String i : transfer.get(inarr[0])) {
                        for(Pair<String , Integer> j : tempAdList.get(i)){
                            if(transfer.get(inarr[0]).contains(j.first())) {
                                tempAdList.get(i).remove(j);
                                tempAdList.get(i).add(new Pair<>(j.first(), 0));
                            }
                        }
                    }
                }
                while (visited.contains(end)) {
                    visited.add(accD.first().second());
                    for (Pair<String, Integer> ele : tempAdList.get(accD.first().second()) ){
                        if(!visited.contains(ele.first())) {
                            accD.remove(contains.get(ele.first()));

                        }
                    }
                }

                tempAdList.clear();
                contains.clear();
                visited.clear();
                accD.clear();
                comeFrom.clear();
            }
        } catch (IOException e) {
            System.out.println("ERROR : " + e.toString());
        }
    }
}
