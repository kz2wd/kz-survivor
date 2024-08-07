package com.cludivers.kz_survivor.survivormap.build_tree.menu

import com.cludivers.kz_survivor.menus.InlineScrollComponent
import com.cludivers.kz_survivor.menus.MenuComponent
import com.cludivers.kz_survivor.menus.SingleMenuComponent
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import org.bukkit.Material
import kotlin.reflect.KClass

interface UserEditable {
    fun triggerEdition()
    fun fetchIcon(): CustomIconBuild
    fun getMenuComponent(): MenuComponent

    companion object {
        fun collectMenuItems(instance: Any): List<MenuComponent> {
            val clazz = instance.javaClass
            return clazz.fields
                .map { field -> field.get(instance) }
                .filter { it is EditableAttribute }
                .map { getMenuComponent(it) }
        }

        fun getMenuComponent(instance: Any): MenuComponent {
            return if (instance is UserEditable){
                instance.getMenuComponent()
            } else {
                buildAttributeMenuComponent(instance)
            }
        }

        private fun buildAttributeMenuComponent(value: Any): MenuComponent {
            return when (value.javaClass) {
                List::class.java -> InlineScrollComponent((value as List).map { getMenuComponent(it) })
                else -> SingleMenuComponent(CustomIconBuild("No representation found", Material.DEBUG_STICK)) {}
            }
        }
    }
}