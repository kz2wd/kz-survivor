package com.cludivers.prototyping

class XBasePlay(val attr1: Int, val attr2: Int, val attr3: Float, val extra: Int = 0): XPlayable() {

    private val children: MutableList<XChildPlay> = mutableListOf()

    fun addChild(xChildPlay: XChildPlay) {
        children.add(xChildPlay)
    }

    fun rootString(): String {
        return "$this:\n${children.joinToString("\n") { it.toString() }}"
    }
}