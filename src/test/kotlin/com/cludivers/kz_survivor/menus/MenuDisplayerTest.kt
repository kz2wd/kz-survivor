package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.KzSurvivor
import com.cludivers.kz_survivor.menus.advanced.ComponentList
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.SurvivorMapBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.UserEditable
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.event.inventory.InventoryOpenEvent
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.mockbukkit.mockbukkit.entity.PlayerMock
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class MenuDisplayerTest {
    private lateinit var world: World
    private lateinit var mock: ServerMock

    @BeforeEach
    fun setUp() {
        mock= MockBukkit.mock()
        val plugin = MockBukkit.load(KzSurvivor::class.java)
        plugin.isUnitTestMode = true
        world = mock.addSimpleWorld("test")

    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun openMenuLauncher(){
        try {
            openMenuTest()
        } catch(e: UnimplementedOperationException){
            e.printStackTrace()
        }
    }

    private fun openMenuTest() {

        val player: PlayerMock = mock.addPlayer()
        player.setItemInHand(SurvivorMenuHandler.mainMenuOpening)

        val unitCompoList: List<CustomIconBuild> = listOf(
            CustomIconBuild("1", Material.ARROW),
            CustomIconBuild("2", Material.ARROW),
            CustomIconBuild("3", Material.ARROW)
        )

        val component = ComponentList(unitCompoList.map { UnitComponent(it) {} })

        unitCompoList.zip(component.toList()).forEachIndexed { i, (icon, inMenu) ->
            assertEquals(i, inMenu.index)
            assertEquals(icon, inMenu.component.icon)
        }
        val inventory = mock.createInventory(player, 54)

        val displayer = MenuDisplayer(player,  "Map Editor Menu test", component, inventory = inventory)
        displayer.open(SurvivorMenuHandler::registerMenu, false)
        mock.pluginManager.assertEventFired(InventoryOpenEvent::class.java)

        assertNotEquals(0, player.openInventory.topInventory.contents.filterNotNull().size)

    }

    @Test
    fun mapMenuLauncher(){
        try {
            mapMenuTest()
        } catch(e: UnimplementedOperationException){
            e.printStackTrace()
        }
    }

    private fun mapMenuTest(){

        val player: PlayerMock = mock.addPlayer()
        player.setItemInHand(SurvivorMenuHandler.mainMenuOpening)

        val map = SurvivorMapBuild()

        val inventory = mock.createInventory(player, 54)
        val mapComponent = UserEditable.getMenuComponent(map) as ComponentList
        val displayer = MenuDisplayer(player,  "Map Editor Menu test", mapComponent, inventory = inventory)
        displayer.open(SurvivorMenuHandler::registerMenu, false)
        mock.pluginManager.assertEventFired(InventoryOpenEvent::class.java)

        assertNotEquals(0, player.openInventory.topInventory.contents.filterNotNull().size)

        mapComponent.iterator().asSequence().toList().forEach {
            val itemstack = player.openInventory.getItem(it.index)
            assertEquals(it.component.icon.buildItemStack(), itemstack)
        }

    }
}