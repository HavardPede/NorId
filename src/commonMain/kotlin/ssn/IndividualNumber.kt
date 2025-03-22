package no.howie.common.ssn

import Gender
import java.lang.Integer.parseInt

class IndividualNumber(private val value: String, private val yearOfBirth: Int) {
    private val valueAsInt: Int = parseInt(value)
    init {
        require(value.length == 3 && value.all { it.isDigit() }) { "Individual number must be 3 digits long" }
    }

    fun gender() = Gender.fromIndividualNumber(valueAsInt)
    override fun toString() = value

    fun century(): Int {
        return when (valueAsInt) {
            in 0..499 -> 19
            in 500..749 -> if (yearOfBirth > 54) 18 else 20
            in 750..999 -> if (yearOfBirth > 40) 19 else 20
            else -> 20
        }
    }

    companion object {
        fun fromSsn(ssn: String) = IndividualNumber(individualNumberFromSsn(ssn), yearFromSsn(ssn))

        fun fromYear(year: Int, gender: Gender): IndividualNumber {
            val randomValue =  when (year) {
                in 1855..1899 -> randomIndividualValue(500, 749, gender)
                in 1900..1999 -> randomIndividualValue(0, 499, gender)
                in 2000..2039 -> randomIndividualValue(500, 999, gender)
                else -> throw IllegalArgumentException("Year must be between $MIN_SUPPORTED_YEAR and $MAX_SUPPORTED_YEAR")
            }

            return IndividualNumber(randomValue, year)
        }

        private fun randomIndividualValue(lower: Int, upper: Int, gender: Gender): String {
            var value = (Math.random() * (upper - lower)).toInt() + lower
            if (Gender.fromIndividualNumber(value) != gender) value++
            return padZeroes(value, numberOfDigits = 3)
        }

    }
}