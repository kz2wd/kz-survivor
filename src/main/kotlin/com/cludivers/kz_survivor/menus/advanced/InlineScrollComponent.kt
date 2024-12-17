package com.cludivers.kz_survivor.menus.advanced

import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.OnClickParameter
import com.cludivers.kz_survivor.menus.UnitComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import org.bukkit.Material

class InlineScrollComponent(private val items: List<UnitComponent>, private val amountOfItemShown: Int, allowPickingItem: Boolean = false, forceNewLine: Boolean = false) : Component(
    forceNewLine, allowPickingItem
) {

    private var currentIndex = 0

    override fun getContent(): Map<Int, CustomIconBuild> {
        return mutableMapOf(
            0 to LEFT_ARROW,
            amountOfItemShown + 1 to RIGHT_ARROW,
        ) + items.take(amountOfItemShown).mapIndexed {i, it -> i + 1 + currentIndex to it.icon}.toMap()
    }

    override fun onClick(p: OnClickParameter): Boolean {
        when(p.index) {
            0 -> { currentIndex -= 1 }
            amountOfItemShown + 1 -> { currentIndex += 1 }
            else -> { items.getOrNull(p.index + currentIndex)?.onClick(p.withIndex(0)) }
        }
        return allowPickingItem
    }

    companion object {
        val LEFT_ARROW = CustomIconBuild("Scroll Left", Material.ARROW)
        val RIGHT_ARROW = CustomIconBuild("Scroll Right", Material.ARROW)
    }
}