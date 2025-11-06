package com.blockycraft.blockyareyouplaying;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.io.File;

public class BlockyAreYouPlaying extends JavaPlugin {

    private DatabaseManager dbManager;
    private int checkInterval;

    @Override
    public void onEnable() {
        setupConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        Configuration config = new Configuration(configFile);
        config.load();
        checkInterval = config.getInt("check_interval", 60);

        dbManager = new DatabaseManager(getDataFolder().getAbsolutePath() + "/players.db");

        long intervalTicks = checkInterval * 20L; // Convert seconds to ticks
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerCheckTask(this, dbManager), intervalTicks, intervalTicks);

        System.out.println("[BlockyAreYouPlaying] Plugin has been enabled.");
    }

    private void setupConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getDataFolder().mkdir();
            try (java.io.InputStream stream = this.getClass().getClassLoader().getResourceAsStream("config.yml");
                 java.io.OutputStream out = new java.io.FileOutputStream(configFile)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = stream.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        if (dbManager != null) {
            dbManager.closeConnection();
        }
        System.out.println("[BlockyAreYouPlaying] Plugin has been disabled.");
    }
}