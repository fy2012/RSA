import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSAGenKey {
    public static void main(String[] args) {
        System.out.println("0 - Enter key size (k)");
        System.out.println("1 - Enter p, q, and e");
        System.out.print("->");

        Scanner kb = new Scanner(System.in);

        int sel = kb.nextInt();
        int k;

        String p, q, e;

        if(sel == 0){
            System.out.print("Enter k:");
            k = kb.nextInt();
            keyGen(k);
        }

        else if(sel == 1){
            System.out.print("Enter p:");
            p = kb.next();
            System.out.print("Enter q:");
            q = kb.next();
            System.out.print("Enter e:");
            e = kb.next();

            //int n = p * q;
            //int fn = (p - 1) * (q - 1);
            BigInteger biP = new BigInteger(p);
            BigInteger biQ = new BigInteger(q);
            BigInteger biE = new BigInteger(e);

            BigInteger biN = biP.multiply(biQ);
            BigInteger biFn = biP.subtract(BigInteger.ONE).multiply(biQ.subtract(BigInteger.ONE));
            keyGen(biE, biN, biFn);
        }
    }

    public static void keyGen(int k){
        BigInteger p, q, e;
        SecureRandom rnd = new SecureRandom();

        do {
            p = BigInteger.probablePrime(k, rnd);
            q = BigInteger.probablePrime(k, rnd);
        }while(p.compareTo(q) > 0);

        BigInteger n = p.multiply(q);
        BigInteger fn = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        do {
            e = new BigInteger(k, rnd);
        } while ((!fn.gcd(e).equals(BigInteger.ONE)) || e.equals(BigInteger.ZERO));

        //System.out.println(p);
        //System.out.println(q);
        //System.out.println(n);
        //System.out.println(fn);
        //System.out.println(e);

        keyGen(e, n, fn);
    }

    public static void keyGen(BigInteger e, BigInteger n, BigInteger fn){
        BigInteger d = e.modInverse(fn);
        //System.out.println(d);

        try {
            FileWriter writer = new FileWriter("pub_key.txt", false);
            writer.write("e = " + e);
            writer.write("\r\n");   // write new line
            writer.write("n = " + n);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter("pri_key.txt", false);
            writer.write("d = " + d);
            writer.write("\r\n");   // write new line
            writer.write("n = " + n);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}