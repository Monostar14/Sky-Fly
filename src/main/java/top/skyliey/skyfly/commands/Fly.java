package top.skyliey.skyfly.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.skyliey.skyfly.Ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static top.skyliey.skyfly.DataManager.plugin;

public class Fly implements CommandExecutor, TabCompleter {

    private final Ctrl ctrl; // 使用插件中已创建的 Ctrl 实例

    // 构造函数，通过构造传递 Ctrl 实例
    public Fly(Ctrl ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        // fly <true/false>命令处理
        if (!(commandSender instanceof Player)) {
            String onlyPlayer = Objects.requireNonNull(plugin.getConfig().getString
                    ("warnMessages.noPlayer"));
            ctrl.sendMessageWithColor(commandSender, onlyPlayer);
            return true;
        }
        Player sender = (Player) commandSender;

        // 权限检查
        if (sender.isOp() || sender.hasPermission("skyfly.fly")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("true")) {
                    setFlyMode(sender, true);  // 调用统一的方法来设置飞行模式
                } else if (args[0].equalsIgnoreCase("false")) {
                    setFlyMode(sender, false); // 调用统一的方法来关闭飞行模式
                }
            }
            return true;
        }
        return false;
    }

    // 设置飞行模式的方法
    private void setFlyMode(Player sender, boolean allowFlight) {
        sender.setAllowFlight(allowFlight);
        String message = allowFlight ? "&a已打开飞行模式！" : "&c已关闭飞行模式！";
        ctrl.sendMessageWithColor(sender, message);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command,
                                      @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return ImmutableList.of("true", "false");
        }
        return new ArrayList<>();
    }
}
