import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

class CharacterFrequency {
    static String fileName;
    public static char[][] ch2D = {
            { '`', '~', '1', '!', '2', '@', '3', '#', '4', '$', '5', '%', '6', '^', '7', '&', '8', '*', '9', '(', '0',
                    ')', '-', '_', '=', '+' },
            { 'q', 'Q', 'w', 'W', 'e', 'E', 'r', 'R', 't', 'T', 'y', 'Y', 'u', 'U', 'i', 'I', 'o', 'O', 'p', 'P', '[',
                    '{', ']', '}', '\\', '|' },
            { 'a', 'A', 's', 'S', 'd', 'D', 'f', 'F', 'g', 'G', 'h', 'H', 'j', 'J', 'k', 'K', 'l', 'L', ';', ':', '\'',
                    '\"' },
            { 'z', 'Z', 'x', 'X', 'c', 'C', 'v', 'V', 'b', 'B', 'n', 'N', 'm', 'M', ',', '<', '.', '>', '/', '?' } };

    public CharacterFrequency(String fileName) {
        CharacterFrequency.fileName = fileName;
    }

    // Method to read the file and store the characters in a HashMap
    public HashMap<Character, Integer> getCharacterFrequency() throws IOException {
        HashMap<Character, Integer> characterFrequency = new HashMap<>();
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (characterFrequency.containsKey(c)) {
                    // Increment frequency of character count
                    characterFrequency.put(c, characterFrequency.get(c) + 1);
                } else {
                    // If no character count found, add character count
                    characterFrequency.put(c, 1);
                }
            }
        }
        sc.close();
        return characterFrequency;
    }

    // Returns the total amount of keys pressed
    public int totalKeysPressed() throws IOException {
        int sum = 0;
        for (int i : getCharacterFrequency().values()) {
            sum += i;
        }
        return sum;
    }

    // Returns the highest frequency
    public int getMaxUniqueFrequency() throws IOException {
        int maxFrequency = 0;
        for (int i : getCharacterFrequency().values()) {
            if (i > maxFrequency) {
                maxFrequency = i;
            }
        }
        return maxFrequency;
    }

    // Returns the lowest frequency
    public int getMinUniqueFrequency() throws IOException {
        int minFrequency = Integer.MAX_VALUE;
        for (int i : getCharacterFrequency().values()) {
            if (i < minFrequency) {
                minFrequency = i;
            }
        }
        return minFrequency;
    }

    // Returns the highest frequency of a key
    public int getMaxKeyFrequency() throws IOException {
        int maxFrequency = 0;
        for (int i = 0; i < ch2D.length; i++) {
            for (int j = 0; j < ch2D[i].length / 2; j++) {
                if (getSingleFrequency(ch2D[i][2 * j]) + getSingleFrequency(ch2D[i][2 * j + 1]) > maxFrequency) {
                    maxFrequency = getSingleFrequency(ch2D[i][2 * j]) + getSingleFrequency(ch2D[i][2 * j + 1]);
                }
            }
        }
        return maxFrequency;
    }

    // Returns the lowest frequency of a key
    public int getMinKeyFrequency() throws IOException {
        int minFrequency = Integer.MAX_VALUE;
        for (int i = 0; i < ch2D.length; i++) {
            for (int j = 0; j < ch2D[i].length / 2; j++) {
                if (getSingleFrequency(ch2D[i][2 * j]) + getSingleFrequency(ch2D[i][2 * j + 1]) < minFrequency) {
                    minFrequency = getSingleFrequency(ch2D[i][2 * j]) + getSingleFrequency(ch2D[i][2 * j + 1]);
                }
            }
        }
        return minFrequency;
    }

    // Returns the frequency of a given chacter
    public int getSingleFrequency(char c) throws IOException {
        if (getCharacterFrequency().containsKey(c)) {
            return getCharacterFrequency().get(c);
        } else {
            return 0;
        }
    }
}