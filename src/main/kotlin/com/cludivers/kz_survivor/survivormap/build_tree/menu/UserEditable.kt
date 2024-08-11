package com.cludivers.kz_survivor.survivormap.build_tree.menu

import com.cludivers.kz_survivor.menus.*
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
    fun getMenuComponent(): MenuComponent {
        return getMenuComponent(this)
    }

    fun fetchPreviewComponent(): SingleMenuComponent {
        return SingleMenuComponent(fetchIcon()) { p -> MenuDisplayer(p.player, getMenuComponent(this)).open(SurvivorMenuHandler::registerMenu) }
    }

    companion object {

        private val classMenusBuilders: MutableMap<KClass<out Any>, (Any) -> MenuComponent> = mutableMapOf()

        fun getMenuComponent(instance: Any?): MenuComponent {
            if (instance == null) return SingleMenuComponent.EMPTY
            val componentBuilder = classMenusBuilders[instance::class]
            if (componentBuilder != null) {
                return componentBuilder(instance)
            }

            val newBuilder = getMenuComponentBuilder(instance::class)
            classMenusBuilders[instance::class] = newBuilder
            return newBuilder(instance)
        }

        fun<T : Any> collectMenuFields(clazz: KClass<T>): Collection<KProperty1<T, *>> {
            return clazz.memberProperties.filter { it.hasAnnotation<EditableAttribute>() }
        }

        fun collectMenuItems(clazz: KClass<out Any>): List<(Any) -> MenuComponent> {
            return collectMenuFields(clazz).map {
                when(it.findAnnotation<EditableAttribute>()?.displayMode) {
                    DisplayModes.PREFER_FULL_DISPLAY -> { parentInstance: Any ->
                        getMenuComponent(it.getter.call(parentInstance)) }
                    else -> { parentInstance: Any -> fetchPreviewComponent(it.getter.call(parentInstance)) }
                }
            }
        }

        fun getMenuComponentBuilder(clazz: KClass<out Any>): (Any) -> MenuComponent {
            Bukkit.broadcast(Component.text("${clazz.simpleName}"))

            return if (clazz.isSubclassOf(UserEditable::class)) {
                val clazzItems = collectMenuItems(clazz)
                // Maybe augment it with instance based stuff?
                // Function like (instance.fillExtraComponent(Map<Int, MenuComponent>)) ?
                return { instance: Any -> MultiMenuComponent(clazzItems.map { it(instance) }) }
            } else {
                buildAttributeMenuComponent(clazz)
            }
        }

        @Suppress("UNCHECKED_CAST")
        private fun buildAttributeMenuComponent(clazz: KClass<out Any>): (Any) -> MenuComponent {
            return when (clazz) {
                List::class, ArrayList::class ->
                    { instance -> EditableListComponent(instance as List<Any>) }
                Int::class -> { _ -> SingleMenuComponent(CustomIconBuild(clazz.simpleName!!, Material.BRICK_WALL)) {} }
                else -> { _ -> SingleMenuComponent.EMPTY }
            }
        }

        fun fetchPreviewComponent(instance: Any?): SingleMenuComponent {
            if (instance == null) return SingleMenuComponent.EMPTY
            if (instance is UserEditable) {
                return instance.fetchPreviewComponent()
            }
            return when (instance::class) {
                Int::class -> {
                    val name: String = (instance as? KProperty<*>)?.name ?: "property"
                    SingleMenuComponent(
                        CustomIconBuild(
                            "Edit $name",
                            Material.BRICK_WALL
                        )
                    ) { p -> p.player.sendMessage(Component.text("Type the new amount :D (not wired yet)")) }
                }
                else -> SingleMenuComponent.EMPTY
            }
        }
    }
}