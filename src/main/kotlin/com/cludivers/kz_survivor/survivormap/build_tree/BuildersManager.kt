package com.cludivers.kz_survivor.survivormap.build_tree

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

@Entity
class BuildersManager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Transient
    var connectedBuilders: MutableList<Player> = mutableListOf()

    fun builderJoin(player: Player) {}

    fun builderLeft(player: Player) {}

    fun inviteBuilders(player: Player) {}

    fun kickBuilder(player: Player) {}

    fun chatBuilders(text: Component){
        connectedBuilders.forEach { it.sendMessage(text) }
    }


}