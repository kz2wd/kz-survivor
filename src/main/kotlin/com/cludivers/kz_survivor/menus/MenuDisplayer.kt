package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.menus.Component as menusComponent
import com.cludivers.kz_survivor.KzSurvivor.Companion.plugin
import com.cludivers.kz_survivor.menus.advanced.MultiComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import net.kyori.adventure.text.Component
import org.apache.commons.lang3.mutable.Mutable
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.Inventory


class MenuDisplayer(private val player: Player, private val name: String, private val mainComponent: menusComponent, val allowShiftClick: Boolean = false) {
    private var inventories: MutableList<Inventory> = mutableListOf()

    private var currentPageIndex = 0
    private var maxPageIndex = 0

    fun open(inventoryAdder: (MenuDisplayer, Inventory) -> Unit, delay: Boolean = true) {

        if (mainComponent !is MultiComponent) { return }

        inventories.add(Bukkit.createInventory(player, 54, Component.text("$name [page: ${0}]")))

        val firstInventory = inventories[0]



        inventoryAdder(this, firstInventory)


        // Delay because some events will trigger menu opening, and some need it to be delayed
        // so default behavior is to delay to ensure no issues occur
        if (!delay){
            player.closeInventory()
            player.openInventory(firstInventory)
        } else {
            Bukkit.getScheduler().runTask(plugin, Runnable {
                player.closeInventory()
                player.openInventory(firstInventory)
            })
        }

    }

    private fun fillInfiniteInventory(content: Map<Int, CustomIconBuild>) {

        content.forEach { (key, value) ->
            val index = key / 45
            val currentInventory: Inventory = inventories.getOrNull(index) ?: run {
                inventories[index] = Bukkit.createInventory(player, 54, Component.text("$name [page: ${index}]"))
                inventories[index]
            }
            currentInventory.setItem(key, value.buildItemStack())
        }
    }

    private fun fillBasicInventory(content: Map<Int, CustomIconBuild>, size: Int){
        if (inventory == null){
            inventory = Bukkit.createInventory(player, size, Component.text(name))
        }

        content.forEach { inventory!!.setItem(it.key, it.value.buildItemStack()) }

    }

    fun close() {
        mainComponent.close()
    }

    fun update() {
        mainComponent.update()
    }

    fun onClick(index: Int, event: InventoryInteractEvent) {
        val isItemPickingAllowed = mainComponent.onClick(OnClickParameter(event.whoClicked as Player, index))
        if (!isItemPickingAllowed) { event.isCancelled = true }
    }

}