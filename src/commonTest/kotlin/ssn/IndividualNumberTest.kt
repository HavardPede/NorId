package no.howie.common.ssn

import no.howie.common.ssn.IndividualNumber
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class IndividualNumberTest {
    @Test
    fun testGetCentury() {
        val individualNumber = IndividualNumber.fromSsn("09025563414")
        assertEquals(18, individualNumber.century())
    }
}