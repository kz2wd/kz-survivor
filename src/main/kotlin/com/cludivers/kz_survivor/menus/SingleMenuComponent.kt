package com.cludivers.kz_survivor.menus

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild

class SingleMenuComponent(val icon: CustomIconBuild, allowPickingItem: Boolean = false, private val onClick: Runnable): MenuComponent(false,  1,
    allowPickingItem
) {
    override fun getContent(): Map<Int, CustomIconBuild> {
        return mapOf(0 to icon)
    }

    override fun onClick(index: Int): Boolean {
        onClick.run()
        return allowPickingItem
    }

}