package com.cludivers.kz_survivor.survivormap.build_tree.menu.components

import com.cludivers.kz_survivor.menus.advanced.InlineScrollComponent
import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.UnitComponent
import com.cludivers.kz_survivor.menus.advanced.MultiComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.UserEditable
import org.bukkit.Material

abstract class EditableListComponent(attribute: List<Any>, newLine: Boolean = true): MultiComponent(newLine) {

    init {
        val size = 9
    }

    companion object{
        fun fromAttribute(value: List<Any>): Map<Int, Component> {
            return mapOf(
                0 to UnitComponent(CustomIconBuild("Add new section", Material.BRICK_WALL)) {},

            )

        }
    }

}