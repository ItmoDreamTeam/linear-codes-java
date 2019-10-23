package linearcodes.ldpc

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class LdpcCodeTest {

    private val ldpcCode: LdpcCode = LdpcCodeImpl()

    @Test
    fun testEncodingAndDecoding() {
        iterateOverAllInputs {
            val encoded = ldpcCode.encodeBlock(it)
            val decoded = ldpcCode.decodeBlock(encoded)
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
}
