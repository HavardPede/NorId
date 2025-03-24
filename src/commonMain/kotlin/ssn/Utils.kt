package no.howie.common.ssn

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Prefixes a number with a zero if it is less than 10.
 */
fun padZeroes(number: Int, numberOfDigits: Int = 2) = number.toString().padStart(numberOfDigits, '0')

fun getBirthYearForAge(age: Int) = now().year - age
fun now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

fun dayFromSsn(ssn: String) = ssn.substring(0, 2).toInt()
fun monthFromSsn(ssn: String) = ssn.substring(2, 4).toInt()
fun yearFromSsn(ssn: String) = ssn.substring(4, 6).toInt()
fun individualNumberFromSsn(ssn: String) = ssn.substring(6, 9)

var MAX_SUPPORTED_YEAR = 2039
var MIN_SUPPORTED_YEAR = 1855
