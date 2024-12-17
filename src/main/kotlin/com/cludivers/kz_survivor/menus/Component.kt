package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.data_structure.RangeItem
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild

abstract class Component(
    val forceNewLine: Boolean,
    val allowPickingItem: Boolean,
) {

    open fun update(){}

    open fun close(){}

    abstract fun onClick(p: OnClickParameter): Boolean

}