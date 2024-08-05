package com.cludivers.kz_survivor.survivormap.build_tree.waves

import com.cludivers.kz_survivor.menus.MenuComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.SBuildable
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.*

@Entity
class WaveBuild: SBuildable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var mobsAmount: Int = 0

    @OneToMany
    var mobPool: MutableList<CustomMobBuild> = mutableListOf()

    var icon: CustomIconBuild = CustomIconBuild()

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

    override fun getMenuComponent(): MenuComponent {
        TODO("Not yet implemented")
    }
}