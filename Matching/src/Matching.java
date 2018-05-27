import java.io.*;

public class Matching {
    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashTable table = new HashTable();
        while (true) {
            try {
                String input = br.readLine();
                if (input.compareTo("QUIT") == 0)
                    break;

                command(input, table);
            } catch (IOException e) {
                System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
            }
        }
    }

    private static void command(String input, HashTable inTable) throws IOException {
//		// TODO : 아래 문장을 삭제하고 구현해라.
//		System.out.println("<< command 함수에서 " + input + " 명령을 처리할 예정입니다 >>");
        HashTable table = inTable;
        String in = input.substring(2, input.length());
        int i = 0;
        if (input.charAt(0) == '<') {
            BufferedReader buf = new BufferedReader(new FileReader(in));
            while(true) {
                i++;
                String read = buf.readLine();
                if(read == null)
                    break;
                for(int j = 0; j < read.length() - 6; j++)
                    table.insert(read.substring(j,j+6), i, j + 1);
            }
            buf.close();
        } else if (input.charAt(0) == '@') {
            System.out.println(table.print(Integer.parseInt(in)));
        } else if (input.charAt(0) == '?') {

        }
        return;
    }
}
