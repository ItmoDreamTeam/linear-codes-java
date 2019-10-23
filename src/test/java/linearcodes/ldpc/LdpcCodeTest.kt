package linearcodes.ldpc

import org.junit.Test

class LdpcCodeTest {

    private val ldpcCode: LdpcCode = LdpcCodeImpl()

    @Test
    fun testEncodingAndDecodingBlock() {
        for (d1 in 0..1) {
            for (d2 in 0..1) {
                for (d3 in 0..1) {
                    for (d4 in 0..1) {
                        for (d5 in 0..1) {
                            val encodedBlock = ldpcCode.encodeBlock("" + d1 + d2 + d3 + d4 + d5)
                            println(encodedBlock)
                        }
                    }
                }
            }
        }
    }
}
