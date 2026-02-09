import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GradeCalculateTest {

    @Test
    void testCalculateAverage() {
        GradeCalculate calc = new GradeCalculate();
        double result = calc.calculateAverage(List.of(80.0, 90.0, 100.0));
        assertEquals(90.0, result);
    }

    @Test
    void testCalculateAverageEmpty() {
        GradeCalculate calc = new GradeCalculate();
        double result = calc.calculateAverage(List.of());
        assertEquals(0.0, result);
    }

    @Test
    void testCalculateWeightedAverage() {
        GradeCalculate calc = new GradeCalculate();
        double result = calc.calculateWeightedAverage(80, 90, 70);
        assertEquals(82.0, result);
    }
}