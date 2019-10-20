package linearcodes.hamming

/**
 * [Wiki](https://en.wikipedia.org/wiki/Hamming_code)
 * [Simple Explanation](https://www.youtube.com/watch?v=373FUw-2U2k)
 */
interface HammingCode {

    fun encodeBlock(input: String): String

    fun decodeBlock(input: String): String
}
