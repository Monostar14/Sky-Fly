package top.skyliey.skyfly;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import top.skyliey.skyfly.commands.CommandsManager;
import top.skyliey.skyfly.utils.Ctrl;
import top.skyliey.skyfly.utils.DataManager;

import java.util.Objects;

public class Sky_Fly extends JavaPlugin {

    private Ctrl ctrl = new Ctrl();
    private static Sky_Fly plugin;
    private CommandsManager commandsManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        plugin = this;
        int pluginId = 24290;
        Metrics metrics = new Metrics(this, pluginId);
        dataManager = DataManager.getInstance();
        commandsManager = new CommandsManager(this);
        this.dataManager = new DataManager(this);
        this.saveDefaultConfig();
        setUpLogic();
        ctrl.logInfo("Sky-Fly已加载");
        //注册指令
        Objects.requireNonNull(this.getCommand("skyfly")).setExecutor(commandsManager);
    }

    @Override
    public void onDisable() {
        ctrl.logInfo("Sky-Fly已关闭");
    }


    public void setUpLogic() {
        ctrl.logInfo("Sky-Fly已启动");
        //检查版本
        ctrl.logInfo("版本：" + getConfig().getString("version"));
    }

    public Ctrl getCtrl() {
        return ctrl; // 返回 Ctrl 实例
    }

    public static Sky_Fly getPlugin() {
        return plugin;
    }
}
