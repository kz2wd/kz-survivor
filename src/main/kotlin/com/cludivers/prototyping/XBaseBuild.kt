package com.cludivers.prototyping

class XClassBuild(val attr1: Int, val attr2: Int, val attr3: Float) {


    fun getPlayVersion(): XClassPlay {
        return XClassPlay(attr1, attr2, attr3)
    }
}