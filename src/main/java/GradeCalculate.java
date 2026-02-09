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

    public double calculateWeightedAverage(double assignment, double exam, double project) {
        return (assignment * 0.4) + (exam * 0.4) + (project * 0.2);
    }
}
