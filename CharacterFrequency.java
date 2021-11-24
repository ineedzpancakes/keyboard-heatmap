import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class CharacterFrequency {
    public String fileName;
    public HashMap<Character, Integer> charFreq;
    public static final char[][] ch2D = {
            { '`', '~', '1', '!', '2', '@', '3', '#', '4', '$', '5', '%', '6', '^', '7', '&', '8', '*', '9', '(', '0',
                    ')', '-', '_', '=', '+' },
            { 'q', 'Q', 'w', 'W', 'e', 'E', 'r', 'R', 't', 'T', 'y', 'Y', 'u', 'U', 'i', 'I', 'o', 'O', 'p', 'P', '[',
                    '{', ']', '}', '\\', '|' },
            { 'a', 'A', 's', 'S', 'd', 'D', 'f', 'F', 'g', 'G', 'h', 'H', 'j', 'J', 'k', 'K', 'l', 'L', ';', ':', '\'',
                    '\"' },
            { 'z', 'Z', 'x', 'X', 'c', 'C', 'v', 'V', 'b', 'B', 'n', 'N', 'm', 'M', ',', '<', '.', '>', '/', '?' } };

    public CharacterFrequency(String fileName) {
        this.fileName = fileName;
        try {
            charFreq = getCharacterFrequency();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Character, Integer> getCharacterFrequency() throws IOException {
        HashMap<Character, Integer> charFreq = new HashMap<Character, Integer>();
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (charFreq.containsKey(c)) {
                    // Increment frequency of character count
                    charFreq.put(c, charFreq.get(c) + 1);
                } else {
                    // If no character count found, add character count
                    charFreq.put(c, 1);
                }
            }
        }
        scanner.close();
        return charFreq;
    }

    public int getMaxFrequency() {
        int freq;
        int max = 0;
        for (int i = 0; i < ch2D.length; i++) {
            for (int j = 0; j < ch2D[i].length / 2; j++) {
                freq = 0;
                if (charFreq.containsKey(ch2D[i][j * 2])) {
                    freq += charFreq.get(ch2D[i][j * 2]);
                }
                if (charFreq.containsKey(ch2D[i][j * 2 + 1])) {
                    freq += charFreq.get(ch2D[i][j * 2 + 1]);
                }
                if (freq > max) {
                    max = freq;
                }
            }
        }
        return max;
    }

    public int getMinFrequency() {
        int freq;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < ch2D.length; i++) {
            for (int j = 0; j < ch2D[i].length / 2; j++) {
                freq = 0;
                if (charFreq.containsKey(ch2D[i][j * 2])) {
                    freq += charFreq.get(ch2D[i][j * 2]);
                }
                if (charFreq.containsKey(ch2D[i][j * 2 + 1])) {
                    freq += charFreq.get(ch2D[i][j * 2 + 1]);
                }
                if (freq < min) {
                    min = freq;
                }
            }
        }
        return min;
    }

    public int getKeyFrequency(char index1, char index2) {
        int freq = 0;
        if (charFreq.containsKey(index1)) {
            freq += charFreq.get(index1);
        }
        if (charFreq.containsKey(index2)) {
            freq += charFreq.get(index2);
        }
        return freq;
    }

}