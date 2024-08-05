package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.data_structure.RangeItem
import com.cludivers.kz_survivor.data_structure.RangeMap
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import kotlin.math.ceil
import kotlin.math.max

class MultiMenuComponent(content: Map<Int, MenuComponent>, forceNewLine: Boolean, size: Int, allowPickingItem: Boolean) : MenuComponent(forceNewLine, size,
    allowPickingItem
) {

    private val interactiveContent: RangeMap<MenuComponent> = RangeMap()

    init {
        var currentPos = 0
        content.toSortedMap().forEach { preferedPosition, component ->
            currentPos = max(currentPos, preferedPosition)
            interactiveContent.ranges.add(RangeItem(currentPos, currentPos + component.size, component))
            currentPos += component.size
        }
    }

    override fun onClick(index: Int): Boolean {
        interactiveContent.findItem(index)?.let { (startIndex, it) -> return it.onClick(index - startIndex) }
        return allowPickingItem
    }


    override fun getContent(): Map<Int, CustomIconBuild> {
        var currentOffset = 0
        val content = mutableMapOf<Int, CustomIconBuild>()

        interactiveContent.ranges.forEach {
            var itemStartPos: Int = if (it.item.forceNewLine) { (ceil(currentOffset / 9.0) * 9).toInt() } else currentOffset
            var lastPos = currentOffset
            it.item.getContent().forEach { (innerPos, component) ->
                lastPos = itemStartPos + innerPos
                content[lastPos] = component
            }
            currentOffset = lastPos

        }

        return content
    }


}