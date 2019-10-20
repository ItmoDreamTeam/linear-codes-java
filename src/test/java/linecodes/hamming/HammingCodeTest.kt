package linecodes.hamming

import org.junit.Test
import java.util.concurrent.ThreadLocalRandom
import kotlin.test.assertEquals

class HammingCodeTest {

    private val hammingCode: HammingCode = HammingCodeImpl()

    @Test
    fun testEncodingBlock() {
        val encodedBlock = hammingCode.encodeBlock("10011010")
        assertEquals("011100101010", encodedBlock)
    }

    @Test
    fun testDecodingBlockWithoutErrors() {
        val decodedBlock = hammingCode.decodeBlock("011100101010")
        assertEquals("10011010", decodedBlock)
    }

    @Test
    fun testDecodingBlockWithOneError() {
        val decodedBlock = hammingCode.decodeBlock("011100101110")
        assertEquals("10011010", decodedBlock)
    }

    @Test
    fun testEncodingAndDecodingWithoutErrors() {
        val input = randomBlock()
        val encoded = hammingCode.encodeBlock(input)
        val decoded = hammingCode.decodeBlock(encoded)
        assertEquals(input, decoded)
    }

    @Test
    fun testEncodingAndDecodingWithOneError() {
        val input = randomBlock()
        val encoded = hammingCode.encodeBlock(input)
        val encodedWithError = makeError(encoded)
        val decoded = hammingCode.decodeBlock(encodedWithError)
        assertEquals(input, decoded)
    }

    private fun randomBlock(): String {
        val length = ThreadLocalRandom.current().nextInt(1024)
        val result = StringBuilder()
        for (i in 0..length) {
            result.append(ThreadLocalRandom.current().nextInt(2))
        }
        return result.toString()
    }

    private fun makeError(block: String): String {
        val errorBitIndex = ThreadLocalRandom.current().nextInt(block.length)
        val result = StringBuilder(block)
        result[errorBitIndex] = if (block[errorBitIndex] == '0') '1' else '0'
        return result.toString()
    }
}
