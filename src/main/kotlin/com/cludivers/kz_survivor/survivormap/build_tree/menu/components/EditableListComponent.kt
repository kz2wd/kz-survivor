package com.cludivers.kz_survivor.survivormap.build_tree.menu.components

import com.cludivers.kz_survivor.menus.InlineScrollComponent
import com.cludivers.kz_survivor.menus.MenuComponent
import com.cludivers.kz_survivor.menus.MultiMenuComponent
import com.cludivers.kz_survivor.menus.SingleMenuComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import org.bukkit.Material

class EditableListComponent<T>(attribute: List<T>): MultiMenuComponent(fromAttribute(attribute)) {

    companion object{
        fun<T> fromAttribute(value: List<T>): Map<Int, MenuComponent> {
            return mapOf(
                0 to SingleMenuComponent(CustomIconBuild("Add new section", Material.BRICK_WALL)) {},
                1 to InlineScrollComponent(value.map { it })
            )

        }
    }

}