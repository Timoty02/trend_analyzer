package solution;

import java.util.ArrayList;
import java.util.List;

public class TrendAnalyzer {
    private final List<Integer> trend1 = new ArrayList<>();
    private final List<Integer> trend2 = new ArrayList<>();
    private int n;
    private int minRange;
    private int maxRange;
    private int count;

    public TrendAnalyzer() {
        this.n = 0;
        this.minRange = 800;
        this.maxRange = 850;
        this.count = 1000;
        generateTrends();
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }
    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }
    public void setN(int n) {
        this.n = n;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void generateTrends() {
        trend1.clear();
        trend2.clear();
        for (int i = 0; i < count; i++) {
            trend1.add((int) (Math.random() * (maxRange - minRange + 1)) + minRange);
            trend2.add((int) (Math.random() * (maxRange - minRange + 1)) + minRange);
        }
    }
    public List<Integer> getTrend1() {
        return trend1;
    }
    public List<Integer> getTrend2() {
        return trend2;
    }
    public int getN() {
        return n;
    }
    public int getMinRange() {
        return minRange;
    }
    public int getMaxRange() {
        return maxRange;
    }
    public int getCount() {
        return count;
    }
    public int analyzeTrend1() {
        int rez = 0;
        for (int i = 0; i < count-1; i++) {
            if (Math.abs(trend1.get(i) - trend1.get(i+1)) > n) {
                break;
            } else {
                rez++;
            }
        }
        return rez==count-1 ? -1 : rez;
    }

    public int analyzeTrend2() {
        int rez = 0;
        for (int i = 0; i < count-1; i++) {
            if (Math.abs(trend2.get(i) - trend2.get(i+1)) > n) {
                break;
            } else {
                rez++;
            }
        }
        return rez==count-1 ? -1 : rez;
    }

}
