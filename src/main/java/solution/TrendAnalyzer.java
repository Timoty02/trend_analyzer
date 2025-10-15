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
        generateRandomTrend(trend1);
        generateRandomTrend(trend2);
    }
    public void generateRandomTrend(List<Integer> trend) {
        int current = minRange + (int)(Math.random() * (maxRange - minRange));
        trend.add(current);
        int range = maxRange - minRange;
        for (int i = 1; i < count; i++) {
            double positionFactor = 1.0 - Math.abs(2.0 * (current - minRange) / range - 1.0);
            positionFactor = 0.3 + 0.7 * positionFactor; // От 30% до 100%
            double baseSmallChange = 0.03 * positionFactor;
            double baseJump = 0.2 * positionFactor;
            if (Math.random() < 0.05) {
                int jump = (int)(Math.random() * baseJump * range) + 1;
                current += (Math.random() < 0.5 ? -1 : 1) * jump;
            } else {
                int smallChange = (int)(Math.random() * baseSmallChange * range) + 1;
                current += (Math.random() < 0.5 ? -1 : 1) * smallChange;
            }
            double distanceToMin = (double)(current - minRange) / range;
            double distanceToMax = (double)(maxRange - current) / range;

            if (distanceToMin < 0.1) {
                if (Math.random() < 0.7) current += Math.max(1, range / 100);
            } else if (distanceToMax < 0.1) {
                if (Math.random() < 0.7) current -= Math.max(1, range / 100);
            }
            current = Math.max(minRange, Math.min(maxRange, current));
            trend.add(current);
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
