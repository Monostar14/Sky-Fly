package top.skyliey.skyfly;

import org.bstats.bukkit.Metrics;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import top.skyliey.skyfly.commands.SkyFlyCommands;
import top.skyliey.skyfly.utils.DataManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Sky_Fly extends JavaPlugin {

    private File dataFile;
    private FileConfiguration dataConfig;
    private Ctrl ctrl = new Ctrl();

    private DataManager DataManager;

    private static Sky_Fly instance;

    @Override
    public void onEnable() {
        instance = this;
        DataManager = new DataManager(this);
        int pluginId = 24290;
        Metrics metrics = new Metrics(this, pluginId);
        SkyFlyCommands skyFlyCommands = new SkyFlyCommands(this, ctrl);
        Objects.requireNonNull(this.getCommand("skyfly")).setExecutor(skyFlyCommands);
        //Objects.requireNonNull(this.getCommand("skyfly")).setTabCompleter(tabCompleter);
        // 加载配置
        setupDataFile();
        saveDefaultConfig();
        reloadConfig();
        logPluginInfo();
        getDataConfig();
        // 保存配置
        saveConfig();
        saveDataConfig();
    }

    @Override
    public void onDisable() {
        ctrl.logInfo("Sky_Fly 已关闭!");
        saveConfig();
        saveDataConfig();
    }

    public static Sky_Fly getInstance() {
        return instance;
    }

    public File setupDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        return dataFile;
    }

    public void saveDataConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            ctrl.logError(getConfig().getString("warn-messages.file-error"), e);
        }
    }

    public FileConfiguration getDataConfig() {
        if(dataConfig == null) {
            reloadConfig();
        }
        return dataConfig;
    }

    private void logPluginInfo() {
        ctrl.logInfo("Sky-Fly 已启动!");
        String version = getServer().getBukkitVersion();
        ctrl.logInfo("当前服务器版本为" + version);
        ctrl.logInfo("当前插件版本为" + getConfig().getString("version"));
    }

    public Ctrl getCtrl() {
        return ctrl; // 返回 Ctrl 实例
    }

    public DataManager getConfigManager() {
        return this.DataManager;
    }

}
