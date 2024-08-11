package com.cludivers.kz_survivor.survivormap.build_tree.sectors

import com.cludivers.kz_survivor.survivormap.build_tree.CustomIconBuild
import com.cludivers.kz_survivor.survivormap.build_tree.menu.EditableAttribute
import com.cludivers.kz_survivor.survivormap.play_tree.SPlayable
import jakarta.persistence.Entity
import jakarta.persistence.Transient
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@Entity
class GameItemBuild: BuyableBuild() {

    @EditableAttribute
    @Transient
    var item: ItemStack = ItemStack(Material.WOODEN_AXE)

    override fun fetchName(): String {
        return item.itemMeta?.itemName()?.examinableName() ?: "unnamed item"
    }

    override fun generateDraftInstance(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun getFinalSPlayable(): SPlayable {
        TODO("Not yet implemented")
    }

    override fun fetchIcon(): CustomIconBuild {
        return CustomIconBuild.fromItemStack(item)
    }
}