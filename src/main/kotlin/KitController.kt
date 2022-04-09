import filemanagement.SafeConfigManager
import org.bukkit.entity.Player
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile
import java.util.zip.ZipFile

class KitController(private val mainInstance: SafeHungerGames, private val configManager: SafeConfigManager) {

    private val kits: LinkedHashMap<String, SafeKit> = LinkedHashMap()

    fun loadKits() {
        val kitsFolder: String? = configManager.get().getString("General.KitsPath")
        val usedKitsFolderPath = if (kitsFolder.isNullOrBlank()) "${mainInstance.dataFolder.canonicalPath}/kits" else kitsFolder
        val kitsPath =
            File(usedKitsFolderPath)
        if (!kitsPath.exists())
            kitsPath.mkdir()
        val kitsFiles = kitsPath.list()
        if (!kitsFiles.isNullOrEmpty()) {
            lateinit var currentKit: JarFile
            lateinit var kitInstance: SafeKit
            lateinit var kitPath: String
            kitsFiles.forEach {
                if (it.endsWith("jar", true)) {
                    kitPath = "${kitsPath.canonicalPath}/$it"
                    currentKit = JarFile(kitPath)
                    for (entry in currentKit.entries()) {
                        if (entry.name.contentEquals("kitConfig.yml")) {if (kitsFolder.isNullOrBlank()) "${mainInstance.dataFolder.canonicalPath}/kits" else kitsFolder
                            readConfigFile(ZipFile(kitPath).getInputStream(entry)).forEach { kitClassPath ->
                                kitInstance = Class.forName(kitClassPath, true, URLClassLoader(Array<URL>(1){File(usedKitsFolderPath).toURI().toURL()}, javaClass.classLoader)).getDeclaredConstructor().newInstance() as SafeKit
                                if(!kits.containsKey(kitInstance.kitName)){
                                    registerKitEvents(kitInstance)
                                    kits[kitInstance.kitName] = kitInstance
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun readConfigFile(inputStream: InputStream): List<String> {
        val reader = BufferedReader(inputStream.reader())
        val content = ArrayList<String>()
        try {
            var line = reader.readLine()
            while (line != null) {
                if (line.endsWith("]")) {
                    line.split("=")[1].split(",").forEach {
                        if (!it.contentEquals("[") && !it.contentEquals("]")) {
                            content.add(it)
                        }
                    }
                } else {
                    if (line.contains("[")) {
                        line.split("=")[1].split(",").forEach {
                            if (!it.contentEquals("[")) {
                                content.add(it)
                            }
                        }
                    } else {
                        line.split(",").forEach{ element ->
                            if(!element.isNullOrBlank()){
                                content.add(element)
                            }
                        }
                    }
                }
            }
            return content;
        } finally {
            reader.close()
            inputStream.close()
        }
    }

    private fun registerKitEvents(safeKit: SafeKit){
        mainInstance.server.pluginManager.registerEvents(safeKit, mainInstance)
        mainInstance.sendMessageWithPrefix("&a Kit ${safeKit.kitName} registered")
    }

    fun giveKit(player: Player, kitName: String){
        kits[kitName]!!.addPlayer(player)
    }
}