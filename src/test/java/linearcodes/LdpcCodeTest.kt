package linearcodes

import org.junit.Test
import java.util.concurrent.ThreadLocalRandom
import kotlin.test.assertEquals
import kotlin.test.fail

class LdpcCodeTest {

    private val ldpcCode: LdpcCode = LdpcCode()

    @Test
    fun testEncodingAndDecoding() {
        iterateOverAllInputs {
            val encoded = ldpcCode.encodeBlock(it)
            val decoded = ldpcCode.decodeBlock(encoded)
            assertEquals(it, decoded)
        }
    }

    @Test
    fun testEncodingAndDecodingWithErrors() {
        iterateOverAllInputs {
            val encoded = ldpcCode.encodeBlock(it)
            val spoiledEncoded = makeError(encoded)
            val decoded = ldpcCode.decodeBlock(spoiledEncoded)
            assertEquals(it, decoded)
        }
    }

    @Test
    fun `Ensure all codewords are different`() {
        val codewords = HashSet<String>()
        iterateOverAllInputs {
            val codeword = ldpcCode.encodeBlock(it)
            println(codeword)
            if (codewords.contains(codeword)) fail()
            codewords.add(codeword)
        }
    }

    private fun iterateOverAllInputs(action: (String) -> Unit) {
        for (d1 in 0..1) {
            for (d2 in 0..1) {
                for (d3 in 0..1) {
                    for (d4 in 0..1) {
                        for (d5 in 0..1) {
                            action("" + d1 + d2 + d3 + d4 + d5)
                        }
                    }
                }
            }
        }
    }

    private fun makeError(block: String): String {
        val errorBitIndex = ThreadLocalRandom.current().nextInt(block.length)
        val result = StringBuilder(block)
        result[errorBitIndex] = if (block[errorBitIndex] == '0') '1' else '0'
        return result.toString()
    }
}
