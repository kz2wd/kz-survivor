package com.cludivers.kz_survivor.menus.advanced

import com.cludivers.kz_survivor.data_structure.RangeItem
import com.cludivers.kz_survivor.data_structure.RangeMap
import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.InMenuComponent
import com.cludivers.kz_survivor.menus.UnitComponent


class UnitComponentIterator(private val content : List<Pair<Int, Component>>): Iterator<InMenuComponent> {

    var currentChildrenIndex = 0
    var currentSubIterator : Iterator<InMenuComponent>? = null

    override fun hasNext(): Boolean {
        return (currentChildrenIndex < content.size)
    }

    override fun next(): InMenuComponent {
        val currentIndex = content[currentChildrenIndex].first
        val currentComponent = content[currentChildrenIndex].second

        if (currentComponent is UnitComponent) {
            return InMenuComponent(currentIndex, currentComponent)
        }

        currentSubIterator = (currentComponent as MultiComponent).iterator()


    }
}