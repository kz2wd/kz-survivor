package com.cludivers.kz_survivor.menus.paginated

import com.cludivers.kz_survivor.menus.Component
import com.cludivers.kz_survivor.menus.InMenuComponent
import com.cludivers.kz_survivor.menus.OnClickParameter
import com.cludivers.kz_survivor.menus.UnitComponent
import com.cludivers.kz_survivor.menus.advanced.ComponentList
import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory


class Paginator(component: ComponentList, private val inventory: Inventory): Component(false, false) {
    val pages: MutableList<Page> = mutableListOf()

    private val pageSequence: Iterator<Page>
    init {
        pageSequence = pageGenerator(component.iterator()).iterator()

        tryMoveToPage(0)
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
            println("Page $currentPageIndex out of bounds")
        }
    }

    private fun getPage(index: Int): Page {
        return when {
            index < 0 -> throw IndexOutOfBoundsException()
            index > pages.lastIndex -> {
                val page = tryGenerate()
                pages.add(page)
                page
            }
            else -> pages[index]
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
    fun pageGenerator(content: Iterator<InMenuComponent>): Sequence<Page> = sequence {
        val currentPage: MutableList<InMenuComponent> = mutableListOf()
        while (content.hasNext()) {
            val it = content.next()
            if (it.index <= (PAGE_SIZE - 1) * (currentPageIndex + 1)) {
                currentPage.add(it.withOffset(-PAGE_SIZE * currentPageIndex))
            } else {
                currentPage.add(LEFT_ARROW)
                currentPage.add(RIGHT_ARROW)
                yield(Page(currentPage))
                currentPage.clear()
                currentPage.add(it.withOffset(-PAGE_SIZE * (currentPageIndex + 1)))
            }
        }
        yield(Page(currentPage))
    }

    override fun onClick(p: OnClickParameter): Boolean {
        try {
            val page = getPage(currentPageIndex)
            return page.onClick(p)
        } catch (_: IndexOutOfBoundsException) {
            return false
        }
    }

    companion object {
        const val PAGE_SIZE = 5 * 9

        private const val LEFT_ARROW_INDEX = 5 * 9
        private const val RIGHT_ARROW_INDEX = 6 * 9 - 1

        val LEFT_ARROW = InMenuComponent(LEFT_ARROW_INDEX, UnitComponent(CustomIconBuild("Scroll left", Material.BOW)) {})
        val RIGHT_ARROW = InMenuComponent(RIGHT_ARROW_INDEX, UnitComponent(CustomIconBuild("Scroll right", Material.ANVIL)) {})

    }

}