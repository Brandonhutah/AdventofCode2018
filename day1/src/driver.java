import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/// Advent of Code 2018 Day 1 - Chronal Calibration
/// Author: Brandon Horlacher
/// Date: December 1, 2018
public class driver {
    public static void main(String[] args) {
        // variables to be used in solving the puzzle
        int change = 0;

        // get filepath for the puzzle input
        System.out.print("Please provide the filepath of the text document containing the puzzle input: ");
        String filePath = new Scanner(System.in).nextLine();

        try {
            // attempt to read each line in the file into a list
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            /// PART 1
            for (String line : lines) {
                switch (line.charAt(0)) {
                    case '+':
                        change += Integer.parseInt(line.substring(1));
                        break;
                    case '-':
                        change -= Integer.parseInt(line.substring(1));
                        break;
                }
            }

            System.out.println("The solution to part 1 is: " + change);

            /// PART 2
            List<Integer> triedFrequencies = new ArrayList<>();
            change = 0;
            boolean repeat = true;
            do {
                for (String line : lines) {
                    switch (line.charAt(0)) {
                        case '+':
                            change += Integer.parseInt(line.substring(1));
                            break;
                        case '-':
                            change -= Integer.parseInt(line.substring(1));
                            break;
                    }

                    if (triedFrequencies.contains(change)) {
                        repeat = false;
                        System.out.println("The solution to part 2 is: " + change);
                        break;
                    } else {
                        triedFrequencies.add(change);
                    }
                }
            } while (repeat);
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }
    }
}
