import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
 
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");

    int sign = 0;// 0 == + , 1 == -
    int[] a = new int[50];
    //each element of array has 4 digits
    //4 digits * 50 = 200 digits
    public BigInteger(){
        this("");
    }
//    public BigInteger(int i)//i is always positive
//    {
//        Arrays.fill(a,0);
//        int index = 0;
//        do{
//            a[index] = i % 10000;
//            i = i / 10000;
//            index++;
//        }while(i % 10000 > 0);
//    }
//
//    public BigInteger(int[] num1)//num1 array follows rules
//    {
//        for(int i = 0; i < 50; i++)
//            a[i] = num1[i];
//    }

    public BigInteger(String s)
    {
        Arrays.fill(a,0);
        if(s.length() != 0) {
            if (s.charAt(0) == '-') {
                sign = 1;
                s = s.substring(1, s.length());
            }
            for (int i = 0; i <= (s.length() - 1 ) / 4; i++)
                a[i] = Integer.parseInt(s.substring((s.length() - i*4 - 4) > 0 ? s.length() - i*4 - 4 : 0, s.length() - i*4));
        }
    }
 
    public BigInteger add(BigInteger big) {
        BigInteger ret = new BigInteger();
        for (int q = 0; q < 2; q++) {
            int c = 0, curr = 0, bigI = 0;//c is counting up number / if 0 + 0 = 0, digit(bigI) is 0
            for (int i = 0; i <= 25; i++) {//i == 25 is for counting up number
                if (sign == 1)//-(900-50+1) = -900 + 50 -1
                    a[i] *= -1;
                if (big.sign == 1)
                    big.a[i] *= -1;
                if (c > 0) {//ret.sign = last c's sign
                    ret.sign = 0;
                    bigI = i; // recording digit
                } else if (c < 0) {
                    ret.sign = 1;
                    bigI = i;
                }

                if (i < 25) {
                    curr = a[i] + big.a[i] + c;
                    c = curr / 10000;
                    curr -= c * 10000;
                    ret.a[i] = curr;
                    if (ret.a[i] > 0) {
                        ret.sign = 0;
                        bigI = i;
                    } else if (ret.a[i] < 0) {
                        ret.sign = 1;
                        bigI = i;
                    }
                }
            }
                for (int i = 0; i <= bigI; i++) {
                    if (ret.sign == 1)
                        ret.a[i] *= -1;//-900 + 50 -1 = -(900-50+1)
                    if(q == 0) {
                        if (i < bigI)
                            ret.a[i] += 9999;// + 9999....(n)
                        else
                            ret.a[i] -= 1;//  - 10^n
                    }
                }
            if (q == 0) {
                ret.a[0] += 1;// + 1
                big = ret;
                Arrays.fill(a, 0);
                sign = 0;
            }
        }//looping for + 1's counting up
        return ret;
    }
 
    public BigInteger subtract(BigInteger big)
    {
        big.sign = big.sign^1;
        return add(big);
    }
 
    public BigInteger multiply(BigInteger big)
    {
        BigInteger ret = new BigInteger();
        ret.sign = big.sign^sign;
        int c = 0, curr = 0;//c is counting up number
        for (int i = 0; i <= 49; i++) {//i == 49 is for counting up
            ret.a[i] = c;
            if(i < 49) {
                c = 0;
                curr = 0;
                for (int j = 0; j <= i; j++) {
                    curr = (j > 24 ? 0 : a[j]) * ((i - j) > 24 ? 0 : big.a[i - j]);//overflow doesn't occur. 10e9 > 8 digits
                    c += curr / 10000;
                    curr -= curr / 10000 * 10000;
                    ret.a[i] += curr;//10e9 > 8digits > 4digits * 50
                }
                c += ret.a[i] / 10000;
                ret.a[i] -= ret.a[i] / 10000 * 10000;
            }
        }
        return ret;
    }
 
    @Override
    public String toString()
    {
        int i = 49;
        String s = "";
        for (;i >= 0 && a[i] == 0;i--);//0000002400 -> 2400
        if(i == -1)
            s = "0";
        else {
            if(sign == 1)
                s = s.concat("-");
            for (int j = i; j >= 0; j--) {//printing 2400.
                String str = "" + a[j];
                if (i > j) {
                    for (int q = 0; q < 4 - str.length(); q++)
                        s = s.concat("0");
                }
                s = s.concat(str);
            }
        }
        return s;
    }
 
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        int oper = 1;//1 2 3 == + - * if - or * is showed, then change
        int task = 0;// {start ,a sign, number a, operator, b sign, number b}
        String a = "";
        String b = "";
        BigInteger ret;
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i)!=' '){
                if(input.charAt(i) == '-'){
                    if(task == 0){
                        a += "-";
                        task++;
                    }
                    else if(task == 2){
                        oper = 2;
                        task++;
                    }
                    else if(task == 3){
                        b += "-";
                        task++;
                    }
                }
                else if(input.charAt(i) == '+'){
                    if(task == 0)
                        task++;
                    else if(task == 2) {
                        oper = 1;
                        task++;
                    }
                }
                else if(input.charAt(i) == '*'){
                    if(task == 2){
                        oper = 3;
                        task++;
                    }
                }
                else if('0'<=input.charAt(i) && '9'>=input.charAt(i)){
                    if(task == 1 || task == 0 || task == 2) {
                        a = a.concat(""+input.charAt(i));
                        task = 2;
                    }
                    else if(task == 3 || task == 4 || task == 5) {
                        b = b.concat("" + input.charAt(i));
                        task = 5;
                    }
                }
            }
        }
        ret = new BigInteger(a);
        if(oper == 1)
            return ret.add(new BigInteger(b));
        else if(oper == 2)
            return ret.subtract(new BigInteger(b));
        else
            return ret.multiply(new BigInteger(b));
        // implement here
        // parse input
        // using regex is allowed

        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;
    }
 
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
 
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
 
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
 
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
 
            return false;
        }
    }
 
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
