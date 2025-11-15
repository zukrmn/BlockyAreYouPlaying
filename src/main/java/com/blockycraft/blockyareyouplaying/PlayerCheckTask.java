package com.blockycraft.blockyareyouplaying;

import com.blockycraft.blockyauth.BlockyAuth;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerCheckTask implements Runnable {

    private final BlockyAreYouPlaying plugin;
    private final DatabaseManager dbManager;
    private final BlockyAuth blockyAuth;

    public PlayerCheckTask(BlockyAreYouPlaying plugin, DatabaseManager dbManager) {
        this.plugin = plugin;
        this.dbManager = dbManager;
        
        Plugin blockyAuthInstance = plugin.getServer().getPluginManager().getPlugin("BlockyAuth");
        if (blockyAuthInstance instanceof BlockyAuth) {
            this.blockyAuth = (BlockyAuth) blockyAuthInstance;
        } else {
            throw new RuntimeException("BlockyAuth not found!");
        }
    }

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (blockyAuth.isAuthenticated(player)) {
                dbManager.logPlayer(player);
            }
        }
    }
}
