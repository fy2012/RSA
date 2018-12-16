import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class RSAEncrypt {
    static BigInteger e;
    static BigInteger n;
    //static String message;
    static ArrayList<BigInteger> message = new ArrayList<>();
    static ArrayList<BigInteger> encryptedMessage = new ArrayList<>();

    public static void main(String[] args) {
        loadKey();
        loadMessage();

        for(int i = 0; i < message.size(); i++) {
            encryptedMessage.add(message.get(i).modPow(e, n));
            //System.out.println(encryptedMessage.get(i));
        }
        writeEncryptedMessage();
    }
    public static void loadKey(){
        File key = new File("pub_key.txt");
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

        //System.out.println(content);
        int eInd = content.indexOf('e');
        int nInd = content.indexOf('n');

        String eStr = content.substring(eInd + 4, nInd);
        String nStr = content.substring(nInd + 4);

        e = new BigInteger(eStr);
        n = new BigInteger(nStr);
    }
    public static void loadMessage(){
        File key = new File("test.txt");
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
        content = content.toUpperCase();

        for(int i = 0; i < content.length(); i += 3){
            String block = toNum(content.charAt(i));
            if(i+1 < content.length()){
                block = block + toNum(content.charAt(i+1));
                if(i+2 < content.length()){
                    block = block + toNum(content.charAt(i+2));
                }
                else{
                    block = block + "26";
                }
            }
            else{
                block = block + "2626";
            }
            //System.out.println(block);
            message.add(new BigInteger(block));
        }
        //System.out.println(content);
    }
    public static String toNum(char c){
        if(c < 65 || c > 90)
            return Integer.toString(26);
        else {
            int temp = c - 65;
            if(temp >= 10)
                return Integer.toString(temp);
            else
                return '0' + Integer.toString(temp);
        }
    }
    public static void writeEncryptedMessage(){
        try {
            FileWriter writer = new FileWriter("test.enc", false);
            writer.write(""+encryptedMessage.get(0));
            for(int i = 1; i < encryptedMessage.size();i++)
                writer.write("\n"+encryptedMessage.get(i));
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}