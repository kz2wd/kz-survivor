package com.cludivers.prototyping

fun main() {
    println("Hello World")
    val builder = XBaseBuild(0, 1, 3f)
    // Add builder children
    listOf(1, 2, 3).forEach { builder.addChild(it) }
    builder.setNeighbor(1, 2)
    builder.setNeighbor(2, 3)
    builder.setNeighbor(3, 1)

    val player = builder.getFinalXPlayable() as XBasePlay
    println(player.rootString())
}


