package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.menus.Component as menusComponent
import com.cludivers.kz_survivor.KzSurvivor.Companion.plugin
import com.cludivers.kz_survivor.menus.advanced.MultiComponent
import com.cludivers.kz_survivor.menus.paginated.Paginator
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.Inventory


class MenuDisplayer(
    private val player: Player,
    private val name: String,
    private val mainComponent: menusComponent,
    val allowShiftClick: Boolean = false
) {
    private val inventory: Inventory = Bukkit.createInventory(player, 54, Component.text("$name [page: ${0}]"))
    private lateinit var paginator: Paginator

    /*
    * Not tested :)
    * */
    fun open(inventoryAdder: (MenuDisplayer, Inventory) -> Unit, delay: Boolean = true) {

        if (mainComponent !is MultiComponent) return

        paginator = Paginator(mainComponent, inventory)

        inventoryAdder(this, inventory)

        // Delay because some events will trigger menu opening, and some need it to be delayed
        // so default behavior is to delay to ensure no issues occur
        if (!delay) {
            player.closeInventory()
            player.openInventory(inventory)
        } else {
            Bukkit.getScheduler().runTask(plugin, Runnable {
                player.closeInventory()
                player.openInventory(inventory)
            })
        }

    }

    fun close() {
        paginator.close()
    }

    fun update() {
        paginator.update()
    }

    fun onClick(index: Int, event: InventoryInteractEvent) {
        val isItemPickingAllowed = paginator.onClick(OnClickParameter(event.whoClicked as Player, index))
        if (!isItemPickingAllowed) {
            event.isCancelled = true
        }
    }

}