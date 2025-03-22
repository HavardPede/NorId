package no.howie.common.ssn

import no.howie.common.ssn.ControlDigits.Companion.firstDigit
import no.howie.common.ssn.ControlDigits.Companion.secondDigit
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.jvm.JvmStatic
import kotlin.test.assertEquals

class ControlDigitsTest {

    companion object {
        @JvmStatic
        fun ssnProvider() = listOf(
            "14070273439",
            "10084937644",
            "29048445061",
            "23084904406",
            "11106035689",
            "17082271481",
            "09018413636",
            "21092540003",
            "18085420891",
            "23078821639",
            "05102906081",
            "03101359211",
            "01052827007",
            "15107448656",
            "05060268845",
            "20088414056",
            "24066845407",
            "10125403242",
            "09077407607",
            "18104502454",
            "07034416278",
            "03093620800",
            "23111197695",
            "08074434887",
            "22018400867",
            "06060758210",
            "03047320215",
            "07125942487",
            "06067409297",
        )
    }

    @ParameterizedTest
    @MethodSource("ssnProvider")
    fun validateControlDigits(ssn: String) {
        val dateOfBirth = DateOfBirth.fromSsn(ssn)
        val individualNumber = IndividualNumber.fromSsn(ssn)
        val controlDigits = ControlDigits.fromSsn(ssn)

        assertEquals(true, controlDigits.isValidFor(dateOfBirth, individualNumber), "Invalid control digits for SSN: $ssn")
    }

    @ParameterizedTest
    @MethodSource("ssnProvider")
    fun controlDigits(ssn: String) {
        val nineFirstDigits = ssn.substring(0, 9)
        var first = firstDigit(nineFirstDigits.substring(0, 6), nineFirstDigits.substring(6, 9))
        var second = secondDigit(nineFirstDigits.substring(0, 6), nineFirstDigits.substring(6, 9), first)
        assertEquals(ssn.substring(9, 10), first.toString())
        assertEquals(ssn.substring(10, 11), second.toString())
    }
}