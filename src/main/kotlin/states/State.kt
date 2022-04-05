package states

import org.bukkit.event.Listener

interface State: Listener {
    fun start(): State
    fun end(): State
}