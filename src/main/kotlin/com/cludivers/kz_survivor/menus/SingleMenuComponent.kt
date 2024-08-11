package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import org.bukkit.Material

class SingleMenuComponent(
    val icon: CustomIconBuild,
    allowPickingItem: Boolean = false,
    private val whenClicked: (OnClickParameter) -> Unit
) : MenuComponent(
    false, 1,
    allowPickingItem
) {

    override fun getContent(): Map<Int, CustomIconBuild> {
        return mapOf(0 to icon)
    }

    override fun onClick(p: OnClickParameter): Boolean {
        whenClicked(p)
        return allowPickingItem
    }

    companion object {
        val EMPTY = SingleMenuComponent(CustomIconBuild("No representation found", Material.STONE)) {}
    }

}