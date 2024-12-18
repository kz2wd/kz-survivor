package com.cludivers.kz_survivor.menus.advanced

import com.cludivers.kz_survivor.menus.InMenuComponent
import com.cludivers.kz_survivor.menus.OnClickParameter
import com.cludivers.kz_survivor.menus.UnitComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import org.bukkit.Material

class InlineScrollComponent(private val items: List<UnitComponent>, private val amountOfItemShown: Int,
                            allowPickingItem: Boolean = false, forceNewLine: Boolean = false) : MultiComponent(
    forceNewLine, allowPickingItem
) {

    private var currentIndex = 0

    override fun iterator(): Iterator<InMenuComponent> {
        return sequence {
            yield(InMenuComponent(0, LEFT_SCROLLER))
            // TODO ADD SUPPORT FOR INDEX
            items.take(amountOfItemShown).forEachIndexed { i, it -> yield(InMenuComponent(i + 1, it)) }
            yield(InMenuComponent(amountOfItemShown + 1, RIGHT_SCROLLER))

        }.iterator()
    }

    private val LEFT_SCROLLER = UnitComponent(LEFT_ARROW, false) { currentIndex -= 1 }
    val RIGHT_SCROLLER = UnitComponent(RIGHT_ARROW, false) { currentIndex += 1 }


    override fun onClick(p: OnClickParameter): Boolean {
        when(p.index) {
            0 -> {  }
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