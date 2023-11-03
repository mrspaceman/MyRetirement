package uk.co.droidinactu.myretirement.presentation

import java.time.LocalDate

data class Event(
    val name: String,
    val dob: LocalDate,
    val date: LocalDate,
    val showAge: Boolean = false,
)
