package com.cludivers.kz_survivor.survivormap.build_tree.menu

import com.cludivers.kz_survivor.menus.*
import com.cludivers.kz_survivor.menus.advanced.ComponentList
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.components.EditableListComponent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties

interface UserEditable {
    fun triggerEdition()
    fun fetchIcon(): CustomIconBuild
    fun fetchMenuComponent(): com.cludivers.kz_survivor.menus.Component {
        return getMenuComponent(this)
    }

    fun fetchPreviewComponent(): UnitComponent {
        return UnitComponent(fetchIcon()) { p -> MenuDisplayer(p.player, "", getMenuComponent(this)).open(SurvivorMenuHandler::registerMenu) }
    }

    companion object {

        fun getMenuComponent(instance: Any?): com.cludivers.kz_survivor.menus.Component {
            if (instance == null) return UnitComponent.EMPTY
            val componentBuilder = DisplayModes.PREFER_FULL_DISPLAY.classMenusBuilders[instance::class]
            if (componentBuilder != null) {
                return componentBuilder(instance)
            }

            val newBuilder = getMenuComponentBuilder(instance::class)
            DisplayModes.PREFER_FULL_DISPLAY.classMenusBuilders[instance::class] = newBuilder
            return newBuilder(instance)
        }

        fun<T : Any> collectMenuFields(clazz: KClass<T>): Collection<KProperty1<T, *>> {
            return clazz.memberProperties.filter { it.hasAnnotation<EditableAttribute>() }
        }

        fun collectMenuItems(clazz: KClass<out Any>): List<(Any) -> com.cludivers.kz_survivor.menus.Component> {
            return collectMenuFields(clazz).map {
                when(it.findAnnotation<EditableAttribute>()?.displayMode) {
                    DisplayModes.PREFER_FULL_DISPLAY -> { parentInstance: Any ->
                        getMenuComponent(it.getter.call(parentInstance)) }
                    else -> { parentInstance: Any -> fetchPreviewComponent(it.getter.call(parentInstance)) }
                }
            }
        }

        fun getMenuComponentBuilder(clazz: KClass<out Any>): (Any) -> com.cludivers.kz_survivor.menus.Component {
//            Bukkit.broadcast(Component.text("${clazz.simpleName}"))
            return if (clazz.isSubclassOf(UserEditable::class)) {
                val clazzItems = collectMenuItems(clazz)
                // Maybe augment it with instance based stuff?
                // Function like (instance.fillExtraComponent(Map<Int, MenuComponent>)) ?
                return { instance: Any -> ComponentList(clazzItems.map { it(instance) }) }
            } else {
                buildAttributeMenuComponent(clazz)
            }
        }

        @Suppress("UNCHECKED_CAST")
        private fun buildAttributeMenuComponent(clazz: KClass<out Any>): (Any) -> com.cludivers.kz_survivor.menus.Component {
//            Bukkit.broadcast(Component.text("Building component of ${clazz.simpleName}"))
            return when (clazz) {
                List::class, ArrayList::class ->
                    { instance -> UnitComponent.EMPTY } //EditableListComponent(instance as List<Any>) }
                Int::class -> { _ -> UnitComponent(CustomIconBuild(clazz.simpleName!!, Material.BRICK_WALL)) {} }
                else -> { _ -> UnitComponent.EMPTY }
            }
        }

        fun fetchPreviewComponent(instance: Any?): UnitComponent {
//            Bukkit.broadcast(Component.text("Fetching preview component of ${instance?.javaClass?.name ?: "class"}"))
            if (instance == null) return UnitComponent.EMPTY
            if (instance is UserEditable) {
                return instance.fetchPreviewComponent()
            }
            return when (instance::class) {
                List::class, ArrayList::class -> UnitComponent(CustomIconBuild("Edit ${instance::class.simpleName}", Material.STONE))
                { p -> MenuDisplayer(p.player, "", getMenuComponent(instance)).open(SurvivorMenuHandler::registerMenu)  }
                Int::class -> {
                    val name: String = (instance as? KProperty<*>)?.name ?: "property"
                    UnitComponent(
                        CustomIconBuild(
                            "Edit $name",
                            Material.BRICK_WALL
                        )
                    ) { p -> p.player.sendMessage(Component.text("Type the new amount :D (not wired yet)")) }
                }
                Material::class -> {
                    UnitComponent(CustomIconBuild("Edit ${instance::class.simpleName}", instance as Material)) {
                        p -> p.player.sendMessage(Component.text("Drop the replacing material (not wired yet)"))
                    }
                }
                String::class -> {
                    UnitComponent(CustomIconBuild("Edit ${instance::class.simpleName}", Material.NAME_TAG)) {
                            p -> p.player.sendMessage(Component.text("Type the new name (not wired yet)"))
                    }
                }
                else -> UnitComponent.EMPTY
            }
        }
    }
}