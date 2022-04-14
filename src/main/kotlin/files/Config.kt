package files

import filemanagement.SafeConfigManager

class Config : SafeConfigManager("config.yml", "SafeHungerGames") {
    override fun generateDefaults() {
        get().addDefault("General.KitsPath", null)
    }
}