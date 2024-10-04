package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.data_structure.RangeItem
import com.cludivers.kz_survivor.data_structure.RangeMap
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import kotlin.math.ceil
import kotlin.math.max

open class MultiMenuComponent(
    content: Map<Int, MenuComponent>,
    forceNewLine: Boolean = false,
    allowPickingItem: Boolean = false
) : MenuComponent(
    forceNewLine, 0,
    allowPickingItem
) {

    constructor(content: List<MenuComponent>, forceNewLine: Boolean = false, allowPickingItem: Boolean = false) :
            this(
                content.mapIndexed { idx, component -> idx to component }.toMap(),
                forceNewLine,
                allowPickingItem
            )

    private val interactiveContent: RangeMap<MenuComponent> = RangeMap()

    init {
        var currentPos = 0
        content.toSortedMap().forEach { preferredPosition, component ->
            currentPos = max(currentPos, preferredPosition)
            interactiveContent.ranges.add(RangeItem(currentPos, currentPos + component.size, component))
            currentPos += component.size
        }
        size = currentPos
    }

    override fun onClick(p: OnClickParameter): Boolean {
        interactiveContent.findItem(p.index)
            ?.let { (startIndex, it) -> return it.onClick(p.withIndex(p.index - startIndex)) }
        return allowPickingItem
    }

    // Horrible mess kill it.
    /**
     * Here is the list of functionalities
     * - GetContent(start: int, end: int)
     * - items have a size
     * - then must fit into the asked range, or not be included -> could lead to items that will never fit ? :/
     * - save what was generated for next time it is asked
     */
    override fun getContent(maxQuantity: Int): Map<Int, CustomIconBuild> {
        var currentOffset = 0
        val content = mutableMapOf<Int, CustomIconBuild>()
        var currentQuantity = 0
        interactiveContent.ranges.takeWhile { currentQuantity < maxQuantity }. forEach {
            currentOffset = max(it.start, currentOffset)
            val itemStartPos: Int = if (it.item.forceNewLine) {
                (ceil(currentOffset / 9.0) * 9).toInt()
            } else currentOffset
            var lastPos = itemStartPos
            val remainingSpace = maxQuantity - currentQuantity
            it.item.getContent(remainingSpace).toSortedMap().forEach { (innerPos, component) ->
                lastPos = itemStartPos + innerPos
                content[lastPos] = component
            }
            currentQuantity += it.size()
            currentOffset = lastPos + 1

        }

        return content
    }


}