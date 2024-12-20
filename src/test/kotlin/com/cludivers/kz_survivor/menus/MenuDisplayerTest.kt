package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.KzSurvivor
import com.cludivers.kz_survivor.menus.SurvivorMenuHandler
import com.cludivers.kz_survivor.survivormap.build_tree.SurvivorMapBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.UserEditable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryOpenEvent
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.mockbukkit.mockbukkit.entity.PlayerMock
import org.mockbukkit.mockbukkit.simulate.entity.PlayerSimulation
import org.junit.jupiter.api.Test

class MenuDisplayerTest {
    private lateinit var world: World
    private lateinit var mock: ServerMock

    @BeforeEach
    fun setUp() {
        mock= MockBukkit.mock()
        val plugin = MockBukkit.load(KzSurvivor::class.java)
        world = mock.addSimpleWorld("test")

    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun test() {
        val player: PlayerMock = mock.addPlayer()
        player.setItemInHand(SurvivorMenuHandler.mainMenuOpening)

        val playerSimulation = PlayerSimulation(player)
        val map = SurvivorMapBuild()
        MenuDisplayer(player,  "Map Editor Menu test", UserEditable.getMenuComponent(map)).open(SurvivorMenuHandler::registerMenu, false)
        System.err.println(player.openInventory.countSlots())
        mock.pluginManager.assertEventFired(InventoryOpenEvent::class.java)



    }
}