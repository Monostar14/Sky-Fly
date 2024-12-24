package top.skyliey.skyfly;

import org.bukkit.plugin.java.JavaPlugin;
import top.skyliey.skyfly.commands.Fly;
import top.skyliey.skyfly.commands.FlySpeed;

import java.util.Objects;

public final class Sky_Fly extends JavaPlugin {

    @Override
    public void onEnable() {
        regCommands();
        regTabCompleter();
        getLogger().info("Sky_Fly插件启动成功!");
        String version = getServer().getBukkitVersion();
        getLogger().info("当前服务器版本为" + version);
    }

    @Override
    public void onDisable() {
        getLogger().info("Sky_Fly插件已关闭!");
    }

    public void regCommands() {
        //注册命令
        Objects.requireNonNull(this.getCommand("fly")).setExecutor(new Fly());
        Objects.requireNonNull(this.getCommand("flyspeed")).setExecutor(new FlySpeed());
    }

    public void regTabCompleter() {
        //注册Tab
        Objects.requireNonNull(this.getCommand("fly")).setTabCompleter(new Fly());
        Objects.requireNonNull(this.getCommand("flyspeed")).setTabCompleter(new FlySpeed());
    }
}
