package top.skyliey.skyfly;

import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import top.skyliey.skyfly.commands.CommandsManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static top.skyliey.skyfly.DataManager.plugin;

public class Sky_Fly extends JavaPlugin {

    private File dataFile;
    private FileConfiguration dataConfig;
    private Ctrl ctrl = new Ctrl();

    public CommandsManager commandsManager;
    public TabCompleter tabCompleter;
    @Override
    public void onEnable() {
        int pluginId = 24290;
        Metrics metrics = new Metrics(this, pluginId);
        this.commandsManager = new CommandsManager(this, ctrl);
        getCommand("skyfly").setExecutor(new CommandsManager(this, ctrl));
        getCommand("skyfly").setTabCompleter(new top.skyliey.skyfly.TabCompleter(this, ctrl));
        // 加载配置
        saveDefaultConfig();
        reloadConfig();
        setupDataFile();
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

//    private void registerCommandsAndTabCompleters() {
//        getCommand("skyfly").setExecutor(new CommandsManager(plugin, ctrl));
//    }
//
//    private void registerCommandWithTabCompleter(String command, CommandExecutor executor) {
//        Objects.requireNonNull(getCommand(command)).setExecutor(executor);
//        Objects.requireNonNull(getCommand(command)).setTabCompleter((TabCompleter) executor); // 强制转换为 TabCompleter
//    }


    void setupDataFile() {
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
            ctrl.logError(getConfig().getString("warnMessages.fileError"), e);
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
