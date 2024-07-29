package com.cludivers.prototyping

abstract class XBuildable {
    private var xplayable: XPlayable? = null

    protected abstract fun generateDraftInstance(): XPlayable

     fun getXPlayableDraftInstance(): XPlayable {
        if (xplayable == null) {
            xplayable = generateDraftInstance()
        }
        return xplayable!!
    }

    abstract fun getFinalXPlayable(): XPlayable
}
