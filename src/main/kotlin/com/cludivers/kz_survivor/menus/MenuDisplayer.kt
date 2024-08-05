package com.cludivers.kz_survivor.menus

import com.cludivers.prototyping.main
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import kotlin.math.ceil
import kotlin.math.max

class MenuDisplayer(private val player: Player, private val mainComponent: MenuComponent, val allowShiftClick: Boolean = false) {
    private var inventory: Inventory? = null

    fun open(inventoryAdder: (MenuDisplayer, Inventory) -> Unit) {

        val content = mainComponent.getContent()

        // Choose good menu type & size depending on size
        // In case there is 1 element at index 0, max between max index required and size
        val size: Int? = content.maxByOrNull { it.key }?.key?.let { max(content.size, it) }
        if (size == null || size == 0) {
            return
        }


        val sizeNeeded = ceil( size / 9.0).toInt() * 9

        if (inventory == null){
            inventory = Bukkit.createInventory(player, sizeNeeded)
        }
        content.forEach { inventory!!.setItem(it.key, it.value.buildItemStack()) }

        inventoryAdder(this, inventory!!)
        player.openInventory(inventory!!)
    }

    fun close() {
        mainComponent.close()
    }

    fun update() {
        mainComponent.update()
    }

    fun onClick(index: Int, event: InventoryInteractEvent) {
        val isItemPickingAllowed = mainComponent.onClick(index)
        if (!isItemPickingAllowed) { event.isCancelled = true }
    }

}