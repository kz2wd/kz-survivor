package com.cludivers.prototyping

class XChildPlay(private val parent: XBasePlay, val position: Int) : XPlayable() {

    var neighbor: XChildPlay? = null

    override fun toString(): String {
        return "XChildPlay(parent=$parent, position=$position, neighbor=${neighbor?.position})"
    }
}