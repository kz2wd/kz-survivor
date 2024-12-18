package com.cludivers.kz_survivor.survivormap.build_tree

import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class CustomItemsBuild: SBuildable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0

    var icon: CustomIconBuild = CustomIconBuild()

    override fun fetchName(): String {
        return icon.name
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
        TODO("Not yet implemented")
    }
}