import com.sun.scenario.effect.Merge;

import java.io.*;
import java.util.*;

public class SortingTest {
    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            boolean isRandom = false;    // 입력받은 배열이 난수인가 아닌가?
            int[] value;    // 입력 받을 숫자들의 배열
            String nums = br.readLine();    // 첫 줄을 입력 받음
            if (nums.charAt(0) == 'r') {
                // 난수일 경우
                isRandom = true;    // 난수임을 표시

                String[] nums_arg = nums.split(" ");

                int numsize = Integer.parseInt(nums_arg[1]);    // 총 갯수
                int rminimum = Integer.parseInt(nums_arg[2]);    // 최소값
                int rmaximum = Integer.parseInt(nums_arg[3]);    // 최대값

                Random rand = new Random();    // 난수 인스턴스를 생성한다.

                value = new int[numsize];    // 배열을 생성한다.
                for (int i = 0; i < value.length; i++)    // 각각의 배열에 난수를 생성하여 대입
                    value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
            } else {
                // 난수가 아닐 경우
                int numsize = Integer.parseInt(nums);

                value = new int[numsize];    // 배열을 생성한다.
                for (int i = 0; i < value.length; i++)    // 한줄씩 입력받아 배열원소로 대입
                    value[i] = Integer.parseInt(br.readLine());
            }

