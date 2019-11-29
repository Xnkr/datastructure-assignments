package com.cs5343;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SpellChecker {
    public static void main(String[] args) {
        try {
            Dictionary dictionary = new Dictionary();
            System.out.println("Dictionary loaded. Enter word");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("$> ");
                String word = scanner.next();
                if (dictionary.contains(word)) {
                    System.out.println("Word is in dictionary!");
                } else {
                    System.out.println("Word not found in dictionary!");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println("Exiting");
        }
    }
}

class Dictionary {
    private static final int DEFAULT_TABLE_SIZE = 53;
    private static final float THRESHOLD = 0.5f;
    private static final String DICTIONARY_FILE = "dictionary.txt";
    private float loadFactor = 0;
    private int tableSize;
    private String[] table;
    private int collisions;
    private boolean linearProbing;
    private int keys;

    Dictionary() throws IOException {
        System.out.println("Loading " + DICTIONARY_FILE);
        List<String> words = getDictWords();
        System.out.println(words);

        linearProbing = true;
        System.out.println("Hashing with Linear probing");
        initDictionary(words);
        linearProbing = false;
        System.out.println("Hashing with Quadratic probing");
        initDictionary(words);
    }

    private void initDictionary(List<String> words) {
        keys = 0;
        collisions = 0;
        table = new String[DEFAULT_TABLE_SIZE];
        tableSize = DEFAULT_TABLE_SIZE;
        loadFactor = 0f;
        for (String word : words) {
            if (loadFactor >= THRESHOLD) {
                rehash();
            }
            putVal(word);
        }
        System.out.println("\tCollisions for table size " + tableSize + ": "+ collisions);
    }

    private BufferedReader getFileReader() throws FileNotFoundException {
        File dictFile = new File(DICTIONARY_FILE);
        FileReader fileReader = new FileReader(dictFile);
        return new BufferedReader(fileReader);
    }

    private int hash(String key) {
        int h = 0;
        for (char ch : key.toCharArray()) {
            h = h * 31 + ch;
        }
        h = h % tableSize;
        if (h < 0) {
            h += tableSize;
        }
        return h;
    }

    private void putVal(String key) {
        int hash = hash(key);
        keys++;
        loadFactor = keys / (float) tableSize;
        if (table[hash] == null) {
            table[hash] = key;
            return;
        } else if (table[hash].equals(key)) {
            return;
        }
        int i = 0;
        while (table[hash] != null && !table[hash].equals(key)) {
            collisions++;
            if (linearProbing) {
                hash = linearProbing(hash);
            } else {
                i++;
                hash = quadraticProbing(hash, i);
            }
        }
        table[hash] = key;
    }

    private void rehash() {
        String[] oldTable = Arrays.copyOf(table, tableSize);
        System.out.println("\tCollisions for table size " + tableSize + ": "+ collisions);
        tableSize = nextPrime(tableSize * 2);
        System.out.println("\tLoad factor >= 0.5, Increasing table size to " + tableSize);
        table = new String[tableSize];
        keys = 0;
        collisions = 0;
        for (String word : oldTable) {
            if (word != null) {
                putVal(word);
            }
        }
    }

    private int linearProbing(int oldHash) {
        return (oldHash + 1) % tableSize;
    }

    private int quadraticProbing(int oldHash, int i) {
        return (oldHash + (int) Math.pow(i, 2)) % tableSize;
    }

    boolean contains(String key) {
        int hash = hash(key);
        if (table[hash] == null){
            return false;
        } else if (table[hash].equals(key)) {
            return true;
        }
        int i = 0;
        while (table[hash] != null && !table[hash].equals(key)) {
            i ++;
            hash = quadraticProbing(hash, i);
        }
        return table[hash] != null && table[hash].equals(key);
    }

    private List<String> getDictWords() throws IOException {
        List<String> words = new ArrayList<>();
        String line;
        BufferedReader bufferedReader = getFileReader();
        while ((line = bufferedReader.readLine()) != null) {
            words.add(line);
        }
        return words;
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;

        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;

        return true;
    }

    private static int nextPrime(int n) {
        if (n <= 0)
            n = 3;

        if (n % 2 == 0)
            n++;

        while (!isPrime(n)) {
            n += 2;
        }

        return n;
    }
}
