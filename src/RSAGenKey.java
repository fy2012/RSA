import java.util.Random;
import java.util.Scanner;

public class RSAGenKey {
    public static void main(String[] args) {
        System.out.println("0 - Enter key size (k)");
        System.out.println("1 - Enter p, q, and e");
        System.out.print("->");

        Scanner kb = new Scanner(System.in);

        int sel = kb.nextInt();

        int k, p, q, e;

        if(sel == 0){
            System.out.print("Enter k:");
            k = kb.nextInt();
            keyGen(k);
        }

        else if(sel == 1){
            System.out.print("Enter p:");
            p = kb.nextInt();
            System.out.print("Enter q:");
            q = kb.nextInt();
            System.out.print("Enter e:");
            e = kb.nextInt();
            keyGen(p, q, e);
        }
    }

    public static void keyGen(int k){
        int size = (int)Math.pow(2, k);

        int p, q, e;

        p = getPrime(size);
        q = getPrime(size);

        System.out.println(p);
        System.out.println(q);

        int n = p * q;
        int fn = (p-1) * (q-1);

        Random rnd = new Random(fn - 2);
        e = rnd.nextInt() + 2;

        while(n % e > 0){
            e = rnd.nextInt() + 2;
        }
        System.out.println(e);
    }

    public static void keyGen(int p, int q, int e){

    }

    public static int getPrime(int size){
        int n = 0;
        Random rnd = new Random(size);
        boolean isPrime = false;
        while(!isPrime){
            n = rnd.nextInt();
            isPrime = checkPrime(n);
        }
        return n;
    }

    public static boolean checkPrime(int num){
        boolean flag = false;
        for(int i = 2; i <= num/2; ++i){
            if(num % i == 0){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
