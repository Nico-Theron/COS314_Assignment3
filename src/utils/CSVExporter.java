package utils;

import java.io.*;

public class CSVExporter {
    PrintWriter writer;

    public CSVExporter(String filename) throws Exception {
        writer = new PrintWriter(filename);
        writer.println("Run,Seed,TrainAccuracy,TrainFMeasure,TestAccuracy,TestFMeasure,Runtime");
    }

    public void write(int run, long seed, double trainAcc, double trainF, double testAcc, double testF, double runtime) {
        writer.println(run + "," + seed + "," + trainAcc + "," + trainF + "," + testAcc + "," + testF + "," + runtime);
    }

    public void close() {
        writer.close();
    }
}