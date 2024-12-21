package com.cludivers.kz_survivor.survivormap.build_tree

import com.cludivers.kz_survivor.KzSurvivor
import com.cludivers.kz_survivor.menus.Component as MenuComponent
import com.cludivers.kz_survivor.menus.UnitComponent
import com.cludivers.kz_survivor.survivormap.build_tree.menu.EditableAttribute
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.Embeddable
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@Embeddable
class CustomIconBuild(@EditableAttribute var name: String, @EditableAttribute var icon: Material) : SBuildable() {
    constructor() : this("Unnamed", Material.FLOWERING_AZALEA) {

    }

    @EditableAttribute
    var description: String = "No description"

    override fun fetchName(): String {
        return name
    }

    override fun generateDraftInstance(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun fetchFinalSPlayable(): SPlayable {
        TODO("Not yet implemented")
    }


    override fun fetchIcon(): CustomIconBuild {
        return this
    }

    override fun fetchMenuComponent(): MenuComponent {
        return UnitComponent(this) {}
    }

    fun buildItemStack(): ItemStack {
        val item = ItemStack.of(icon)
        // Have to do this because of unimplemented methods
        if (KzSurvivor.plugin.isUnitTestMode) return item
        item.editMeta { it.itemName(Component.text(name)) }
        item.editMeta { it.lore(mutableListOf(Component.text(description))) }
        return item
    }

    companion object {
        fun fromItemStack(item: ItemStack): CustomIconBuild {
            return CustomIconBuild(
                item.itemMeta?.itemName()?.examinableName() ?: "unnamed item",
                item.type
                )
        }
    }
}