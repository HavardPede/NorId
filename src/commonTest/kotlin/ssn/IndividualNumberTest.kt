package no.howie.common.ssn

import kotlin.test.Test
import kotlin.test.assertEquals

class IndividualNumberTest {
    @Test
    fun testGetCentury() {
        val individualNumber = IndividualNumber.fromSsn("09025563414")
        assertEquals(18, individualNumber.century())
    }
}