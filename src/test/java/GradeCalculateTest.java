import org.junit.jupiter.api.Test;
import util.GradeCalculate;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GradeCalculateTest {

    private final GradeCalculate calc = new GradeCalculate();

    @Test
    void testWeightedAverageWithCustomWeights() {
        assertEquals(4.0, GradeCalculate.weightedAverage(
                List.of(5.0, 3.0, 4.0),
                List.of(40.0, 40.0, 20.0)
        ));
    }
    @Test
    void testWeightedAverageEqualWeights() {
        assertEquals(3.0, GradeCalculate.weightedAverage(
                List.of(2.0, 3.0, 4.0),
                List.of(1.0, 1.0, 1.0)
        ));
    }

    @Test
    void testSingleValue() {
        assertEquals(5.0, GradeCalculate.weightedAverage(
                List.of(5.0),
                List.of(10.0)
        ));
    }

    @Test
    void testDecimalWeights() {
        assertEquals(3.5, GradeCalculate.weightedAverage(
                List.of(3.0, 4.0),
                List.of(0.5, 0.5)
        ));
    }

    @Test
    void testMismatchedListSizes() {
        assertThrows(IllegalArgumentException.class, () ->
                GradeCalculate.weightedAverage(
                        List.of(3.0, 4.0),
                        List.of(50.0)
                )
        );
    }

    @Test
    void testZeroTotalWeight() {
        assertThrows(ArithmeticException.class, () ->
                GradeCalculate.weightedAverage(
                        List.of(3.0, 4.0),
                        List.of(0.0, 0.0)
                )
        );
    }

    @Test
    void testNegativeWeights() {
        assertEquals(2.0, GradeCalculate.weightedAverage(
                List.of(1.0, 3.0),
                List.of(-1.0, -1.0)
        ));
    }
}