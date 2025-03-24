package no.howie.common.ssn

import Gender
import kotlin.jvm.JvmStatic
import kotlin.test.Test
import kotlin.test.assertEquals

class SsnTest {

    companion object {
        const val MALE = "05079426778"
        const val FEMALE = "05079426697"
        @JvmStatic fun years() = MIN_SUPPORTED_YEAR..MAX_SUPPORTED_YEAR
    }

    @Test
    fun constructorTest() {
        val ssn = Ssn("05079426778")
        assertEquals("050794", ssn.dateOfBirth())
        assertEquals("267", ssn.individualNumber.toString())
        assertEquals("78", ssn.controlDigit.toString())
    }

    @Test
    fun stringConstructorTest() {
        val ssn = Ssn(MALE)
        assertEquals("050794", ssn.dateOfBirth())
        assertEquals("267", ssn.individualNumber.toString())
        assertEquals("78", ssn.controlDigit.toString())
    }

    @Test
    fun getGenderTest() {
        assertEquals(Gender.Male, Ssn(MALE).gender())
        assertEquals(Gender.Female, Ssn(FEMALE).gender())
    }

    @Test
    fun forYear() {
        years().forEach { year ->
            var ssn = Ssn.forYear(year)
            assertEquals(year, ssn.dateOfBirth.year)
            assertEquals(Gender.Female, ssn.gender(), "invalid gender for ssn $ssn")
        }
    }

    @Test
    fun forYear_overrideGender() {
        var ssn = Ssn.forYear(1997, Gender.Male)
        assertEquals(Gender.Male, ssn.gender())
    }

    @Test
    fun forAge() {
        (0..169).forEach { age ->
            var ssn = Ssn.forAge(age)
            assertEquals(age, ssn.age(), "invalid age for ssn $ssn")
            assertEquals(Gender.Female, ssn.gender(), "invalid gender for ssn $ssn")
        }
    }

    @Test
    fun forAge_overrideGender() {
        var ssn = Ssn.forAge(25, Gender.Male)
        assertEquals(Gender.Male, ssn.gender())
    }
}
