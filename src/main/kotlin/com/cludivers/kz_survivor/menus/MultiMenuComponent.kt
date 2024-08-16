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


    override fun getContent(): Map<Int, CustomIconBuild> {
        var currentOffset = 0
        val content = mutableMapOf<Int, CustomIconBuild>()

        interactiveContent.ranges.forEach {
            currentOffset = max(it.start, currentOffset)
            val itemStartPos: Int = if (it.item.forceNewLine) {
                (ceil(currentOffset / 9.0) * 9).toInt()
            } else currentOffset
            var lastPos = itemStartPos
            it.item.getContent().toSortedMap().forEach { (innerPos, component) ->
                lastPos = itemStartPos + innerPos
                content[lastPos] = component
            }
            currentOffset = lastPos + 1

        }

        return content
    }


}