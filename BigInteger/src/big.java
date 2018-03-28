public class big {
    public static void  main(String[] args){
        BigInteger a = new BigInteger("10");
        BigInteger b = new BigInteger("200000000000000000");
        String c = ""+0;
        a = BigInteger.evaluate("10000000000000000+200000000000000000");
        System.out.print(a);
    }
}
