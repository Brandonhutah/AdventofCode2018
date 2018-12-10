import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class driver {
    public static void main(String[] args) {
        // variables to be used in solving the puzzle
        Map<Point, Integer> fabricSquares = new HashMap<>();
        Integer overlappingSquares = 0;

        // get filepath for the puzzle input
        System.out.print("Please provide the filepath of the text document containing the puzzle input: ");
        String filePath = new Scanner(System.in).nextLine();

        try {
            // attempt to read each line in the file into a list
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            /// PART 1
            for (String line : lines) {
                // every line is in the format #1 @ 565,109: 14x24
                int x = 0, y = 0, width = 0, height = 0;
                // split line in parts to get various pieces
                String[] parts = line.split(" ");

                // get the starting x and y
                String[] coords = parts[2].split(",");
                x = Integer.parseInt(coords[0]);
                y = Integer.parseInt(coords[1].substring(0, coords[1].length() - 1));

                String[] size = parts[3].split("x");
                width = Integer.parseInt(size[0]);
                height = Integer.parseInt(size[1]);

                // now that we have the various specs, loop through and determine coords for each square inch
                for (int i = x; i < (x + width); i++) {
                    for (int j = y; j < (y + height); j++) {
                        Point point = new Point(i, j);
                        // add point to dict if it doesn't exist, increment counter otherwise
                        if (fabricSquares.containsKey(point)) {
                            fabricSquares.put(point, fabricSquares.get(point) + 1);
                        } else {
                            fabricSquares.put(point, 1);
                        }
                    }
                }
            }
            // count how many square inches are used more than ones
            for (Integer count : fabricSquares.values()) {
                if (count > 1) {
                    overlappingSquares++;
                }
            }

            System.out.println("The solution to part 1 is: " + overlappingSquares);

            /// PART 2
            Map<Point, List<String>> patternMap = new HashMap<>();
            Map<String, Integer> patternOverlapCount = new HashMap<>();
            for (String line : lines) {
                // every line is in the format #1 @ 565,109: 14x24
                int x = 0, y = 0, width = 0, height = 0;
                String id = "";
                // split line in parts to get various pieces
                String[] parts = line.split(" ");

                // get the starting x and y
                String[] coords = parts[2].split(",");
                x = Integer.parseInt(coords[0]);
                y = Integer.parseInt(coords[1].substring(0, coords[1].length() - 1));
                id = parts[0];

                String[] size = parts[3].split("x");
                width = Integer.parseInt(size[0]);
                height = Integer.parseInt(size[1]);

                // now that we have the various specs, loop through and determine coords for each square inch
                for (int i = x; i < (x + width); i++) {
                    for (int j = y; j < (y + height); j++) {
                        Point point = new Point(i, j);
                        // add point to dict if it doesn't exist, increment counter otherwise
                        if (patternMap.containsKey(point)) {
                            if (!patternMap.get(point).contains(id)) {
                                patternMap.get(point).add(id);
                            }
                        } else {
                            patternMap.put(point, new ArrayList<>());
                            patternMap.get(point).add(id);
                        }
                    }
                }
            }

            for (List<String> ids : patternMap.values()) {
                if (ids.size() > 1) {
                    for (String id : ids) {
                        if (patternOverlapCount.containsKey(id)) {
                            patternOverlapCount.put(id, patternOverlapCount.get(id) + 1);
                        }
                    }
                } else {
                    if (!patternOverlapCount.containsKey(ids.get(0))) {
                        patternOverlapCount.put(ids.get(0), 0);
                    }
                }
            }

            for (Map.Entry<String, Integer> entry : patternOverlapCount.entrySet()) {
                if (entry.getValue() == 0) {
                    System.out.println("The solution to part 2 is: " + entry.getKey());
                    return;
                }
            }
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }
    }
}
