package com.cludivers.kz_survivor.data_structure

data class RangeItem<T>(val start: Int, val end: Int, val item: T) {

    fun size(): Int {
        return end - start
    }

}

