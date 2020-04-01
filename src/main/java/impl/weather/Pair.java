package impl.weather;

public class Pair {
    private final double first;
    private final int second;

    public Pair(double first, int second) {
        this.first = first;
        this.second = second;
    }

    public double getFirst() {
        return this.first;
    }

    public int getSecond() {
        return this.second;
    }
}
