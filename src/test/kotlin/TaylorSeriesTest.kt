import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.asin

class TaylorSeriesTest {

    companion object {
        const val eps = 0.00000001
    }

    @Test
    fun testComputation_ValuesWithinBounds() {
        assertEquals(asin(-0.5), taylorseries.asin(-0.5, eps), eps)
        assertEquals(asin(0.5), taylorseries.asin(0.5, eps), eps)
        assertEquals(asin(0.2), taylorseries.asin(0.2, eps), eps)
        assertEquals(asin(0.0), taylorseries.asin(0.0, eps), eps)
    }

    @Test
    fun testComputation_ValuesOutOfBounds() {
        assertEquals(asin(1.1), taylorseries.asin(1.1, eps))
        assertEquals(asin(-2.0), taylorseries.asin(-2.0, eps))
    }

    @Test
    fun testComputation_ValuesOnBounds() {
        /*
        * impossible to calculate any closer than delta=0.07 on this values
        * */
        assertEquals(asin(1.0), taylorseries.asin(1.0, eps), 0.07)
        assertEquals(asin(-1.0), taylorseries.asin(-1.0, eps), 0.07)

    }
}
