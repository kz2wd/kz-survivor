package com.cludivers.kz_survivor.menus

data class InMenuComponent(var index: Int, val component: UnitComponent) {
    fun withOffset(offset: Int): InMenuComponent {
        index += offset
        return this
    }
}
