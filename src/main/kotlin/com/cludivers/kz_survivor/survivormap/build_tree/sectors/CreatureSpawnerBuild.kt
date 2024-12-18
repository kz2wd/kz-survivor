package com.cludivers.kz_survivor.survivormap.build_tree.sectors

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.EditableAttribute
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.Entity
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

    override fun fetchFinalSPlayable(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun fetchIcon(): CustomIconBuild {
        return icon
    }

}