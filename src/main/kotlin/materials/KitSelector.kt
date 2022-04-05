package materials

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class KitSelector {
    private val itemStack: ItemStack = ItemStack(Material.CHEST)

    init {
        itemStack.itemMeta!!.setDisplayName("KitSelector")
    }
}