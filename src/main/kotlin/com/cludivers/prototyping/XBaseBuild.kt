package com.cludivers.prototyping

class XBaseBuild(private val attr1: Int, private val attr2: Int, private val attr3: Float) : XBuildable() {

    private val children: MutableList<XChildBuild> = mutableListOf()

    fun addChild(position: Int) {
        children.add(XChildBuild(this, position))
    }

    fun setNeighbor(originChild: Int, neighbor: Int){
        children.find { it.position == originChild }?.let { child -> child.neighbor = children.find { it.position == neighbor } }
    }

    override fun generateDraftInstance(): XPlayable {
        return XBasePlay(attr1, attr2, attr3)
    }

    override fun getFinalXPlayable(): XPlayable {
        val xBasePlay = getXPlayableDraftInstance() as XBasePlay
        children.forEach { xBasePlay.addChild(it.getFinalXPlayable() as XChildPlay) }
        return xBasePlay
    }

}