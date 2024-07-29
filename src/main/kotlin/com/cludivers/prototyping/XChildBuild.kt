package com.cludivers.prototyping

class XChildBuild(val parent: XBaseBuild, val position: Int): XBuildable() {

    var neighbor: XChildBuild? = null

    override fun generateDraftInstance(): XPlayable {
        return XChildPlay(parent.getXPlayableDraftInstance() as XBasePlay, position)
    }

    override fun getFinalXPlayable(): XPlayable {
        val xChildPlay = getXPlayableDraftInstance() as XChildPlay
        neighbor?.let { xChildPlay.neighbor = neighbor!!.getXPlayableDraftInstance() as XChildPlay }
        return xChildPlay
    }
}