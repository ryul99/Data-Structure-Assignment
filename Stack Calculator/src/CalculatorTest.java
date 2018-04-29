import java.io.*;
import java.util.*;

public class CalculatorTest {
    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String input = br.readLine();
                if (input.compareTo("q") == 0)
                    break;

                command(input);
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }
    }

    private static void command(String input) throws RuntimeException {
        ArrayList<String> postfix = new ArrayList<>();
        Stack<Character> oper = new Stack<>();
        Stack<Long> cal = new Stack<>();
        int check = 0;//check previous element is operator or ~ or operand or ( or )// 1, 2, 3, 4, 5
        String inte = "";

        input = "(" + input + ")";//adding parentheses both sides to easily combine operands

        //checking parentheses matches
        int idx = 0;
        int checkParen = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                checkParen++;
                idx = i;
            }
            if (input.charAt(i) == ')') {
                checkParen--;
                if (idx + 1 == i)
                    throw new RuntimeException();
            }
        }
        if (checkParen != 0)
            throw new RuntimeException();

        //infix to postfix
        for (int i = 0; i < input.length(); i++) {
            char in = input.charAt(i);
            if ('0' <= in && in <= '9') {//if operand
                if (check == 5 || check == 3)
                    throw new RuntimeException();
                inte = inte + in;
            } else { // not number
                if (checkPriority(in) == -1)
                    throw new RuntimeException();
                if (inte.length() != 0) {//make operand
                    postfix.add(inte);
                    inte = "";
                    check = 3;
                }

                //check formula is right and determine unary - and binary -
                if (check == 4 || check == 1) {
                    if (in == '-')
                        in = '~';
                    else if (checkPriority(in) == 1 || checkPriority(in) == 2 || checkPriority(in) == 4 || in == ')')
                        throw new RuntimeException();
                } else if (check == 2) {
                    if (in == '-')
                        in = '~';
                    else if (checkPriority(in) == 1 || checkPriority(in) == 2 || checkPriority(in) == 4)
                        throw new RuntimeException();
                } else if (check == 3) {
                    if (in == '(')
                        throw new RuntimeException();
                } else if (check == 5)
                    if (in == '(')
                        throw new RuntimeException();
                //if oper stack is empty or element has higher priority
                //(at same priority, right associative has higher priority than left one. if they have have priority and associative,
                // left act like top is higher and right act like top is lower) than top of oper stack
                // or top of stack is (
                //or element is (, just push
                //when element is ), pop stack and add result until remove (
                //if element has lower priority than top of oper stack, pop oper and record it. then retest top and element
                if (in != ' ' && in != '\t') {
                    if (in == ')') {
                        while (oper.peek() != '(')
                            postfix.add("" + oper.pop());
                        oper.pop();
                    }
                    else if (oper.empty() || oper.peek() == '(')
                        oper.push(in);
                    else if (in == '(')
                        oper.push(in);
                     else {
                        while (true) {
                            if (checkPriority(in) > checkPriority(oper.peek())) {
                                oper.push(in);
                                break;
                            } else if (checkPriority(in) > checkPriority(oper.peek())) {
                                postfix.add("" + oper.pop());
                                continue;
                            } else {
                                if (checkPriority(in) == 4 || checkPriority(in) == 3) {
                                    oper.push(in);
                                    break;
                                } else {
                                    postfix.add("" + oper.pop());
                                    continue;
                                }
                            }
                        }
                    }
                }
                if (in == '~')
                    check = 2;
                else if (0 < checkPriority(in) && checkPriority(in) < 5)
                    check = 1;
                else if (in == '(')
                    check = 4;
                else if (in == ')')
                    check = 5;
            }
        }
        while (!oper.empty())
            postfix.add("" + oper.pop());

        //calculate
        for(int i = 0; i < postfix.size(); i++){
            String in = postfix.get(i);
            char ch = in.charAt(0);
            long operand2 = 0;
            long operand1 = 0;
            if(in.length()>1 || ('0'<=ch && ch<='9')) {// number
                cal.push(Long.parseLong(in));
            }
            else{ // operator
                long result = 0;
                if(ch == '^') {
                    operand2 = cal.pop();
                    operand1 = cal.pop();
                    if(operand1 == 0 && operand2 < 0)
                        throw new RuntimeException();
                    result = (long) Math.pow(operand1, operand2);
                }
                else if( ch == '~') {
                    operand1 = cal.pop();
                    result = -operand1;
                }
                else if( ch == '*'){
                    operand2 = cal.pop();
                    operand1 = cal.pop();
                    result = operand1 * operand2;
                }
                else if( ch == '/'){
                    operand2 = cal.pop();
                    operand1 = cal.pop();
                    result = operand1 / operand2;
                }
                else if(ch == '%'){
                    operand2 = cal.pop();
                    operand1 = cal.pop();
                    result = operand1 % operand2;
                }
                else if( ch == '+'){
                    operand2 = cal.pop();
                    operand1 = cal.pop();
                    result = operand1 + operand2;
                }
                else if( ch == '-'){
                    operand2 = cal.pop();
                    operand1 = cal.pop();
                    result = operand1 - operand2;
                }

                cal.push(result);
            }
        }

        if( cal.empty())
            throw new RuntimeException();
        long re = cal.pop();
        if(!cal.empty())
            throw new RuntimeException();

        print(postfix);
        System.out.println();
        System.out.println(re);
    }

    private static int checkPriority(char a) {
        if (a == '^')
            return 4;
        else if (a == '~')
            return 3;
        else if (a == '*' || a == '/' || a == '%')//they are all left associative
            return 2;
        else if (a == '+' || a == '-')//they are all left associative
            return 1;
        else if (a == '(')//controlling exception
            return 0;
        else if (a == ')')//controlling exception
            return 5;
        else if (a == ' ')
            return 6;
        else if (a == '\t')
            return 7;
        else return -1;//controlling exception
    }

    private static void print(ArrayList<String> in) {
        for (int i = 0; i < in.size(); i++) {
            if (i == in.size() - 1)
                System.out.print(in.get(i));
            else {
                System.out.print(in.get(i) + " ");
            }
        }
    }
}
