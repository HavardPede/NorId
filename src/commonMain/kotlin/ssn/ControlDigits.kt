package no.howie.common.ssn


class ControlDigits(private val digits: String) {
    init {
        require(digits.length == 2 && digits.all { it.isDigit() }) { "Control digits must be 2 digits long" }
    }

    fun isValidFor(dob: DateOfBirth, individualNumber: IndividualNumber): Boolean {
        return equals(of(dob, individualNumber))
    }



    override fun toString() = digits.toString()
    override fun hashCode() = digits.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ControlDigits

        return digits == other.digits
    }

    companion object {
        val FIRST_DIGIT_WEIGHTS = intArrayOf(3, 7, 6, 1, 8, 9, 4, 5, 2)
        val SECOND_DIGIT_WEIGHTS = intArrayOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)

        fun fromSsn(ssn: String): ControlDigits {
            return ControlDigits(ssn.substring(9, 11))
        }

        fun of(dob: DateOfBirth, individualNumber: IndividualNumber): ControlDigits {
            return of(dob.toString(), individualNumber.toString())
        }

        fun of(dob: String, individualNumber: String): ControlDigits {
            require(dob.length == 6) { "Date of birth string should have 6 digits; $dob" }
            require(individualNumber.length == 3) { "Individual number string should have 3 digits; $individualNumber" }
            val firstDigit = firstDigit(dob, individualNumber)
            val secondDigit = secondDigit(dob, individualNumber, firstDigit)

            return ControlDigits("$firstDigit$secondDigit")
        }

        fun firstDigit(dob: String, individualNumber: String): Int {
            return calculateControlDigit(dob + individualNumber, FIRST_DIGIT_WEIGHTS)
        }

        fun secondDigit(dob: String, individualNumber: String, firstDigit: Int): Int {
            return calculateControlDigit(dob + individualNumber + firstDigit, SECOND_DIGIT_WEIGHTS)
        }

        private fun calculateControlDigit(digits: String, weights: IntArray): Int {
            var sum = sumDigits(digits, weights) % 11
            if (sum == 0) return 0
            require (sum > 1) { "Control digit must be a single digit" }
            return 11 - sum
        }

        private fun sumDigits(nineFirstStr: String, weights: IntArray): Int {
            return weights.foldIndexed(0) { index, sum, weight -> sum + (nineFirstStr[index].digitToInt() * weight) }
        }

    }
}