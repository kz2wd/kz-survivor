package com.cludivers.kz_survivor.survivormap.build_tree.menu

import com.cludivers.kz_survivor.menus.Component
import kotlin.reflect.KClass

enum class DisplayModes {
    PREFER_FULL_DISPLAY,
    PREFER_PREVIEW_DISPLAY,

    ;


    val classMenusBuilders: MutableMap<KClass<out Any>, (Any) -> Component> = mutableMapOf()

}