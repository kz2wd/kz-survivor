package com.cludivers.kz_survivor

import org.bukkit.Location
import org.bukkit.World
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import kotlin.test.Test

class HelloWorldTest {


    private lateinit var world: World

    @BeforeEach
    fun setUp() {
        val mock: ServerMock = MockBukkit.mock()
        val plugin = MockBukkit.load(KzSurvivor::class.java)
        world = mock.addSimpleWorld("test")
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun test() {
        world.createExplosion(Location(world, 0.0, 0.0, 0.0), 0.2f)
    }
}