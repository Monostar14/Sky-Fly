package top.skyliey.skyfly.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import top.skyliey.skyfly.Sky_Fly;

import java.io.File;
import java.io.IOException;

public class DataManager {

    public static Sky_Fly plugin;
    public File dataFile;
    private FileConfiguration dataConfig;

    public DataManager(Sky_Fly plugin) {
        DataManager.plugin = plugin;
        dataFile = plugin.setupDataFile();
        if (dataFile == null) {
            // 处理dataFile为null的情况
            throw new IllegalArgumentException("data.yml文件错误或不存在");
        }
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    //返回玩家在data.yml中的飞行能量值，要是没有就返回默认值 :)
    public int getFlyPower(String playerName) {
        return dataConfig.getInt(playerName + ".flypower", plugin.getConfig().getInt("default-flypower"));
    }

    //设置玩家飞行能量
    public void setFlyPower(String playerName, int power) {
        dataConfig.set(playerName + ".flypower", power);
        plugin.saveDataConfig();
    }

    private void saveDataConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
