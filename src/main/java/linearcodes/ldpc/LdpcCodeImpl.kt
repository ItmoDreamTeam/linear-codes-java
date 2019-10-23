package linearcodes.ldpc

import linearcodes.BinaryMatrix
import linearcodes.Utils

/**
 * ([n], [k]) LDPC code
 */
class LdpcCodeImpl : LdpcCode {

    private val checkMatrix = BinaryMatrix.ofInts(listOf(
            listOf(0, 1, 1, 1, 0, 1, 0, 0, 0, 0),
            listOf(1, 0, 1, 0, 0, 0, 1, 0, 0, 0),
            listOf(1, 0, 1, 0, 1, 0, 0, 1, 0, 0),
            listOf(0, 0, 1, 1, 1, 0, 0, 0, 1, 0),
            listOf(1, 1, 0, 0, 1, 0, 0, 0, 0, 1)
    ))

    private val generatorMatrix = BinaryMatrix.ofInts(listOf(
            listOf(1, 0, 0, 0, 0, 0, 1, 1, 0, 1),
            listOf(0, 1, 0, 0, 0, 1, 0, 0, 0, 1),
            listOf(0, 0, 1, 0, 0, 1, 1, 1, 1, 0),
            listOf(0, 0, 0, 1, 0, 1, 0, 0, 1, 0),
            listOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1)
    ))

    private val k = generatorMatrix.height
    private val n = generatorMatrix.width

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
        require(syndrome.isZero()) { "Syndrome is not zero. Encoded block contains errors." }
        return Utils.listToString(inputDigits)
    }
}
