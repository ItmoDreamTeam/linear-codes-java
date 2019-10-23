package linearcodes

import linearcodes.utils.Utils
import java.util.*
import java.util.stream.IntStream.range
import kotlin.collections.ArrayList
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.streams.toList

/**
 * [Wiki](https://en.wikipedia.org/wiki/Hamming_code)
 * [Simple Explanation](https://www.youtube.com/watch?v=373FUw-2U2k)
 */
class HammingCode : BlockCode {

    private val parityBitsIndices = range(0, 128).map { 2.0.pow(it).roundToInt() }.toList().toSet()

    override fun encodeBlock(input: String): String {
        val inputDigits = Utils.stringToList(input)
        val outputDigits = fillInParityBits(insertEmptyParityBits(inputDigits))
        return Utils.listToString(outputDigits)
    }

    private fun insertEmptyParityBits(inputDigits: List<Byte>): List<Byte> {
        val inputQueue = LinkedList<Byte>(inputDigits)
        val result = ArrayList<Byte>()
        var index = 1
        while (inputQueue.isNotEmpty()) {
            result.add(if (index++ in parityBitsIndices) 0 else inputQueue.pop())
        }
        return result
    }

    private fun fillInParityBits(block: List<Byte>): List<Byte> {
        val result = ArrayList<Byte>()
        for (index in block.indices) {
            result.add(if ((index + 1) in parityBitsIndices) computeParityBit(block, index) else block[index])
        }
        return result
    }

    private fun computeParityBit(block: List<Byte>, targetIndex: Int): Byte {
        var onesCount = 0
        var i = targetIndex
        while (i < block.size) {
            onesCount += block.subList(i, min(i + targetIndex + 1, block.size)).count { it.toInt() == 1 }
            i += 2 * (targetIndex + 1)
        }
        return (onesCount % 2).toByte()
    }

    override fun decodeBlock(input: String): String {
        val inputDigits = Utils.stringToList(input)
        val correctedDigits = fixBlock(inputDigits, getIncorrectParityBitsIndices(inputDigits))
        val outputDigits = extractInformationBits(correctedDigits)
        return Utils.listToString(outputDigits)
    }

    private fun getIncorrectParityBitsIndices(block: List<Byte>): List<Int> {
        val result = ArrayList<Int>()
        for (index in block.indices) {
            if ((index + 1) in parityBitsIndices && 1 == computeParityBit(block, index).toInt()) {
                result.add(index + 1)
            }
        }
        return result
    }

    private fun fixBlock(block: List<Byte>, incorrectParityBitsIndices: List<Int>): List<Byte> {
        if (incorrectParityBitsIndices.isEmpty()) {
            println("No errors detected")
            return block
        }
        val errorBitIndex = incorrectParityBitsIndices.sum()
        println("Detected error in bit #$errorBitIndex (indexing from 1)")
        val result = ArrayList<Byte>(block)
        result[errorBitIndex - 1] = if (result[errorBitIndex - 1].toInt() == 0) 1 else 0
        return result
    }

    private fun extractInformationBits(block: List<Byte>): List<Byte> {
        val result = ArrayList<Byte>()
        for (index in block.indices) {
            if ((index + 1) !in parityBitsIndices) {
                result.add(block[index])
            }
        }
        return result
    }
}
