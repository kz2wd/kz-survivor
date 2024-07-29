package com.cludivers.kz_survivor.survivormap.build_tree

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

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getChildren(): List<SBuildable> {
        TODO("Not yet implemented")
    }

    override fun generateDraftInstance(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun getFinalSPlayable(): SPlayable {
        TODO("Not yet implemented")
    }

//    var sectors: MutableList<MapSectorBuild> = mutableListOf()

}