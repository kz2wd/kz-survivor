package com.cludivers.kz_survivor.menus.paginated

import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.InMenuComponent
import com.cludivers.kz_survivor.menus.OnClickParameter
import org.bukkit.inventory.Inventory


class Page(private val components: List<InMenuComponent>): Component(false, false) {

    private val componentMap: Map<Int, Component> = components.associate { Pair(it.index, it.component) }

    fun apply(inventory: Inventory) {
        components.forEach {
            it.insert(inventory)
        }
    }

    override fun onClick(p: OnClickParameter): Boolean {
        return componentMap[p.index]?.onClick(p) ?: false
    }

}