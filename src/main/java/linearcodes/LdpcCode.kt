package linearcodes

import linearcodes.utils.BinaryMatrix
import linearcodes.utils.Utils

/**
 * ([n], [k]) LDPC code
 * [Article](https://shodhganga.inflibnet.ac.in/bitstream/10603/18696/10/10_chapter%203.pdf)
 */
class LdpcCode : BlockCode {

    private val checkMatrix = BinaryMatrix.ofInts(listOf(
            listOf(1, 1, 0, 1, 0, 1, 0, 0, 1, 0),
            listOf(0, 1, 1, 0, 1, 0, 1, 1, 0, 0),
            listOf(1, 0, 0, 0, 1, 1, 0, 0, 1, 1),
            listOf(0, 1, 1, 1, 0, 1, 1, 0, 0, 0),
            listOf(1, 0, 1, 0, 1, 0, 0, 1, 0, 1),
            listOf(0, 0, 0, 1, 0, 0, 1, 1, 1, 1)
    ))

    private val generatorMatrix = BinaryMatrix.ofInts(listOf(
            listOf(1, 0, 0, 1, 1, 0, 1, 0, 0, 0),
            listOf(0, 0, 0, 1, 1, 1, 0, 1, 0, 0),
            listOf(0, 0, 1, 1, 1, 0, 0, 0, 1, 0),
            listOf(0, 1, 0, 1, 1, 0, 0, 0, 0, 1)
    ))

    private val k = generatorMatrix.height
    private val n = generatorMatrix.width
    private val r = n - k

    override fun encodeBlock(input: String): String {
        require(input.length == k) { "Input block size must be $k" }
        val inputDigits = Utils.stringToList(input)
        val outputDigits = BinaryMatrix.ofBytes(listOf(inputDigits)).mult(generatorMatrix).getAsBytesVector()
        return Utils.listToString(outputDigits)
    }

    override fun decodeBlock(input: String): String {
        require(input.length == n) { "Encoded block size must be $n" }
        val inputDigits = Utils.stringToList(input)
        val syndrome = BinaryMatrix.ofBytes(listOf(inputDigits)).mult(checkMatrix.transpose())
        val outputDigits = if (syndrome.isZero()) inputDigits else fixBlock(inputDigits)
        return Utils.listToString(outputDigits.subList(n - k, n))
    }

    private fun fixBlock(block: List<Byte>): List<Byte> {
        val fixingBlock = block.toMutableList()
        for (i in 0..100) {
            val syndrome = BinaryMatrix.ofBytes(listOf(fixingBlock)).mult(checkMatrix.transpose())
            if (syndrome.isZero()) return fixingBlock
            val incorrectBitIndex = computeIndexOfMostLikelyIncorrectBit(fixingBlock)
            fixingBlock[incorrectBitIndex] = if (fixingBlock[incorrectBitIndex].toInt() == 0) 1 else 0
        }
        throw Exception("Received block contains erasures")
    }

    private fun computeIndexOfMostLikelyIncorrectBit(input: List<Byte>): Int {
        val failedChecks = IntArray(n)
        for (checkBitIndex in 0 until r) {
            val mask = checkMatrix.content[checkBitIndex].toIntArray()
            val parity = mask.zip(input, Int::times).sum() % 2
            if (parity != 0) {
                val controlledBitsIndices = mask.withIndex().filter { it.value == 1 }.map { it.index }
                controlledBitsIndices.forEach { failedChecks[it]++ }
            }
        }
        return failedChecks.indexOfFirst { it == failedChecks.max() }
    }
}
