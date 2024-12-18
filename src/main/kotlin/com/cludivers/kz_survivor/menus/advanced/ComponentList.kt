package com.cludivers.kz_survivor.menus.advanced

import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.InMenuComponent
import com.cludivers.kz_survivor.menus.OnClickParameter
import com.cludivers.kz_survivor.menus.UnitComponent
import kotlin.math.ceil
import kotlin.math.max

class ComponentList(
    val components: Map<Int, Component>,
    forceNewLine: Boolean = false,
    allowPickingItem: Boolean = false
) : Component(
    forceNewLine,
    allowPickingItem
), Sequence<InMenuComponent> {

    constructor(components: List<Component>, forceNewLine: Boolean = false, allowPickingItem: Boolean = false) :
            this(
                components.mapIndexed { idx, component -> idx to component }.toMap(),
                forceNewLine,
                allowPickingItem
            )

    private val interactiveContent: MutableMap<Int, Component> = mutableMapOf()

    override fun onClick(p: OnClickParameter): Boolean {
        interactiveContent[p.index]
            ?.let { return it.onClick(p) }
        return allowPickingItem
    }

    /*
    * Not tested :)
    * */
    override fun iterator(): Iterator<InMenuComponent> {
        return sequence {
            var currentOffset = 0
            val children = components.toList()

            children.forEach { (index, child) ->
                currentOffset = max(currentOffset, index)

                // Jump to a new line if new line is forced
                if (child.forceNewLine) { currentOffset = (ceil(currentOffset / 9.0) * 9).toInt() }

                when (child) {
                    is UnitComponent -> yield(InMenuComponent(currentOffset, child))
                    is ComponentList -> child.forEach { yield(it.withOffset(currentOffset)) }
                }
            }
        }.iterator()
    }

}