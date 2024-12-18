package com.cludivers.kz_survivor.menus.paginated

import com.cludivers.kz_survivor.menus.InMenuComponent
import org.bukkit.inventory.Inventory


class Page(private val components: List<InMenuComponent>) {

    fun apply(inventory: Inventory) {
        components.forEach {
            it.insert(inventory)
        }
    }

}