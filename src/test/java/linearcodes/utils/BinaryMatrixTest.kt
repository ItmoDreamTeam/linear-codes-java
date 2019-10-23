package linearcodes.utils

import org.junit.Test
import kotlin.test.assertEquals

class BinaryMatrixTest {

    @Test
    fun testMultiplication() {
        val a = BinaryMatrix.ofInts(listOf(
                listOf(0, 1, 1, 0),
                listOf(1, 0, 0, 0),
                listOf(0, 1, 0, 1)
        ))
        val b = BinaryMatrix.ofInts(listOf(
                listOf(0, 1),
                listOf(1, 0),
                listOf(0, 1),
                listOf(0, 0)
        ))
        assertEquals(listOf(
                listOf(1, 1),
                listOf(0, 1),
                listOf(1, 0)
        ), a.mult(b).content)
    }

    @Test
    fun testTranspose() {
        val m = BinaryMatrix.ofInts(listOf(
                listOf(0, 1, 1, 0),
                listOf(1, 0, 0, 0),
                listOf(0, 1, 0, 1)
        ))
        assertEquals(listOf(
                listOf(0, 1, 0),
                listOf(1, 0, 1),
                listOf(1, 0, 0),
                listOf(0, 0, 1)
        ), m.transpose().content)
    }
}
