package com.cludivers.kz_survivor.survivormap.build_tree

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.bukkit.World

@Entity
class SurvivorWorld {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Transient
    var playWorld: World? = null

    fun save() {
        // Todo
    }

    fun load() {
        // Todo
    }
}