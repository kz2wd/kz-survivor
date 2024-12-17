package com.cludivers.kz_survivor.menus.advanced

import com.cludivers.kz_survivor.data_structure.RangeItem
import com.cludivers.kz_survivor.data_structure.RangeMap
import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.InMenuComponent
import com.cludivers.kz_survivor.menus.OnClickParameter
import com.cludivers.kz_survivor.menus.UnitComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import kotlin.math.ceil
import kotlin.math.max
import kotlin.properties.Delegates

open class MultiComponent(
    val components: Map<Int, Component>,
    forceNewLine: Boolean = false,
    allowPickingItem: Boolean = false
) : Component(
    forceNewLine,
    allowPickingItem
), Iterable<InMenuComponent> {

    constructor(components: List<Component>, forceNewLine: Boolean = false, allowPickingItem: Boolean = false) :
            this(
                components.mapIndexed { idx, component -> idx to component }.toMap(),
                forceNewLine,
                allowPickingItem
            )

    private val interactiveContent: MutableMap<Int, Component> = mutableMapOf()
    var size by Delegates.notNull<Int>()

    override fun onClick(p: OnClickParameter): Boolean {
        interactiveContent.get(p.index)
            ?.let { return it.onClick(p) }
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

    override fun iterator(): Iterator<InMenuComponent> {
        return UnitComponentIterator(components.toList())
    }


}