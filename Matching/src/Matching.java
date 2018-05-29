import java.io.*;
import java.util.ArrayList;
import java.util.function.Function;

public class Matching {
    public static Integer hashFunc(String substring) {//k == 6, so substring's length is 6
        int hashcode = 0;
        for (int i = 0; i < 6; i++) {
            hashcode += substring.charAt(i);
        }
        hashcode = hashcode % 100;
        return hashcode;
    }

//    public static Integer betterHashFunc(String s) {
//        return s.chars().sum() % 100;
//    }

    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashTable<String, Pair<Integer, Integer>> table = new HashTable<>(Matching::hashFunc);

        while (true) {
            try {
                String input = br.readLine();
                if (input.compareTo("QUIT") == 0)
                    break;

                table = command(input, table);
            } catch (IOException e) {
                System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
            }
        }
    }

    private static HashTable<String, Pair<Integer, Integer>> command(String input, HashTable inTable) throws IOException {
//		// TODO : 아래 문장을 삭제하고 구현해라.
//		System.out.println("<< command 함수에서 " + input + " 명령을 처리할 예정입니다 >>");
        HashTable table = inTable;
        String in = input.substring(2, input.length());
        int i = 0;
        if (input.charAt(0) == '<') {
            BufferedReader buf = new BufferedReader(new FileReader(in));
            table = new HashTable<>(Matching::hashFunc);
            while (true) {
                i++;
                String read = buf.readLine();
                if (read == null)
                    break;
                for (int j = 0; j < read.length() - 5; j++) {
                    Pair<Integer, Integer> a = new Pair<>(i, j + 1);
                    table.insert(read.substring(j, j + 6), a);
                }
            }
            buf.close();
        } else if (input.charAt(0) == '@') {
            System.out.println(table.print(Integer.parseInt(in)));
        } else if (input.charAt(0) == '?') {
            StringBuilder out = new StringBuilder();
//            System.out.println(table.search(in));
            if (in.length() == 6) {
                ArrayList<Pair<Integer, Integer>> re = table.search(in);
                if (re == null) {
                    System.out.println("(0, 0)");
                    return table;
                }
                for (int f = 0; f < re.size(); f++) {
                    if (f != 0)
                        out.append(" ");
                    out.append(re.get(f).toString());
                }
                System.out.println(out);
                return table;
            } else {
                ArrayList<Pair<Integer, Integer>> ou = new ArrayList<>();
                ArrayList<Pair<Integer, Integer>> tou = new ArrayList<>();//temporary ou
                int share = in.length() / 6;
                int remain = in.length() % 6;
                for (int j = 0; j <= share; j++) {
                    if (j == 0) {
                        String inn = in.substring(j * 6, (j + 1) * 6);
                        ArrayList<Pair<Integer, Integer>> re = table.search(inn);
                        if (re == null) {
                            System.out.println("(0, 0)");
                            return table;
                        }
                        ou = re;
                    } else if (j == share) {
                        if(remain != 0) {
                            int p = 0, q = 0;
                            String inn = in.substring(j * 6 + remain - 6, j * 6 + remain);
                            ArrayList<Pair<Integer, Integer>> re = table.search(inn);
                            if (re == null) {
                                System.out.println("(0, 0)");
                                return table;
                            }
                            while (true) {
                                if (p >= ou.size() || q >= re.size())
                                    break;
                                if (ou.get(p).first() > re.get(q).first())
                                    q++;
                                else if (ou.get(p).first() < re.get(q).first())
                                    p++;
                                else {
                                    if (ou.get(p).second() + 6 * (j - 1) + remain < re.get(q).second())
                                        p++;
                                    else if (ou.get(p).second() + 6 * (j - 1) + remain > re.get(q).second())
                                        q++;
                                    else {
                                        tou.add(ou.get(p));
                                        p++;
                                        q++;
                                    }
                                }
                            }
                            ou = (ArrayList<Pair<Integer, Integer>>) tou.clone();
                            tou.removeAll(tou);
                        }
                        for (int f = 0; f < ou.size(); f++) {
                            if (f != 0)
                                out.append(" ");
                            out.append(ou.get(f).toString());
                        }
                    } else {
                        int p = 0, q = 0;
                        String inn = in.substring(j * 6, (j + 1) * 6);
                        ArrayList<Pair<Integer, Integer>> re = table.search(inn);
                        if (re == null) {
                            System.out.println("(0, 0)");
                            return table;
                        }
                        while (true) {
                            if (p >= ou.size() || q >= re.size())
                                break;
                            if (ou.get(p).first() > re.get(q).first())
                                q++;
                            else if (ou.get(p).first() < re.get(q).first())
                                p++;
                            else {
                                if (ou.get(p).second() + 6 * j < re.get(q).second())
                                    p++;
                                else if (ou.get(p).second() + 6 * j > re.get(q).second())
                                    q++;
                                else {
                                    tou.add(ou.get(p));
                                    p++;
                                    q++;
                                }
                            }
                        }
                        ou = (ArrayList<Pair<Integer, Integer>>) tou.clone();
                        tou.removeAll(tou);
                    }
                }
                if (out.toString().length() == 0) {
                    System.out.println("(0, 0)");
                    return table;
                }
                System.out.println(out);
            }
        }
        return table;
    }
}
