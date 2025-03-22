package no.howie.ssn

fun isValidSsn(ssn: String): Boolean {
    try {
        Ssn(ssn)
        return true
    } catch (e: IllegalArgumentException) {
        return false
    }
}

fun getGender(ssn: String) = Ssn(ssn).gender()
fun ssnFromAge(age: Int) = Ssn.forAge(age).toString()
fun ssnFromYear(year: Int) = Ssn.forYear(year).toString()
