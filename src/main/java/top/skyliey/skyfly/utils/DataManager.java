package top.skyliey.skyfly.utils;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import top.skyliey.skyfly.Sky_Fly;

import java.io.File;
import java.io.IOException;


public class DataManager {
    private static DataManager instance;
    private FileConfiguration dataConfig;
    private File dataFile;
    private final Sky_Fly plugin;

    public DataManager(Sky_Fly plugin) {
        this.plugin = plugin;
        loadData();
    }
    public void loadData() {
        this.dataFile = new File(this.plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            this.plugin.saveResource("data.yml", false);
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public static DataManager getInstance() {
        if(instance == null) {
            instance = new DataManager(Sky_Fly.getPlugin());
        }
        return instance;
    }

    public int getEnergy(String playerName) {
        return dataConfig.getInt("players." + playerName + ".energy");
    }

    public void setEnergy(String playerName, double energy) {
        dataConfig.set("players." + playerName + ".energy", energy);
        saveConfig();
    }

    public void addEnergy(String playerName, double energy) {
        int currentEnergy = getEnergy(playerName);
        dataConfig.set("players." + playerName + ".energy", currentEnergy + energy);
        saveConfig();
    }
    public void removeEnergy(String playerName, double energy) {
        int currentEnergy = getEnergy(playerName);
        dataConfig.set("players." + playerName + ".energy", currentEnergy - energy);
        saveConfig();
    }

    private void saveConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

