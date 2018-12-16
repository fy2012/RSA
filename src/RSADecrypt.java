import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class RSADecrypt {
    static BigInteger d;
    static BigInteger n;
    static ArrayList<Integer> p = new ArrayList<>();
    static ArrayList<BigInteger> message = new ArrayList<>();
    static ArrayList<BigInteger> encryptedMessage = new ArrayList<>();

    public static void main(String[] args) {
        loadKey();
        loadEncryptedMessage();

        for(int i = 0; i < encryptedMessage.size(); i ++){
            message.add(encryptedMessage.get(i).modPow(d, n));
            //System.out.println(message.get(i));
        }

        writeMessage();
    }
    public static void loadKey(){
        File key = new File("pri_key.txt");
        String line,content = "";
        try{
            FileReader reader = new FileReader(key);
            BufferedReader buffer = new BufferedReader(reader);
            while((line = buffer.readLine()) != null){
                content = content + line;
            }
            buffer.close();
        }catch (Exception ex){
            //do nothing
        }

        int eInd = content.indexOf('d');
        int nInd = content.indexOf('n');

        String eStr = content.substring(eInd + 4, nInd);
        String nStr = content.substring(nInd + 4);

        d = new BigInteger(eStr);
        n = new BigInteger(nStr);
    }
    public static void loadEncryptedMessage(){
        File key = new File("test.enc");
        String line;
        try{
            FileReader reader = new FileReader(key);
            BufferedReader buffer = new BufferedReader(reader);
            while((line = buffer.readLine()) != null){
                encryptedMessage.add(new BigInteger(line));
                //System.out.println(line);
            }
            buffer.close();
        }catch (Exception ex){
            //do nothing
        }
    }
    public static void writeMessage(){
        try {
            FileWriter writer = new FileWriter("test.dec", false);
            for(int i = 0; i < message.size(); i++){
                int temp = Integer.parseInt(message.get(i).toString())+656565;
                //System.out.println(Integer.parseInt(message.get(i).toString())+656565);
                int temp1 = temp/10000;
                p.add(temp1);
                int temp2 = (temp - temp1*10000) / 100;
                p.add(temp2);
                int temp3 = temp - temp2*100 - temp1*10000;
                p.add(temp3);
                //System.out.println("0:" + temp1);
                //System.out.println("1:" + temp2);
                //System.out.println("2:" + temp3);
            }
            for(int i = 0; i < p.size(); i++){
                if(p.get(i) == 91)
                    writer.write(" ");
                else {
                    int c = p.get(i);
                    writer.write((char)c);
                }
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /*
    public static void writeMessage(){
        try {
            FileWriter writer = new FileWriter("test.dec", false);
            for(int i = 0; i < message.size(); i ++){
                int tempInt1 = Integer.parseInt(message.get(i).toString())/10000 + 65;
                if(tempInt1 != 0) {
                    char tempChar1 = (char) tempInt1;
                    System.out.print(tempInt1);
                    writer.write(tempInt1);
                }
                else{
                    writer.write(" ");
                }

                int tempInt2 = Integer.parseInt(message.get(i).toString())/100- tempInt1 + 65 ;
                if(tempInt2 != 0) {
                    char tempChar2 = (char) tempInt2;
                    System.out.print(tempInt2);
                    writer.write(tempInt2);
                }
                else{
                    writer.write(" ");
                }

                int tempInt3 = Integer.parseInt(message.get(i).toString()) - tempInt1 - tempInt2 + 65;
                if(tempInt3 != 0) {
                    System.out.print(tempInt3);
                    char tempChar3 = (char) tempInt3;
                    writer.write(tempInt3);
                }
                else{
                    writer.write(" ");
                }
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }*/
}
