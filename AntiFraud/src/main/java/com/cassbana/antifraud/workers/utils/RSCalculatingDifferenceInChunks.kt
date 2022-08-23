package com.cassbana.antifraud.workers.utils

import androidx.annotation.VisibleForTesting
import com.cassbana.antifraud.dto.RSDifferenceList
import com.cassbana.antifraud.workers.constants.RSWorkerConstants
import timber.log.Timber

/**
 * Calculating difference between two lists but by sending list in chunks.
 * @param tag: The tag to log the operations with
 * @param keySelector: a lambda function that take the object as a parameter and returning the
 * key or identifier of it.
 */
class RSCalculatingDifferenceInChunks<Key, T>(
    private val tag: String = RSWorkerConstants.SYNCING_TAG,
    private val keySelector: (T) -> Key
) : RSCalculatingDifferenceContract<T> {

    private var deletedList = mutableListOf<T>()
    private var insertedList = mutableListOf<T>()
    private var updatedList = mutableListOf<T>()

    override fun calculateChunk(chunk1: List<T>, chunk2: List<T>) {
        Timber.tag(tag).d("Calculating Difference")
        val difference = getDifferenceBetweenLists(
            deletedList + chunk1,
            insertedList + chunk2
        )
        deletedList = difference.deletedList.toMutableList()
        Timber.tag(tag).d("deleted list: $deletedList")
        insertedList = difference.insertedList.toMutableList()
        Timber.tag(tag).d("inserted list: $insertedList")
        updatedList.addAll(difference.updatedList)
        Timber.tag(tag).d("updated list: $updatedList")
    }

    override fun getFinalResult(): RSDifferenceList<T> {
        return RSDifferenceList(
            insertedList = insertedList,
            updatedList = updatedList,
            deletedList = deletedList
        )
    }

    @VisibleForTesting
    fun getDifferenceBetweenLists(beforeList: List<T>, afterList: List<T>): RSDifferenceList<T> {
        if (afterList.isEmpty())
            return RSDifferenceList(
                deletedList= beforeList
            )

        if (beforeList.isEmpty())
            return RSDifferenceList(
                insertedList = afterList
            )

        val updatedList = mutableListOf<T>()
        val insertedList = mutableListOf<T>()
        val deletedList = mutableListOf<T>()


        val (smallerList, largerList, smallerListIsBeforeList) =
            if (beforeList.size > afterList.size)
                Triple(afterList, beforeList, false)
            else
                Triple(beforeList, afterList, true)

        val map = smallerList.associateBy(keySelector).toMutableMap()

        for (item in largerList) {
            val mapItem = map[keySelector(item)]
            if (mapItem == null) {
                if (smallerListIsBeforeList)
                    insertedList.add(item)
                else
                    deletedList.add(item)
            } else {
                if (item != mapItem) {
                    if (smallerListIsBeforeList)
                        updatedList.add(item)
                    else
                        updatedList.add(mapItem)
                }
                map.remove(keySelector(item))
            }
        }

        for (item in map) {
            if (smallerListIsBeforeList)
                deletedList.add(item.value)
            else
                insertedList.add(item.value)
        }

        return RSDifferenceList(
            insertedList = insertedList,
            updatedList = updatedList,
            deletedList = deletedList
        )
    }
}