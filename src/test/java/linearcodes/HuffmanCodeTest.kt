package linearcodes

import linearcodes.HuffmanCode.EncodedSymbol
import linearcodes.HuffmanCode.Symbol
import org.junit.Test
import kotlin.test.assertEquals

class HuffmanCodeTest {

    private val huffmanCode = HuffmanCode()

    @Test
    fun test() {
        val encodedSymbols = huffmanCode.apply(setOf(
                Symbol("a", 0.50),
                Symbol("b", 0.20),
                Symbol("c", 0.15),
                Symbol("d", 0.10),
                Symbol("e", 0.05)
        ))
        assertEquals(listOf(
                EncodedSymbol("a", 0.50, "0"),
                EncodedSymbol("b", 0.20, "10"),
                EncodedSymbol("c", 0.15, "110"),
                EncodedSymbol("d", 0.10, "1111"),
                EncodedSymbol("e", 0.05, "1110")
        ), encodedSymbols)
    }
}
