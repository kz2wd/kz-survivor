package com.cludivers.kz_survivor.menus.paginated

import com.cludivers.kz_survivor.KzSurvivor
import com.cludivers.kz_survivor.menus.OnClickParameter
import com.cludivers.kz_survivor.menus.advanced.ComponentList
import com.cludivers.kz_survivor.survivormap.build_tree.SurvivorMapBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.UserEditable
import org.bukkit.World
import org.bukkit.entity.Player
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException
import kotlin.test.Test
import kotlin.test.assertEquals

class PaginatorTest {
    private lateinit var world: World
    private lateinit var mock: ServerMock

    @BeforeEach
    fun setUp() {
        mock = MockBukkit.mock()
        val plugin = MockBukkit.load(KzSurvivor::class.java)
        plugin.isUnitTestMode = true
        plugin.mockInventoryGetter = { player: Player, size: Int -> mock.createInventory(player, size)}
        world = mock.addSimpleWorld("test")

    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun paginatorLauncher(){
        try {
            paginatorTest()
        } catch(e: UnimplementedOperationException){
            e.printStackTrace()
        }
    }

    private fun paginatorTest() {
        val survivorMap = SurvivorMapBuild()
        val component = UserEditable.getMenuComponent(survivorMap) as ComponentList
        val player = mock.addPlayer()
        val inventory = mock.createInventory(player, 54)
        val paginator = Paginator(component, inventory)
        val result = paginator.pageGenerator(component.iterator())
        val page = result.take(1).iterator().next()
        val firstPageComponents = component.iterator().asSequence().take(45)
        firstPageComponents.toList().zip(page.components).forEach {
            assertEquals(it.first, it.second)
        }
        val clickZero = OnClickParameter(player, 0)

        assertEquals(1, paginator.pages.size)
        paginator.onClick(clickZero)
        assertEquals(1, paginator.pages.size)
        paginator.onClick(clickZero)
        assertEquals(1, paginator.pages.size)


    }

}