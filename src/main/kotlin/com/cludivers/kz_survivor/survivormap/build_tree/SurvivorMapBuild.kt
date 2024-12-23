package com.cludivers.kz_survivor.survivormap.build_tree

import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.advanced.ComponentList
import com.cludivers.kz_survivor.menus.UnitComponent
import com.cludivers.kz_survivor.survivormap.build_tree.menu.DisplayModes
import com.cludivers.kz_survivor.survivormap.build_tree.menu.EditableAttribute
import com.cludivers.kz_survivor.survivormap.build_tree.sectors.MapSectorBuild
import com.cludivers.kz_survivor.survivormap.build_tree.waves.WaveManagerBuild
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.*
import org.bukkit.Material
import java.util.UUID

@Entity
class SurvivorMapBuild: SBuildable() {

    companion object {
        const val DEFAULT_MAX_PLAYERS = 8
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var owner: UUID? = null

    @ElementCollection
    var maxMapElements: MutableMap<Class<*>, Int> = mutableMapOf()

//    @EditableAttribute
    var maxPlayers: Int = DEFAULT_MAX_PLAYERS

//    @EditableAttribute
    var icon: CustomIconBuild = CustomIconBuild("SurvivorMapNameHere", Material.COMPASS)

    @OneToOne
    var world: SurvivorWorld = SurvivorWorld()

    @OneToOne
//    @EditableAttribute
    var openingSection: MapSectorBuild = MapSectorBuild()

    @OneToMany
//    @EditableAttribute(displayMode = DisplayModes.PREFER_FULL_DISPLAY)
    var sectors: MutableList<MapSectorBuild> = mutableListOf()

    @OneToOne
//    @EditableAttribute
    var waveManager: WaveManagerBuild = WaveManagerBuild()

    override fun fetchName(): String {
        return icon.fetchName()
    }

    override fun getChildren(): List<SBuildable> {
        return sectors.toList()
    }

    override fun generateDraftInstance(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun fetchFinalSPlayable(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun fetchIcon(): CustomIconBuild {
        return icon
    }

    override fun fetchMenuComponent(): Component {
        val content = mutableMapOf<Int, Component>(
            0 to UnitComponent(icon) {},
        )
        return ComponentList(content.toMap())
    }

}