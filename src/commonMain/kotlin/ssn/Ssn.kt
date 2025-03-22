package no.howie.common.ssn

import Gender

class Ssn(private val value: String) {
    internal var dateOfBirth = DateOfBirth.fromSsn(value)
    var individualNumber = IndividualNumber.fromSsn(value)
    var controlDigit = ControlDigits.fromSsn(value)

    init {
        require(value.length == 11 && value.all { it.isDigit() }) { "SSN must be 11 digits long" }
        controlDigit.isValidFor(dateOfBirth, individualNumber)
    }

    fun gender() = individualNumber.gender()
    fun dateOfBirth() = dateOfBirth.toString()
    fun age() = dateOfBirth.age()

    @Override
    override fun toString() = value

    companion object {
        fun forAge(age: Int, gender: Gender = Gender.Female): Ssn {
            val dateOfBirth = DateOfBirth.fromAge(age)
            return forDateOfBirth(dateOfBirth, gender)
        }

        fun forYear(year: Int, gender: Gender = Gender.Female): Ssn {
            val dateOfBirth = DateOfBirth.fromYear(year)
            return forDateOfBirth(dateOfBirth, gender)
        }

        fun forDateOfBirth(dateOfBirth: DateOfBirth, gender: Gender = Gender.Female): Ssn {
            val individualNumber = dateOfBirth.validIndividualNumber(gender)
            return of(dateOfBirth, individualNumber, ControlDigits.of(dateOfBirth, individualNumber))
        }

        private fun of(dob: DateOfBirth, individualNumber: IndividualNumber, controlDigits: ControlDigits): Ssn {
            return Ssn("$dob$individualNumber$controlDigits")
        }
    }
}