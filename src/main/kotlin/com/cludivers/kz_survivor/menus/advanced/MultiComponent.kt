package com.cludivers.kz_survivor.menus.advanced

import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.InMenuComponent

abstract class MultiComponent(
    forceNewLine: Boolean = false,
    allowPickingItem: Boolean = false
) : Component(
    forceNewLine,
    allowPickingItem
), Sequence<InMenuComponent>