import btree.BTree
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class BTreeTest {
    companion object {
        val tree = BTree(3)
    }

    @Before
    fun init() {
        tree.insert(10)
        tree.insert(20)
        tree.insert(5)
        tree.insert(6)
        tree.insert(12)
        tree.insert(30)
        tree.insert(7)
        tree.insert(17)
    }

    @Test
    fun testInsertion() {
        tree.insert(60)
        assertTrue(tree.contain(60))
    }

    @Test
    fun testDeletion() {
        tree.remove(60)
        assertFalse(tree.contain(60))
    }
}