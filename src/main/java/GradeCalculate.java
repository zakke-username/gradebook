import java.util.List;

public class GradeCalculate {

    public double calculateAverage(List<Double> grades) {
        if (grades.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }

        return sum / grades.size();
    }

    public static double calculateWeightedAverage(List<Double> grades, List<Double> weights) {
        double weightedSum = 0;
        double totalWeight = 0;
        for (int i = 0; i < grades.size(); i++) {
            weightedSum += grades.get(i) * weights.get(i);
            totalWeight += weights.get(i);
        }

        return weightedSum / totalWeight;
    }


    public double percentageToGrade(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Only between 0 and 100");
        }

        if (percentage < 40) return 0;
        if (percentage < 50) return 1;
        if (percentage < 60) return 2;
        if (percentage < 70) return 3;
        if (percentage < 80) return 4;
        return 5;
    }
}