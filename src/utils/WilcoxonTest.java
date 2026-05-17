package utils;

import java.io.*;
import java.util.*;

public class WilcoxonTest {
        public static void run(double[] ga_results, double[] ils_results, String output_filename) throws Exception {
        int ga_length = ga_results.length;

        if (ga_length != ils_results.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }

        List<Double> differences = new ArrayList<>();

        for (int i = 0; i < ga_length; i++) {
            double diff = ga_results[i] - ils_results[i];

            // Make sure the difference is not zero
            if (diff != 0) {
                differences.add(diff);
            }
        }

        int difference_size = differences.size();
        if (difference_size == 0) {
            exportResults(output_filename, 0, 0, "Failed to reject H0", difference_size);
            return;
        }


        Double[] diff_array = differences.toArray(new Double[0]);
        Arrays.sort(diff_array, (a,b) -> Double.compare(Math.abs(a), Math.abs(b)));
        Double[] abs_diff_array = Arrays.stream(diff_array).map(Math::abs).toArray(Double[]::new);

        double w_plus = 0;
        double w_minus = 0;

        // Handle duplicates
        for (int i = 0; i < difference_size; ) {

            // Place j at the end of the current group of equal differences
            int j = i;
            while (j < difference_size && abs_diff_array[j].equals(abs_diff_array[i])) {
                j++;
            }

            // sum of duplicates
            double duplicates_sum = 0;
            for (int k = i; k < j; k++) {
                duplicates_sum += (k + 1);
            }

            // We get the value for: sum of duplicates / number of duplicates
            double avg_duplicate_number = duplicates_sum / (j - i);

            for (int k = i; k < j; k++) {
                if (diff_array[k] > 0) w_plus += avg_duplicate_number;
                else w_minus += avg_duplicate_number;
            }

            // go to the next group of equal differences
            i = j;
        }

        double W = Math.min(w_plus, w_minus);

        int critical_value = getCriticalValue(difference_size);
        if (W <= critical_value) {
            exportResults(output_filename, w_plus, w_minus, "Reject H0", difference_size);
        } else {
            exportResults(output_filename, w_plus, w_minus, "Fail to Reject H0", difference_size);
        }
    }

    private static void exportResults(String filename, double w_plus, double w_minus, String conclusion, int n) throws Exception {
        PrintWriter writer = new PrintWriter(new File(filename));
        writer.println("Statistic,Value");
        writer.println("W+: " + w_plus);
        writer.println("W-: " + w_minus);
        writer.println("Test Statistic: " + Math.min(w_plus, w_minus));
        writer.println("Valid Pairs (n): " + n);
        writer.println("Critical Value: " + getCriticalValue(n));
        writer.println("Conclusion: " + conclusion);
        writer.close();
        System.out.println("Statistical analysis exported to " + filename);
    }

    private static int getCriticalValue(int n) {
        File file = new File("wilcoxon_critical_values.csv");
        if (!file.exists()) {
            System.err.println("Critical values file not found!");
            return 0;
        }

        try (Scanner sc = new Scanner(file)) {
            if (sc.hasNextLine()) sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int n_number = Integer.parseInt(parts[0].trim());
                    int critical_value = Integer.parseInt(parts[1].trim());

                    if (n_number == n) {
                        return critical_value;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading critical values: " + e.getMessage());
        }

        return 0;
    }
}