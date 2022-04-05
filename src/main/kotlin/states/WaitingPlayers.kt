package states

import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class WaitingPlayers: State {
    override fun start(): State {
        TODO("Not yet implemented")
    }

    override fun end(): State {
        TODO("Not yet implemented")
    }

    @EventHandler
    fun onPlayerInteract(playerInteractEvent: PlayerInteractEvent){
        var item = playerInteractEvent.item!!
        if(item.hasItemMeta() && !item.itemMeta!!.displayName.equals("KitSelector", true))
        playerInteractEvent.isCancelled = true
    }

    @EventHandler
    fun onEntityDamagedEvent(entityDamageByEntityEvent: EntityDamageByEntityEvent){
        entityDamageByEntityEvent.isCancelled = true
    }
}