package com.cludivers.kz_survivor.survivormap.build_tree

import com.cludivers.kz_survivor.survivormap.build_tree.menu.UserEditable
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import java.io.File.separator

abstract class SBuildable: UserEditable {
    companion object {
        val SEPARATOR: String = "::"
    }

    var modifed: Boolean = true
    var isValid: Boolean = false

    val conditions: MutableList<MapCondition> = mutableListOf()


    abstract fun fetchName(): String

    open fun getChildren(): List<SBuildable> {
        return listOf()
    }


    /**
     *  Return failing condition with the path of the element that failed
     */
    fun validate(parentPath: String): List<Pair<MapCondition, String>> {
        val currentName = "$parentPath$separator.${fetchName()}"
        return conditions.filterNot { it.asBoolean }.map { Pair(it, currentName) } + getChildren().flatMap { it.validate(currentName) }
    }

    /**
     * This part is about generating the playable map
     */

    private var splayable: SPlayable? = null

    abstract fun generateDraftInstance(): SPlayable

    fun getSPlayableDraftInstance(): SPlayable {
        if (splayable == null) {
            splayable = generateDraftInstance()
        }
        return splayable!!
    }

    abstract fun fetchFinalSPlayable(): SPlayable

    override fun triggerEdition() {
        TODO("Not yet implemented")
    }

}