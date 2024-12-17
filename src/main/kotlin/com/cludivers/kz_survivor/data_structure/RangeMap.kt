package com.cludivers.kz_survivor.data_structure


class RangeMap<T>(val ranges: MutableList<RangeItem<T>> = mutableListOf()) {

    fun prepare(){
        ranges.sortedBy { it.start() }
    }

    fun findItem(value: Int): Pair<Int, T>? {
        val index = ranges.binarySearch {
            when {
                value < it.start() -> 1
                value >= it.end() -> -1
                else -> 0
            }
        }
        return if (index >= 0) Pair(ranges[index].start(), ranges[index].item()) else null
    }

    fun isEmpty(): Boolean {
        return ranges.isEmpty()
    }
}
