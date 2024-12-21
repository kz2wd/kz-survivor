package com.cludivers.kz_survivor

import com.cludivers.kz_survivor.menus.SurvivorMenuHandler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level.INFO
import java.util.logging.Logger

open class KzSurvivor : JavaPlugin() {
    companion object {
        lateinit var plugin: KzSurvivor
    }

    var isUnitTestMode: Boolean = false

    override fun onEnable() {
        // Plugin startup logic

        plugin = this
        Bukkit.getPluginManager().registerEvents(SurvivorMenuHandler, this)
        Logger.getAnonymousLogger().log(INFO, "KzSurvivor Plugin Enabled Z")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
