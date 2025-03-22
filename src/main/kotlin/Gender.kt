package no.howie

enum class Gender {
    Male,
    Female;

    companion object {
        fun fromIndividualNumber(value: Int): Gender {
            return if (((value % 10) % 2) == 0) Female else Male
        }
    }

}