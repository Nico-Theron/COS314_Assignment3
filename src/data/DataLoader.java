package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataLoader {

    public static Dataset loadCSV(String filepath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filepath));

        if (scanner.hasNextLine()) {// Skip header
            scanner.nextLine();
        }

        int rowCount =0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            rowCount++;
        }
        scanner.close();

        int[][] features = new int[rowCount][9];
        int[] labels = new int[rowCount];

        scanner = new Scanner(new File(filepath));

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");
            labels[row] = Integer.parseInt(parts[0].trim());

            for (int i = 1; i < parts.length; i++) {
                features[row][i - 1] = Integer.parseInt(parts[i].trim());
            }

            row++;
        }
        scanner.close();

        return new Dataset(features, labels);
    }
}