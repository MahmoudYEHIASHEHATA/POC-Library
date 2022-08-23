package com.cassbana.antifraud.dto

data class RSDifferenceList<T>(
    val insertedList: List<T> = listOf(),
    val updatedList: List<T> = listOf(),
    val deletedList: List<T> = listOf()
)