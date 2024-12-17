package com.cludivers.kz_survivor.survivormap.build_tree.waves

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.SBuildable
import com.cludivers.kz_survivor.survivormap.build_tree.menu.EditableAttribute
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.*
import org.bukkit.Material

@Entity
class WaveManagerBuild: SBuildable() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @OneToMany
    @EditableAttribute
    var waves: MutableList<WaveBuild> = mutableListOf()

    @EditableAttribute
    var icon: CustomIconBuild = CustomIconBuild("Wave Manager", Material.ZOMBIE_HEAD)

    override fun fetchName(): String {
        return icon.name
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
}