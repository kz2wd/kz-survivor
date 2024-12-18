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


class MenuDisplayer(
    private val player: Player,
    private val name: String,
    private val mainComponent: menusComponent,
    val allowShiftClick: Boolean = false
) {
    private var inventories: MutableList<Inventory> = mutableListOf()

    private var currentPageIndex = 0
    private var maxPageIndex = 0

    /*
    * Not tested :)
    * */
    fun open(inventoryAdder: (MenuDisplayer, Inventory) -> Unit, delay: Boolean = true) {

        if (mainComponent !is MultiComponent) return

        // 5 rows of inventory size, times the cumulative current inventory index
        val inventoryGenerator = inventoryFiller(mainComponent.iterator()).iterator()
        if (!inventoryGenerator.hasNext()) return

        val firstInventory = inventoryGenerator.next()

        inventoryAdder(this, firstInventory)

        // Delay because some events will trigger menu opening, and some need it to be delayed
        // so default behavior is to delay to ensure no issues occur
        if (!delay) {
            player.closeInventory()
            player.openInventory(firstInventory)
        } else {
            Bukkit.getScheduler().runTask(plugin, Runnable {
                player.closeInventory()
                player.openInventory(firstInventory)
            })
        }

    }

    /*
    * Not tested :)
    * */
    private fun inventoryFiller(content: Iterator<InMenuComponent>): Sequence<Inventory> = sequence {
        inventories.add(Bukkit.createInventory(player, 54, Component.text("$name [page: ${0}]")))
        while (content.hasNext()) {
            val it = content.next()
            if (it.index <= (5 * 9 - 1) * (currentPageIndex + 1)) {
                yield(inventories[currentPageIndex])
                inventories.add(Bukkit.createInventory(player, 54, Component.text("$name [page: ${currentPageIndex}]")))
                it.withOffset(-5 * 9 * (currentPageIndex + 1)).insert(inventories[currentPageIndex + 1])
            }
            it.insert(inventories[currentPageIndex])
        }
    }

    fun scrollLeft() {
        if (currentPageIndex <= 0) return
        currentPageIndex--
        inventories[currentPageIndex]

    }

    fun scrollRight() {
        currentPageIndex++
    }


    fun close() {
        mainComponent.close()
    }

    fun update() {
        mainComponent.update()
    }

    fun onClick(index: Int, event: InventoryInteractEvent) {
        when (index) {
            in 0..<5 * 9 -> {
                val isItemPickingAllowed = mainComponent.onClick(OnClickParameter(event.whoClicked as Player, index))
                if (!isItemPickingAllowed) {
                    event.isCancelled = true
                }
                return
            }
            // Scroll left
            5 * 9 -> {
                // Open left menu

            }
            // Scroll right
            6 * 9 - 1 -> {
                // Open right menu
            }
        }
        event.isCancelled = true
    }

}