package com.cludivers.kz_survivor.survivormap.build_tree.sectors

import com.cludivers.kz_survivor.menus.MenuComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.SBuildable
import com.cludivers.kz_survivor.survivormap.build_tree.menu.EditableAttribute
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.bukkit.Location
import org.bukkit.Material

@Entity
class CreatureSpawnerBuild: BuyableBuild() {

    @EditableAttribute
    var icon: CustomIconBuild = CustomIconBuild("Creature spawner", Material.SPAWNER)


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