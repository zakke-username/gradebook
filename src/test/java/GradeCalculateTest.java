import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GradeCalculateTest {

    private final GradeCalculate calc = new GradeCalculate();

    @Test
    void returnsCorrectAverage() {
        double result = calc.calculateAverage(List.of(80.0, 90.0, 100.0));
        assertEquals(90.0, result);
    }

    @Test
    void emptyListReturnsZero() {
        double result = calc.calculateAverage(List.of());
        assertEquals(0.0, result);
    }

    @Test
    void singleValueReturnsSameValue() {
        double result = calc.calculateAverage(List.of(75.0));
        assertEquals(75.0, result);
    }

    @Test
    void WeightedAverageBasicValuesReturnsCorrectResult() {
        double result = calc.calculateWeightedAverage(80, 90, 70);
        assertEquals(82.0, result);
    }

    @Test
    void calculateWeightedAverageWithDecimals() {
        double result = calc.calculateWeightedAverage(89.5, 91.2, 76.8);
        assertEquals(87.64, result, 0.01);
    }

    @Test
    void Below0throwsException() {
        assertThrows(IllegalArgumentException.class, () -> calc.percentageToGrade(-1));
    }

    @Test
    void invalidAbove100throwsException() {
        assertThrows(IllegalArgumentException.class, () -> calc.percentageToGrade(120));
    }

    @Test
    void percentageToGrade0To49Returns1() {
        assertEquals(0, calc.percentageToGrade(0));
        assertEquals(0, calc.percentageToGrade(39));
    }

    @Test
    void percentageToGrade50To59Returns1() {
        assertEquals(1, calc.percentageToGrade(40));
        assertEquals(1, calc.percentageToGrade(49));
    }

    @Test
    void percentageToGrade50To59Returns2() {
        assertEquals(2, calc.percentageToGrade(50));
        assertEquals(2, calc.percentageToGrade(59));
    }

    @Test
    void percentageToGrade60To69Returns3() {
        assertEquals(3, calc.percentageToGrade(60));
        assertEquals(3, calc.percentageToGrade(69));
    }

    @Test
    void percentageToGrade_70To79_returns4() {
        assertEquals(4, calc.percentageToGrade(70));
        assertEquals(4, calc.percentageToGrade(79));
    }

    @Test
    void percentageToGrade_80To100_returns5() {
        assertEquals(5, calc.percentageToGrade(80));
        assertEquals(5, calc.percentageToGrade(90));
        assertEquals(5, calc.percentageToGrade(100));
    }
}
