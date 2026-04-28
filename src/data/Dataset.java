package data;

public class Dataset {
    public int[][] features;
    public int[] labels;

    public Dataset(int[][] features, int[] labels) {
        this.features = features;
        this.labels = labels;
    }

    public int size() {
        return labels.length;
    }
}