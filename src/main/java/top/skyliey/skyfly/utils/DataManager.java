package top.skyliey.skyfly.utils;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import top.skyliey.skyfly.Sky_Fly;

import java.io.File;
import java.io.IOException;


public class DataManager {
    private static DataManager instance;
    private FileConfiguration dataConfig;
    private final File dataFile;
    private static Sky_Fly plugin;

    public DataManager(Sky_Fly plugin) {
        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            try {
                dataFile.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public static DataManager getInstance() {
        if(instance == null) {
            instance = new DataManager(plugin);
        }
        return instance;
    }
    public int getEnergy(String playerName) {
        return dataConfig.getInt("players." + playerName + ".energy");
    }

    public void setEnergy(String playerName, int energy) {
        dataConfig.set("players." + playerName + ".energy", energy);
        saveConfig();
    }

    public void  addEnergy(String playerName, int energy) {
        int currentEnergy = getEnergy(playerName);
        dataConfig.set("players." + playerName + ".energy", currentEnergy + energy);
        saveConfig();
    }
    public void removeEnergy(String playerName, int energy) {
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

