package top.skyliey.skyfly;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
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
    private Ctrl ctrl = new Ctrl();

    @Override
    public void onEnable() {
        int pluginId = 24275; // <-- Replace with the id of your plugin!
        org.bstats.bukkit.Metrics metrics = new org.bstats.bukkit.Metrics(this, pluginId);
        // 加载配置
        setupDataFile();
        registerCommandsAndTabCompleters();
        logPluginInfo();
        // 保存配置
        saveConfig();
        saveDataConfig();
    }

    @Override
    public void onDisable() {
        ctrl.logInfo("Sky_Fly 已关闭!");
        saveConfig();
    }

    private void registerCommandsAndTabCompleters() {
        getCommand("fly").setExecutor(new Fly(ctrl));
        getCommand("flyspeed").setExecutor(new FlySpeed(ctrl));
        registerCommandWithTabCompleter("flypower", new FlyPower(this));
    }

    private void registerCommandWithTabCompleter(String command, CommandExecutor executor) {
        Objects.requireNonNull(getCommand(command)).setExecutor(executor);
        Objects.requireNonNull(getCommand(command)).setTabCompleter((TabCompleter) executor); // 强制转换为 TabCompleter
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
            ctrl.logError("保存 data.yml 时发生错误：", e);
        }
    }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }

    private void logPluginInfo() {
        ctrl.logInfo(ChatColor.DARK_GREEN + "Sky-Fly 已启动!");
        String version = getServer().getBukkitVersion();
        ctrl.logInfo(ChatColor.DARK_GREEN + "当前服务器版本为" + version);
        ctrl.logInfo(ChatColor.DARK_GREEN + "当前插件版本为" + getConfig().getString("version"));
    }

    public Ctrl getCtrl() {
        return ctrl; // 返回 Ctrl 实例
    }

}
