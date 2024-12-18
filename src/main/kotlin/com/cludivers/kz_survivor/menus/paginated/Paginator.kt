package com.cludivers.kz_survivor.menus.paginated

import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.InMenuComponent
import com.cludivers.kz_survivor.menus.OnClickParameter
import com.cludivers.kz_survivor.menus.advanced.ComponentList
import org.bukkit.inventory.Inventory


class Paginator(private val component: ComponentList, private val inventory: Inventory): Component(false, false) {
    private val pages: MutableList<Page> = mutableListOf()

    private val pageSequence: Iterator<Page>
    init {
        pageSequence = pageGenerator(component.iterator()).iterator()
    }

    private var currentPageIndex = 0

    private fun scrollLeft() {
        tryMoveToPage(-1)
    }

    private fun scrollRight() {
        tryMoveToPage(1)
    }

    private fun tryMoveToPage(offset: Int){
        try {
            getPage(currentPageIndex + offset).apply(inventory)
            currentPageIndex += offset  //  we need increment ONLY IF getPage succeed
        } catch (_: IndexOutOfBoundsException) {
            // ignored
        }
    }

    private fun getPage(index: Int): Page {
        return when {
            index < 0 -> throw IndexOutOfBoundsException()
            index > pages.lastIndex -> tryGenerate()
            else ->  pages[index]
        }
    }

    private fun tryGenerate(): Page {
        if (pageSequence.hasNext()) {
            return pageSequence.next()
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    /*
    * Not tested :)
    * */
    private fun pageGenerator(content: Iterator<InMenuComponent>): Sequence<Page> = sequence {
        val currentPage: MutableList<InMenuComponent> = mutableListOf()
        while (content.hasNext()) {
            val it = content.next()
            if (it.index <= (5 * 9 - 1) * (currentPageIndex + 1)) {
                currentPage.add(it.withOffset(-5 * 9 * currentPageIndex))
            } else {
                yield(Page(currentPage))
                currentPage.clear()
            }
        }
    }

    override fun onClick(p: OnClickParameter): Boolean {
        when (p.index) {
            in 0..<5 * 9 -> {
                val isItemPickingAllowed = component.onClick(p)
                return isItemPickingAllowed
            }
            5 * 9 -> {
                scrollRight()
            }
            6 * 9 - 1 -> {
                scrollLeft()
            }
        }
        return false
    }


}