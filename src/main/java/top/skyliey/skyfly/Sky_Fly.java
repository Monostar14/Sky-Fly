package top.skyliey.skyfly;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import top.skyliey.skyfly.commands.Fly;
import top.skyliey.skyfly.commands.FlySpeed;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Sky_Fly extends JavaPlugin {
    private File dataFile;
    private FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        setupDataFile();
        regCommands();
        regTabCompleter();
        getLogger().info(ChatColor.DARK_GREEN +"Sky_Fly插件启动成功!");
        String version = getServer().getBukkitVersion();
        getLogger().info(ChatColor.DARK_GREEN + "当前服务器版本为" + version);
        getLogger().info(ChatColor.DARK_GREEN + "当前插件版本为" + getConfig().getString("version"));
        saveConfig();
        saveDataConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("Sky_Fly插件已关闭!");
        saveConfig();
    }

    public void regCommands() {
        //注册命令
        Objects.requireNonNull(this.getCommand("fly")).setExecutor(new Fly());
        Objects.requireNonNull(this.getCommand("flyspeed")).setExecutor(new FlySpeed());
        Objects.requireNonNull(this.getCommand("flypower")).setExecutor(new FlyPower(this));
    }

    public void regTabCompleter() {
        //注册Tab
        Objects.requireNonNull(this.getCommand("fly")).setTabCompleter(new Fly());
        Objects.requireNonNull(this.getCommand("flyspeed")).setTabCompleter(new FlySpeed());
    }

    private void setupDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveDataConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }
}
