package com.blockycraft.blockyareyouplaying;

import com.blocky.auth.AuthPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerCheckTask implements Runnable {

    private final BlockyAreYouPlaying plugin;
    private final DatabaseManager dbManager;
    private final AuthPlugin authPlugin;

    public PlayerCheckTask(BlockyAreYouPlaying plugin, DatabaseManager dbManager) {
        this.plugin = plugin;
        this.dbManager = dbManager;
        
        Plugin authPluginInstance = plugin.getServer().getPluginManager().getPlugin("AuthPlugin");
        if (authPluginInstance instanceof AuthPlugin) {
            this.authPlugin = (AuthPlugin) authPluginInstance;
        } else {
            throw new RuntimeException("AuthPlugin not found!");
        }
    }

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (authPlugin.isAuthenticated(player)) {
                dbManager.logPlayer(player);
            }
        }
    }
}
