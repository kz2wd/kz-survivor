package com.cludivers.kz_survivor.survivormap.build_tree.sectors

import com.cludivers.kz_survivor.survivormap.build_tree.SBuildable
import com.cludivers.kz_survivor.survivormap.build_tree.menu.EditableAttribute
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.bukkit.Location

@Entity
abstract class BuyableBuild: SBuildable() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @EditableAttribute
    @Transient
    var location: Location? = null


    override fun generateDraftInstance(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun getFinalSPlayable(): SPlayable {
        TODO("Not yet implemented")
    }

}