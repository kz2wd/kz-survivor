package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import org.bukkit.event.inventory.InventoryClickEvent

abstract class MenuComponent (
    val forceNewLine: Boolean,
    val size: Int,
    val allowPickingItem: Boolean,
) {
    abstract fun getContent(): Map<Int, CustomIconBuild>

    open fun update(){}

    open fun close(){}

    abstract fun onClick(index: Int): Boolean

}