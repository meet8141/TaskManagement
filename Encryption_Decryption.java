import java.util.*;
public class Encryption_Decryption {
    /*--------------------Simple Encryption method using shift cipher--------------------*/

    protected String encrypt(String message, int key) {
        int shift = key % 26;
        int numShift = key % 10;
        StringBuffer result = new StringBuffer();

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + shift) % 26 + base);
            } else if (Character.isDigit(c)) {
                c = (char) ((c - '0' + numShift) % 10 + '0');
            }
            result.append(c);
        }
        return result.toString();
    }

    /*--------------------Simple Decryption method using shift cipher--------------------*/
    protected String decrypt(String message, int key) {
        int shift = key % 26;
        int numShift = key % 10;
        StringBuffer result = new StringBuffer();
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base - shift + 26) % 26 + base);
            } else if (Character.isDigit(c)) {
                c = (char) ((c - '0' - numShift + 10) % 10 + '0');
            }
            result.append(c);
        }
        return result.toString();
    }



}