package no.howie.common.ssn

import Gender
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus

class DateOfBirth(val day: Int, val month: Int, val year: Int) {

    init {
        require(year in MIN_SUPPORTED_YEAR..MAX_SUPPORTED_YEAR) { "Year must be between $MIN_SUPPORTED_YEAR and $MAX_SUPPORTED_YEAR" }
        require(month in 1..12) { "Month must be between 01 and 12" }
        require(day in 1..31) { "Day must be between 01 and 31" }
    }


    fun age(): Int {
        var age = now().year - year
        if (isOneYearTooOld(day, month)) age--

        return age
    }

    fun validIndividualNumber(gender: Gender): IndividualNumber {
        val individualNumber = IndividualNumber.fromYear(year, gender)
        try {
            ControlDigits.of(this, individualNumber)
            return individualNumber
        } catch (_: IllegalArgumentException) {
            return this.validIndividualNumber(gender)
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other as DateOfBirth

        if (day != other.day) return false
        if (month != other.month) return false
        if (year != other.year) return false

        return true
    }

    override fun hashCode(): Int {
        var result = day.hashCode()
        result = 31 * result + month.hashCode()
        result = 31 * result + year.hashCode()
        return result
    }

    override fun toString(): String {
        val dayStr = padZeroes(day)
        val monthStr = padZeroes(month)
        return "$dayStr$monthStr${twoDigitYear()}"
    }

    fun twoDigitYear(): String {
        return padZeroes(year % 100)
    }

    companion object {
        fun fromSsn(ssn: String): DateOfBirth {
            val century = IndividualNumber.fromSsn(ssn).century()
            val year = (century * 100) + yearFromSsn(ssn)

            return DateOfBirth(dayFromSsn(ssn), monthFromSsn(ssn), year)
        }

        fun fromAge(age: Int): DateOfBirth {
            var birthYear = getBirthYearForAge(age)
            val randomMonth = (1..12).random()
            val randomDay = getDayOfMonth(birthYear, randomMonth)

            if (isOneYearTooOld(randomDay, randomMonth)) birthYear --
            return DateOfBirth(randomDay, randomMonth, birthYear)
        }

        fun fromYear(year: Int): DateOfBirth {
            val randomMonth = (1..12).random()
            val randomDay = getDayOfMonth(year, randomMonth)
            return DateOfBirth(randomDay, randomMonth, year)
        }

        private fun getDayOfMonth(year: Int, month: Int): Int {
            val maxDayOfMonth = getNumberOfDaysInMonth(year, month)
            return (1..maxDayOfMonth).random()
        }

        fun getNumberOfDaysInMonth(year: Int, month: Int): Int {
            val start = LocalDate(year, month, 1)
            val end = start.plus(1, DateTimeUnit.MONTH)
            return start.daysUntil(end)
        }

        private fun isOneYearTooOld(day: Int, month: Int): Boolean {
            var currentMonth = now().monthNumber
            var currentDay = now().dayOfMonth
            return month > currentMonth || (month == currentMonth && currentDay < day)
        }
    }
}