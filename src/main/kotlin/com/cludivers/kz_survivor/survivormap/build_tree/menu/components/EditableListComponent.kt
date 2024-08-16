package com.cludivers.kz_survivor.survivormap.build_tree.menu.components

import com.cludivers.kz_survivor.menus.InlineScrollComponent
import com.cludivers.kz_survivor.menus.MenuComponent
import com.cludivers.kz_survivor.menus.MultiMenuComponent
import com.cludivers.kz_survivor.menus.SingleMenuComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.UserEditable
import org.bukkit.Material

class EditableListComponent(attribute: List<Any>, newLine: Boolean = true): MultiMenuComponent(fromAttribute(attribute), newLine) {

    init {
        size = 9
    }

    companion object{
        fun fromAttribute(value: List<Any>): Map<Int, MenuComponent> {
            return mapOf(
                0 to SingleMenuComponent(CustomIconBuild("Add new section", Material.BRICK_WALL)) {},
                1 to InlineScrollComponent(value.map { UserEditable.fetchPreviewComponent(it) }, 6)
            )

        }
    }

}