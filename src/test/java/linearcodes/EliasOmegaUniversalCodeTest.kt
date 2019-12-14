package linearcodes

import org.junit.Test
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.toList
import kotlin.test.assertEquals

class EliasOmegaUniversalCodeTest {

    private val eliasOmegaUniversalCode = EliasOmegaUniversalCode()

    @Test
    fun testEncoding() {
        testEncoding(listOf(1), "0")
        testEncoding(listOf(2), "100")
        testEncoding(listOf(3), "110")
        testEncoding(listOf(4), "101000")
        testEncoding(listOf(10, 100, 16), "1110100101101100100010100100000")
        testEncoding(listOf(1_000_000), "1010010011111101000010010000000")
    }

    private fun testEncoding(message: List<Int>, encodedMessage: String) =
            assertEquals(encodedMessage, eliasOmegaUniversalCode.encode(message))

    @Test
    fun testDecoding() {
        testDecoding(listOf(1), "0")
        testDecoding(listOf(2), "100")
        testDecoding(listOf(3), "110")
        testDecoding(listOf(4), "101000")
        testDecoding(listOf(10, 100, 16), "1110100101101100100010100100000")
        testDecoding(listOf(1_000_000), "1010010011111101000010010000000")
    }

    private fun testDecoding(message: List<Int>, encodedMessage: String) =
            assertEquals(message, eliasOmegaUniversalCode.decode(encodedMessage))

    @Test
    fun testEncodingAndDecoding() {
        val message = randomMessage()
        val encodedMessage = eliasOmegaUniversalCode.encode(message)
        val decodedMessage = eliasOmegaUniversalCode.decode(encodedMessage)
        assertEquals(message, decodedMessage)
    }

    private fun randomMessage(): List<Int> = ThreadLocalRandom.current()
            .ints(1, 1024)
            .limit(100)
            .toList()
}
