package no.howie.common.ssn

import kotlin.test.Test
import kotlin.test.assertEquals

class UtilsTest {
    @Test
    fun testPadZeroes() {
        assertEquals("01", padZeroes(1))
        assertEquals("10", padZeroes(10))
        assertEquals("100", padZeroes(100))
        assertEquals("001", padZeroes(1, 3))
    }
}