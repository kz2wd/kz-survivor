package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object SurvivorMenuHandler : Listener {

    private val mainMenuOpening: ItemStack = ItemStack(Material.STICK)

    private val allInventoryMenus = HashMap<Inventory, MenuDisplayer>()

    fun registerMenu(menuDisplayer: MenuDisplayer, inventory: Inventory) {
        allInventoryMenus[inventory] = menuDisplayer
    }

    fun closeMenu(inventory: Inventory?) {
        allInventoryMenus.remove(inventory)
    }

    fun getMenuDisplay(inventory: Inventory?): MenuDisplayer? {
        return allInventoryMenus[inventory]
    }

    @EventHandler
    fun onInitialMenuOpening(event: PlayerInteractEvent) {
        if (mainMenuOpening != event.item) {
            return
        }
        val menuContent = mapOf(
            0 to SingleMenuComponent(
                CustomIconBuild(
                    "Icon dummy",
                    Material.STONE
                )
            ) { Bukkit.broadcast(Component.text("Hello Menu :D")) },
            10 to SingleMenuComponent(
                CustomIconBuild(
                    "Huya",
                    Material.STONE
                )
            ) { Bukkit.broadcast(Component.text("No :(")) }
        )

        val menu = MultiMenuComponent(menuContent, false, 1, false)
        MenuDisplayer(event.player, menu).open(::registerMenu)
    }

    @EventHandler
    fun onMenuInteraction(event: InventoryClickEvent) {

        // Let the player still interact with his own inventory
        val inventory = if (event.action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            event.view.topInventory
        } else event.clickedInventory

        val menuDisplay = getMenuDisplay(inventory) ?: return
        if (!menuDisplay.allowShiftClick && event.isShiftClick) {
            event.isCancelled = true
            return
        }
        menuDisplay.onClick(event.rawSlot, event)

    }

    @EventHandler
    fun onMenuDragItem(event: InventoryDragEvent) {
        // Let the player still interact with his own inventory
        val isTopInventoryAffected = !event.rawSlots.none { it < event.view.topInventory.size }
        val inventory = if (isTopInventoryAffected) { event.view.topInventory } else { event.view.bottomInventory }
        val menuDisplay = getMenuDisplay(inventory) ?: return
        menuDisplay.onClick(event.rawSlots.first(), event)
    }

    @EventHandler
    fun onMenuClose(event: InventoryCloseEvent) {
        closeMenu(event.inventory)
    }
}