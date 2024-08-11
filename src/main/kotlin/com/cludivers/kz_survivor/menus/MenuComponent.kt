package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild

abstract class MenuComponent (
    val forceNewLine: Boolean,
    var size: Int,
    val allowPickingItem: Boolean,
) {
    abstract fun getContent(): Map<Int, CustomIconBuild>

    open fun update(){}

    open fun close(){}

    abstract fun onClick(p: OnClickParameter): Boolean

}