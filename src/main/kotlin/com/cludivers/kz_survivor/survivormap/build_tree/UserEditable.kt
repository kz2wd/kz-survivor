package com.cludivers.kz_survivor.survivormap.build_tree

import com.cludivers.kz_survivor.menus.MenuComponent

interface UserEditable {
    fun triggerEdition()
    fun fetchIcon(): CustomIconBuild
    fun getMenuComponent(): MenuComponent
}