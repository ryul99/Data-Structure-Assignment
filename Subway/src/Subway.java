import java.io.*;
import java.util.*;

public class Subway {
    public static void main(String args[]) {
        HashMap<String, LinkedList<Pair<String, Long>>> adjacencyList = new HashMap<>();//<Unique, LinkedList<Unique, distance>> / immutable
        HashMap<String, String> match = new HashMap<>();//<Unique, station> / immutable
        HashMap<String, String> revmatch = new HashMap<>();//<station, Unique> / immutable
        HashMap<String, LinkedList<String>> transfer = new HashMap<>();//<station, LinkedLIst<Unique>>
//        SortedSet<Pair<Long, String>> accudis = new TreeSet<>();

//        int check = 0;// 0 is shortest distance, 1 is fewest transfer
//        HashSet<String> chT = new HashSet<>();//<Unique> transfer stations are in it
        HashMap<String, Pair<Long, String>> contains;//<Unique, member of accD> / whether accD contains specific Unique or not
        HashMap<String, Pair<Pair<Long, Long>, String>> containsT;//<Unique, member of accTD> / transfer version of contains
        HashMap<String, LinkedList<Pair<String, Long>>> tempAdList;//<Unique, LinkedList<Unique, distance>> / temporary
        HashSet<String> visited = new HashSet<>();//<Unique> / check whether Unique(station) is visited (visited Unique is in it)
        HashSet<String> visiSta = new HashSet<>();//station version of visited
        SortedSet<Pair<Long, String>> accD;//<accumulated distance, Unique> / starting point is 1. get real result by minus 1 from result / must reset each case
        SortedSet<Pair<Pair<Long, Long>, String>> accTD;//<<accumulated transfer, accumulated distance>, Unique>
        HashMap<String, String> comeFrom = new HashMap<>();//<Unique1, Unique2> / Unique1 is comes from Unique2
        File file = new File(args[0]);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader data = new BufferedReader(new FileReader(file));

            //pre processing
            while (true) {
                String in = data.readLine();
                if (in.equals(""))
                    break;
                String[] inarr = in.split(" ");
                match.put(inarr[0], inarr[1]);
                String o = revmatch.put(inarr[1], inarr[0]);
                if (o != null) {//if value of revmatch has replaced == station is transfer station
                    for (String i : transfer.get(inarr[1])) {
                        if (adjacencyList.get(i) == null) {
                            LinkedList<Pair<String, Long>> a = new LinkedList<>();
                            a.add(new Pair<>(inarr[0], (long) 5));
                            adjacencyList.put(i, a);
                        } else {
                            adjacencyList.get(i).add(new Pair<>(inarr[0], (long) 5));
                        }
                        if (adjacencyList.get(inarr[0]) == null) {
                            LinkedList<Pair<String, Long>> a = new LinkedList<>();
                            a.add(new Pair<>(i, (long) 5));
                            adjacencyList.put(inarr[0], a);
                        } else {
                            adjacencyList.get(inarr[0]).add(new Pair<>(i, (long) 5));
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
                    LinkedList<Pair<String, Long>> a = new LinkedList<>();
                    a.add(new Pair<>(inarr[1], Long.parseLong(inarr[2])));
                    adjacencyList.put(inarr[0], a);
                } else {
                    adjacencyList.get(inarr[0]).add(new Pair<>(inarr[1], Long.parseLong(inarr[2])));
                }
            }


            while (true) {//every case

                int startingC = 0;//checking starting point is transfer 0 is not 1 is transfer
                String endP = null;
                long dist = 0;//distance of from start to end + 1
//                chT = new HashSet<>();
                visiSta = new HashSet<>();
                visited = new HashSet<>();
                comeFrom = new HashMap<>();
                tempAdList = new HashMap<>();

                for (HashMap.Entry<String, LinkedList<Pair<String, Long>>> entry : adjacencyList.entrySet()) {
                    LinkedList<Pair<String, Long>> a = new LinkedList<>();
                    for (Pair<String, Long> g : entry.getValue()) {
                        a.add(new Pair<>(g.first(), g.second()));
                    }
                    tempAdList.put(entry.getKey(), a);
                }

                //interpreting
                String in = br.readLine();
                if (in.equals("QUIT"))
                    break;
                String[] inarr = in.split(" ");
                String start = revmatch.get(inarr[0]);
                String end = revmatch.get(inarr[1]);
                if (transfer.get(inarr[0]).size() > 1) {//if starting station is transfer station
                    startingC = 1;
                    for (String i : transfer.get(inarr[0])) {
                        for (String j : transfer.get(inarr[0])) {
                            if (!i.equals(j)) {
                                tempAdList.get(i).remove(new Pair<>(j, (long) 5));
                                tempAdList.get(i).add(new Pair<>(j, (long) 0));
                            }
                        }
                    }
                }

                //dijkstra
                if (inarr.length == 3 && inarr[2].equals("!")) {//minimum transfer
                    accTD = new TreeSet<>();
                    containsT = new HashMap<>();
                    accTD.add(new Pair<>(new Pair<>((long) 0, (long) 1), start));
                    while (!visiSta.contains(match.get(end))) {
                        int transfercheck;//0 is not transferring station, 1 is transferring station
                        long tf = accTD.first().first().first();
                        dist = accTD.first().first().second();
                        String Uni = accTD.first().second();
                        accTD.remove(accTD.first());
                        visited.add(Uni);
                        visiSta.add(match.get(Uni));
                        if (visiSta.contains(match.get(end)))
                            endP = Uni;
                        if (tempAdList.get(Uni) != null) {
                            for (Pair<String, Long> ele : tempAdList.get(Uni)) {
                                transfercheck = 0;
                                if (!visited.contains(ele.first())) {
                                    Pair<Pair<Long, Long>, String> o = containsT.get(ele.first());
                                    Pair<Pair<Long, Long>, String> n;
                                    if (match.get(Uni).equals(match.get(ele.first())) && !Uni.equals(start))
                                        transfercheck = 1;
                                    if (transfercheck == 0)
                                        n = new Pair<>(new Pair<>(tf, (dist + ele.second())), ele.first());
                                    else
                                        n = new Pair<>(new Pair<>(tf + 1, (dist + ele.second())), ele.first());
                                    if (o == null) {
                                        accTD.add(n);
                                        containsT.put(ele.first(), n);
                                        comeFrom.put(ele.first(), Uni);
                                    } else if (o.first().compareTo(n.first()) > 0) {
                                        accTD.remove(o);
                                        accTD.add(n);
//                                    contains.remove(ele.first());
                                        containsT.put(ele.first(), n);
//                                    comeFrom.remove(ele.first());
                                        comeFrom.put(ele.first(), Uni);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    accD = new TreeSet<>();
                    contains = new HashMap<>();
                    accD.add(new Pair<>((long) 1, start));
                    while (!visiSta.contains(match.get(end))) {
                        dist = accD.first().first();
                        String Uni = accD.first().second();
                        accD.remove(accD.first());
                        visited.add(Uni);
                        visiSta.add(match.get(Uni));
                        if (visiSta.contains(match.get(end)))
                            endP = Uni;
                        if (tempAdList.get(Uni) != null) {
                            for (Pair<String, Long> ele : tempAdList.get(Uni)) {
                                if (!visited.contains(ele.first())) {
                                    Pair<Long, String> o = contains.get(ele.first());
                                    Pair<Long, String> n = new Pair<>((dist + ele.second()), ele.first());
                                    if (o == null) {
                                        accD.add(n);
                                        contains.put(ele.first(), n);
                                        comeFrom.put(ele.first(), Uni);
                                    } else if (o.first() - n.first() > 0) {
                                        accD.remove(o);
                                        accD.add(n);
//                                    contains.remove(ele.first());
                                        contains.put(ele.first(), n);
//                                    comeFrom.remove(ele.first());
                                        comeFrom.put(ele.first(), Uni);
                                    }
                                }
                            }
                        }
                    }
                }


                //print
                StringBuilder out = new StringBuilder();
                String where = endP;//Unique
                String prewhere = null;
                int prestart;
                while (!match.get(where).equals(match.get(start))) {
                    prestart = match.get(where).length() + 1;
                    if (where.equals(endP)) {
                        out.insert(0, match.get(where));
                    } else {
                        if (!match.get(where).equals(match.get(prewhere)))
                            out.insert(0, " ").insert(0, match.get(where));
                        else if (!match.get(where).equals(match.get(end))) {
                            out.delete(0, prestart);
                            out.insert(0, " ").insert(0, "]").insert(0, match.get(where)).insert(0, "[");
                        }
                    }
                    prewhere = where;
                    where = comeFrom.get(where);
                }
                out.insert(0, " ").insert(0, match.get(where));
                System.out.println(out.toString());
                System.out.println(dist - 1);

                //refresh
//                tempAdList.clear();
//                contains.clear();
//                visited.clear();
//                accD.clear();
//                comeFrom.clear();
//                chT.clear();
//                visiSta.clear();
//                accTD.clear();
//                containsT.clear();
            }
            br.close();
            data.close();
        } catch (IOException e) {
            System.out.println("ERROR : " + e.toString());
        }
    }
}
