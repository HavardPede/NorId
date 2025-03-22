package ssn

import no.howie.Gender
import no.howie.ssn.MAX_SUPPORTED_YEAR
import no.howie.ssn.MIN_SUPPORTED_YEAR
import no.howie.ssn.Ssn
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SsnTest {

    companion object {
        const val MALE = "05079426778"
        const val FEMALE = "05079426697"
        @JvmStatic fun years() = MIN_SUPPORTED_YEAR..MAX_SUPPORTED_YEAR
        @JvmStatic fun ages() = 0..169
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

    @ParameterizedTest
    @MethodSource("years")
    fun forYear(year: Int) {
        var ssn = Ssn.forYear(year)
        assertEquals(year, ssn.dateOfBirth.year)
        assertEquals(Gender.Female, ssn.gender())
    }

    @Test
    fun forYear_overrideGender() {
        var ssn = Ssn.forYear(1997, Gender.Male)
        assertEquals(Gender.Male, ssn.gender())
    }

    @ParameterizedTest
    @MethodSource("ages")
    fun forAge(age: Int) {
        var ssn = Ssn.forAge(age)
        assertEquals(age, ssn.age())
        assertEquals(Gender.Female, ssn.gender())
    }

    @Test
    fun forAge_overrideGender() {
        var ssn = Ssn.forAge(25, Gender.Male)
        assertEquals(Gender.Male, ssn.gender())
    }
}
