import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.asin

class TaylorSeriesTest {

    @ParameterizedTest
    @CsvSource(
        "-0.5, 0.00000001",
        "0.5, 0.00000001",
        "0.2, 0.00000001",
        "0.0, 0.00000001"
    )
    fun testComputation_ValuesWithinBounds(x: Double, eps: Double) {
        assertEquals(asin(x), taylorseries.asin(x, eps), eps)
    }

    @ParameterizedTest
    @CsvSource(
        "1.1, 0.00000001",
        "-2.0, 0.00000001"
    )
    fun testComputation_ValuesOutOfBounds(x: Double, eps: Double) {
        assertEquals(asin(x), taylorseries.asin(x, eps))
    }

    @ParameterizedTest
    @CsvSource(
        "1.0, 0.00000001, 0.07",
        "-1.0, 0.00000001, 0.07"
    )
    fun testComputation_ValuesOnBounds(x: Double, eps: Double, delta: Double) {
        /*
        * impossible to calculate any closer than delta=0.07 on this values
        * */
        assertEquals(asin(x), taylorseries.asin(x, eps), delta)

    }
}
