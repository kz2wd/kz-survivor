package com.cludivers.kz_survivor.menus

abstract class Component(
    val forceNewLine: Boolean,
    val allowPickingItem: Boolean,
) {

    open fun update(){}

    open fun close(){}

    abstract fun onClick(p: OnClickParameter): Boolean

}