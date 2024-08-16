package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.KzSurvivor.Companion.plugin
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.Inventory
import kotlin.math.ceil
import kotlin.math.max

class MenuDisplayer(private val player: Player, private val name: String, private val mainComponent: MenuComponent, val allowShiftClick: Boolean = false) {
    private var inventory: Inventory? = null

    private var inventories: MutableList<Inventory> = mutableListOf()
    private var currentPageIndex = 0
    private var maxPageIndex = 0

    fun open(inventoryAdder: (MenuDisplayer, Inventory) -> Unit, delay: Boolean = true) {

        val content = mainComponent.getContent()

        // Choose good menu type & size depending on size
        // In case there is 1 element at index 0, max between max index required and size
        val size: Int? = content.maxByOrNull { it.key }?.key?.let { max(content.size, it) }
        if (size == null || size == 0) {
            return
        }

        // size + 1 because it starts at 0 in the inventory, so if 1 item at index 9 -> needs 2 rows
        val sizeNeeded = ceil( (size + 1) / 9.0).toInt() * 9

        // 54 is max inventory size in minecraft
        if (sizeNeeded <= 54) {
            fillBasicInventory(content, sizeNeeded)
        } else {
            fillInfiniteInventory(content)
        }

        inventoryAdder(this, inventory!!)


        // Delay because some events will trigger menu opening, and some need it to be delayed
        // so default behavior is to delay to ensure no issues occur
        if (!delay){
            player.closeInventory()
            player.openInventory(inventory!!)
        } else {
            Bukkit.getScheduler().runTask(plugin, Runnable {
                player.closeInventory()
                player.openInventory(inventory!!)
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