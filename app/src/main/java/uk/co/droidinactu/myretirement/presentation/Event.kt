package uk.co.droidinactu.myretirement.presentation

import java.time.LocalDate

data class Event(
    val name: String,
    val date: LocalDate? = null,
    val dob: LocalDate? = null,
    val ageAtEvent: Int? = null,
    val showAge: Boolean = false,
)
