package com.cludivers.kz_survivor.data_structure

abstract class RangeItem<T> (var start: Int = 0) {

    fun start(): Int {
        return start
    }

    abstract fun size(): Int

    fun end(): Int {
        return start + size()
    }

    abstract fun item(): T

    fun withOffset(offset: Int): RangeItem<T> {
        start += offset
        return this
    }

}

