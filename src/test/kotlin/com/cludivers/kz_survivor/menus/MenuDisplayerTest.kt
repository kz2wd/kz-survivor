package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.KzSurvivor
import com.cludivers.kz_survivor.menus.advanced.ComponentList
import com.cludivers.kz_survivor.menus.paginated.Paginator
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.SurvivorMapBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.UserEditable
import org.bukkit.Material
import org.bukkit.World
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.mockbukkit.mockbukkit.entity.PlayerMock
import org.mockbukkit.mockbukkit.simulate.entity.PlayerSimulation
import kotlin.test.Test
import kotlin.test.assertEquals

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
        val paginator = Paginator(component, inventory)

        paginator.

        assert(false)

//        val displayer = MenuDisplayer(player,  "Map Editor Menu test", component)

//        mock.pluginManager.assertEventFired(InventoryOpenEvent::class.java)

    }
}