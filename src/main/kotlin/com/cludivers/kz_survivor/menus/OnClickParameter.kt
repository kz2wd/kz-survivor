package com.cludivers.kz_survivor.menus

import org.bukkit.entity.Player

data class OnClickParameter(val player: Player, var index: Int) {
    fun withIndex(newIndex: Int): OnClickParameter {
        index = newIndex
        return this
    }
}


