package com.cassbana.antifraud.dto

data class DifferenceList<T>(
    val insertedList: List<T> = listOf(),
    val updatedList: List<T> = listOf(),
    val deletedList: List<T> = listOf()
)