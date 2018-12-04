import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/// Advent of Code 2018 Day 2 - Inventory Management System
/// Author: Brandon Horlacher
/// Date: December 3, 2018
public class driver {
    public static void main(String[] args) {
        // variables to be used in solving the puzzle
        Map<Character, Integer> charCount;
        int doubleOccurrences = 0, tripleOccurrences = 0;

        // get filepath for the puzzle input
        System.out.print("Please provide the filepath of the text document containing the puzzle input: ");
        String filePath = new Scanner(System.in).nextLine();

        try {
            // attempt to read each line in the file into a list
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            /// PART 1
            for (String line : lines) {
                // reset dictionary for each word
                charCount = new HashMap<>();
                // split word into characters and count them
                char[] word = line.toCharArray();
                for (char c : word) {
                    // if char has already been seen, increment counter. Otherwise add it
                    if (charCount.containsKey(c)) {
                        charCount.put(c, charCount.get(c) + 1);
                    } else {
                        charCount.put(c, 1);
                    }
                }

                // determine if it contains a triple or double occurrence, make sure only 1 of each is counted
                boolean doub = false, triple = false;
                for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
                    if (!doub && entry.getValue() == 2) {
                        doubleOccurrences++;
                        doub = true;
                    }
                    if (!triple && entry.getValue() == 3) {
                        tripleOccurrences++;
                        triple = true;
                    }

                    // short circuit in case we match both cases early
                    if (doub && triple) {
                        break;
                    }
                }
            }

            System.out.println("The solution to part 1 is: " + (doubleOccurrences * tripleOccurrences));

            /// PART 2
            // nested loops to compare each word to each other word. Using for to get access to index
            // this prevents comparing a word against itself
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.size(); j++) {
                    // skip comparing a word against itself
                    if (i == j) {
                        continue;
                    }
                    int diff = 0;
                    char[] word1 = lines.get(i).toCharArray();
                    char[] word2 = lines.get(j).toCharArray();
                    for (int x = 0; x < word1.length; x++) {
                        if (word1[x] != word2[x]) {
                            diff++;
                        }
                    }

                    // words have exactly one letter difference in the same spot, they're the answer
                    String answer = "";
                    if (diff == 1) {
                        // build string that lists the common letters between the words
                        for (int x = 0; x < word1.length; x++) {
                            if (word1[x] == word2[x]) {
                                answer += word1[x];
                            }
                        }

                        System.out.println("The solution to part 2 is: " + answer);
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }
    }
}