            // 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
            while (true) {
                int[] newvalue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.

                String command = br.readLine();

                long t = System.currentTimeMillis();
                switch (command.charAt(0)) {
                    case 'B':    // Bubble Sort
                        newvalue = DoBubbleSort(newvalue);
                        break;
                    case 'I':    // Insertion Sort
                        newvalue = DoInsertionSort(newvalue);
                        break;
                    case 'H':    // Heap Sort
                        newvalue = DoHeapSort(newvalue);
                        break;
                    case 'M':    // Merge Sort
                        newvalue = DoMergeSort(newvalue);
                        break;
                    case 'Q':    // Quick Sort
                        newvalue = DoQuickSort(newvalue);
                        break;
                    case 'R':    // Radix Sort
                        newvalue = DoRadixSort(newvalue);
                        break;
                    case 'X':
                        return;    // 프로그램을 종료한다.
                    default:
                        throw new IOException("잘못된 정렬 방법을 입력했습니다.");
                }
                if (isRandom) {
                    // 난수일 경우 수행시간을 출력한다.
                    System.out.println((System.currentTimeMillis() - t) + " ms");
                } else {
                    // 난수가 아닐 경우 정렬된 결과값을 출력한다.
                    for (int i = 0; i < newvalue.length; i++) {
                        System.out.println(newvalue[i]);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoBubbleSort(int[] value) {
        // TODO : Bubble Sort 를 구현하라.
        // value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
        // 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
        // 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
        // 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
        for (int i = value.length; i > 0; i--) {//i : number of not sorted element
            for (int j = 0; j < i - 1; j++) {
                if (value[j] > value[j + 1]) {
                    swap(value, j, j + 1);
                }
            }
        }
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoInsertionSort(int[] value) {
        // TODO : Insertion Sort 를 구현하라.
        for (int i = 1; i < value.length; i++) {//0 ~ i-1 : sorted
            int temp = value[i];
            for (int j = i - 1; j >= 0; j--) {//shifting
                value[j + 1] = value[j];
                if (value[j] <= temp) {
                    value[j + 1] = temp;
                    break;
                } else if (j == 0)
                    value[0] = temp;
            }
        }
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoHeapSort(int[] value) {
        //copy from lecture note
        //build initial heap
        for (int i = (value.length - 1) / 2; i >= 0; i--) {
            percolateDown(value, i, value.length);
        }
        //delete one by one
        for (int size = value.length - 1; size > 0; size--) {
            swap(value, 0, size);
            percolateDown(value, 0, size);
        }
        // TODO : Heap Sort 를 구현하라.
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoMergeSort(int[] value) {
        //modification from lecture note
        if (value.length > 1) {
            int[] a = new int[value.length / 2];
            int[] b = new int[value.length - value.length / 2];
            System.arraycopy(value, 0, a, 0, value.length / 2);
            System.arraycopy(value, value.length / 2, b, 0, value.length - value.length / 2);
            a = DoMergeSort(a);
            b = DoMergeSort(b);
            value = merge(a, b);
        }
        // TODO : Merge Sort 를 구현하라.
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoQuickSort(int[] value) {
        return QuickSort(value, 0, value.length);
    }

    private static int[] QuickSort(int[] value, int head, int tail) {
        //modification from lecture note
        if (tail > head) {
//            Queue<Integer> t = new LinkedList<>();
            int check;
            int pivot = value[head];
            int pivotIdx = head;
            for (int i = head + 1; i < tail; i++) {
                if (value[i] < pivot)
                    pivotIdx++;
            }
            swap(value, head, pivotIdx);
            check = pivotIdx + 1;
//            for (int j = pivotIdx + 1; j < tail; j++) {
//                if (value[j] < pivot)
//                    t.add(j);
//            }
            for (int i = head; i < pivotIdx; i++) {
                if (value[i] >= pivot) {
                    for (; check < tail; check++) {
                        if (value[check] < pivot) {
                            swap(value, i, check);
                            check++;
                            break;
                        }
                    }
                }
            }
            QuickSort(value, head, pivotIdx);
            QuickSort(value, pivotIdx + 1, tail);
//            int pivot = value[0];
//            int[] a = new int[value.length];
//            int[] b = new int[value.length];
//            int aidx = 0;//a's index
//            int bidx = 0;//b's index
//            for(int i = 1; i < value.length; i++){//calculate pivot index
//                if(pivot > value[i]){
//                    a[aidx] = value[i];
//                    aidx++;
//                } else {
//                    b[bidx] = value[i];
//                    bidx++;
//                }
//            }
//            int[] realA = new int[aidx];
//            int[] realB = new int[bidx];
//            System.arraycopy(a, 0, realA, 0, aidx);
//            System.arraycopy(b, 0, realB, 0, bidx);
//            DoQuickSort(realA);
//            DoQuickSort(realB);
//            System.arraycopy(realA, 0, value, 0, aidx);
//            value[aidx] = pivot;
//            System.arraycopy(realB, 0, value, aidx+1, bidx);
        }
        // TODO : Quick Sort 를 구현하라.
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoRadixSort(int[] value) {
        //modification from wikipedia
        // TODO : Radix Sort 를 구현하라.
        int[] counts = new int[19];
        int[] temp = new int[value.length];
        int check = 1;
        int index, pval;
        for (int n = 0; n < 10; n++) {//range of int is smaller than 10^10
            for (int i = 0; i < 19; i++)//initialize
                counts[i] = 0;
            pval = (int) Math.pow(10.0, (double) n);//10^n
            for (int i = 0; i < value.length; i++) {//253 -> n:0 => 3, n:1 => 5, ...
                index = (value[i] / pval) % 10;
                counts[9 + index]++; // 9 + index is for negative number
            }
            for (int i = 1; i < 19; i++)//accumulating
                counts[i] = counts[i] + counts[i - 1];
            for (int i = value.length - 1; i >= 0; i--) {
                index = (value[i] / pval) % 10;
                temp[counts[9 + index] - 1] = value[i];
                counts[9 + index]--;
            }
            System.arraycopy(temp, 0, value, 0, value.length);
        }
        return (value);
    }

    private static int[] swap(int[] v, int i, int j) {
        int temp = v[i];
        v[i] = v[j];
        v[j] = temp;
        return v;
    }

    private static void percolateDown(int[] v, int i, int n) {
        //copy from lecture note
        int child = 2 * i;//left child
        int rightChild = 2 * i + 1;//right child
        if (child < n) {
            if ((rightChild < n) && (v[child] < v[rightChild])) {
                child = rightChild; // index of larger child
            }
            if (v[i] < v[child]) {
                swap(v, i, child);
                percolateDown(v, child, n);
            }
        }
    }

    private static int[] merge(int[] a, int[] b) {
        int[] re = new int[a.length + b.length];
        int adx = 0;
        int bdx = 0;
        for (int i = 0; i < re.length; i++) {
            if (adx >= a.length) {
                re[i] = b[bdx];
                bdx++;
            } else if (bdx >= b.length) {
                re[i] = a[adx];
                adx++;
            } else {
                if (a[adx] <= b[bdx]) {
                    re[i] = a[adx];
                    adx++;
                } else {
                    re[i] = b[bdx];
                    bdx++;
                }
            }
        }
        return re;
    }
}
