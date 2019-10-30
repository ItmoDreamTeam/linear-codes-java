package linearcodes

import linearcodes.HuffmanCode.EncodedSymbol
import kotlin.math.log2

class HuffmanCodeModel(private val codeTable: List<EncodedSymbol>) : BlockCode {

    fun entropy(): Double {
        return -codeTable.map { it.frequency }.map { it * log2(it) }.sum()
    }

    fun meanCodewordLength(): Double {
        return codeTable.map { it.frequency * it.codeword.length }.sum()
    }

    override fun encodeBlock(input: String): String {
        return input
                .map { symbol -> codeTable.find { it.value == symbol.toString() }!!.codeword }
                .joinToString(separator = "")
    }

    override fun decodeBlock(input: String): String {
        val result = ArrayList<String>()
        var prefix = ""
        for (symbol in input) {
            prefix += symbol
            val match = codeTable.find { it.codeword == prefix }?.value
            if (match != null) {
                prefix = ""
                result.add(match)
            }
        }
        return result.joinToString(separator = "")
    }
}
