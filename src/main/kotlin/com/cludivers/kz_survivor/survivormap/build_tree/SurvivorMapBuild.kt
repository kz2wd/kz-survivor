package com.cludivers.kz_survivor.survivormap.build_tree

import com.cludivers.kz_survivor.menus.MenuComponent
import com.cludivers.kz_survivor.menus.MultiMenuComponent
import com.cludivers.kz_survivor.menus.SingleMenuComponent
import com.cludivers.kz_survivor.survivormap.build_tree.sectors.MapSectorBuild
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.*
import java.util.UUID

@Entity
class SurvivorMapBuild: SBuildable() {

    companion object {
        val DEFAULT_MAX_PLAYERS = 8
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var owner: UUID? = null

    @ElementCollection
    var maxMapElements: MutableMap<Class<*>, Int> = mutableMapOf()

    var maxPlayers: Int = DEFAULT_MAX_PLAYERS

    var icon: CustomIconBuild = CustomIconBuild()

    @OneToOne
    var world: SurvivorWorld = SurvivorWorld()

    @OneToOne
    var openingSection: MapSectorBuild? = null

    @OneToMany
    var sectors: MutableList<MapSectorBuild> = mutableListOf()

    override fun fetchName(): String {
        return icon.fetchName()
    }

    override fun getChildren(): List<SBuildable> {
        return sectors.toList()
    }

    override fun generateDraftInstance(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun getFinalSPlayable(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun fetchIcon(): CustomIconBuild {
        return icon
    }

    override fun getMenuComponent(): MenuComponent {
        val content = mutableMapOf<Int, MenuComponent>(
            0 to SingleMenuComponent(icon) {},
        )
        return MultiMenuComponent(content.toMap())
    }

}