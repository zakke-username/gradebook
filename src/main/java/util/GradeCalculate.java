package util;

import java.util.List;

public class GradeCalculate {
    public static double weightedAverage(List<Double> values, List<Double> weights) {
        if (values.size() != weights.size()) {
            throw new IllegalArgumentException("Values and weights must be the same length");
        }
        double totalWeight = 0.0;
        double weightedSum = 0.0;
        for (int i = 0; i < values.size(); i++) {
            weightedSum += values.get(i) * weights.get(i);
            totalWeight += weights.get(i);
        }
        if (totalWeight == 0) {
            throw new ArithmeticException("Total weight cannot be zero");
        }
        return weightedSum / totalWeight;
    }
}
