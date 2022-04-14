import files.Config
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class SafeHungerGames: JavaPlugin() {

    val config: Config = Config()

    val kitController: KitController = KitController(this, config)

    override fun onEnable() {
        sendMessageWithPrefix("&a Plugin Enabled")
        kitController.loadKits()
    }

    override fun onLoad() {
        sendMessageWithPrefix("&a Plugin Loaded")
    }

    override fun onDisable() {
        sendMessageWithPrefix("&c Plugin Disabled")
    }

    fun sendMessageWithPrefix(message: String){
        Bukkit.getServer().consoleSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bSafe&6Hungergames&7] $message"))
    }
}