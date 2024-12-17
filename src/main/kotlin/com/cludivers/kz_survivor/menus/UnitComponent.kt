package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import org.bukkit.Material

class UnitComponent(
    val icon: CustomIconBuild,
    allowPickingItem: Boolean = false,
    private val whenClicked: (OnClickParameter) -> Unit
) : Component(
    false,
    allowPickingItem
) {
    override fun onClick(p: OnClickParameter): Boolean {
        whenClicked(p)
        return allowPickingItem
    }

    companion object {
        val EMPTY = UnitComponent(CustomIconBuild("No representation found", Material.STONE)) {}
    }

}