import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import subject.Dummy

class DummyTest {
    companion object {
        val dummy = Dummy("Arthur", intArrayOf(1, 2, 3, 4, 5, 6))
    }

    @Test
    fun testOrder() {
        val res = dummy.act()
        for (i in res.second?.indices!!) {
            assertEquals(res.second!![i], intArrayOf(1, 2, 3, 4, 5, 6)[i])
        }
    }

}