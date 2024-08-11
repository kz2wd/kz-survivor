package com.cludivers.kz_survivor.survivormap.build_tree.waves

import com.cludivers.kz_survivor.menus.MenuComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.SBuildable
import com.cludivers.kz_survivor.survivormap.build_tree.menu.EditableAttribute
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.*
import org.bukkit.Material

@Entity
class WaveBuild: SBuildable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @EditableAttribute
    var mobsAmount: Int = 0

    @OneToMany
    @EditableAttribute
    var mobPool: MutableList<CustomMobBuild> = mutableListOf()

    @EditableAttribute
    var icon: CustomIconBuild = CustomIconBuild("Wave X", Material.SKELETON_SPAWN_EGG)

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