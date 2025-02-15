package top.skyliey.skyfly;

import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import top.skyliey.skyfly.commands.CommandsManager;
import top.skyliey.skyfly.utils.Ctrl;
import top.skyliey.skyfly.utils.DataManager;

import java.io.File;
import java.util.Objects;

public class Sky_Fly extends JavaPlugin {

    private File dataFile;
    private FileConfiguration dataConfig;
    private Ctrl ctrl = new Ctrl();
    private static Sky_Fly instance;
    private CommandsManager commandsManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;
        int pluginId = 24290;
        Metrics metrics = new Metrics(this, pluginId);
        this.commandsManager = new CommandsManager(this);
        this.dataManager = new DataManager(this);
        setUpDataFile();
        this.dataConfig = this.getConfig();
        this.saveDefaultConfig();
        setUpLogic();
        ctrl.logInfo("Sky-Fly已加载");
        //注册指令
        Objects.requireNonNull(getCommand("fly")).setExecutor(new CommandsManager(this));
    }

    @Override
    public void onDisable() {
        ctrl.logInfo("Sky-Fly已关闭");
    }

    public static Sky_Fly getInstance() {
        return instance;
    }

    public void setUpLogic() {
        ctrl.logInfo("Sky-Fly已启动");
        //检查版本
        ctrl.logInfo("版本：" + getConfig().getString("version"));
    }

    public void setUpDataFile() {
        this.dataFile = new File(this.getDataFolder(), "data.yml");
        if (!this.dataFile.exists()) {
            this.dataFile.getParentFile().mkdirs();
            this.saveResource("data.yml", false);
        }
    }

    public Ctrl getCtrl() {
        return ctrl; // 返回 Ctrl 实例
    }


}
