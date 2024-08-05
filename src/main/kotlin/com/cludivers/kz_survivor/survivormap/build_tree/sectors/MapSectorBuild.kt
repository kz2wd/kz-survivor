package com.cludivers.kz_survivor.survivormap.build_tree.sectors

import com.cludivers.kz_survivor.menus.MenuComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.SBuildable
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class MapSectorBuild: SBuildable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @OneToMany
    var doors: MutableList<SectorDoorBuild> = mutableListOf()


    var icon: CustomIconBuild = CustomIconBuild()

    override fun fetchName(): String {
        return icon.name
    }
    override fun fetchIcon(): CustomIconBuild {
        return icon
    }

    override fun generateDraftInstance(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun getFinalSPlayable(): SPlayable {
        TODO("Not yet implemented")
    }



    override fun getMenuComponent(): MenuComponent {
        TODO("Not yet implemented")
    }

}