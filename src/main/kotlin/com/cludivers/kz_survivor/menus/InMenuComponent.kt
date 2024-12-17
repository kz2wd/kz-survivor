package com.cludivers.kz_survivor.menus

import org.bukkit.inventory.Inventory

data class InMenuComponent(var index: Int, val component: UnitComponent) {

    fun withOffset(offset: Int): InMenuComponent {
        index += offset
        return this
    }

    fun insert(inventory: Inventory){
        inventory.setItem(index, component.icon.buildItemStack())
    }
}
