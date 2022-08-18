package com.cassbana.antifraud.workers.utils

import com.cassbana.antifraud.dto.DifferenceList


/**
 * Contact to calculating difference between two lists but by sending list in chunks.
 */
interface CalculatingDifferenceContract<T> {
    /**
     * Calculate the difference between chunks
     */
    fun calculateChunk(chunk1: List<T>, chunk2: List<T>)

    /**
     * @return final results after comparing chunks as inserted, updated and deleted lists
     */
    fun getFinalResult(): DifferenceList<T>
}