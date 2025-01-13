package top.skyliey.skyfly.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.skyliey.skyfly.Ctrl;

import java.util.List;

import static top.skyliey.skyfly.DataManager.plugin;

public class FlySpeed implements CommandExecutor, TabCompleter {

    private final Ctrl ctrl; // 使用共享的 Ctrl 实例

    // 构造器，通过构造传递 Ctrl 实例
    public FlySpeed(Ctrl ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        // flyspeed <speed> 命令处理
        if (!(commandSender instanceof Player)) {
            ctrl.sendMessageWithColor(commandSender, plugin.getConfig().getString
                    ("messages.playerOnly"));
            return true;
        }
        Player player = (Player) commandSender;

        if (args.length == 0) {
            player.setFlySpeed(1f);  // 恢复默认飞行速度
            ctrl.sendMessageWithColor(player, "&a飞行速度已恢复为默认值！");
            return true;
        }

        try {
            float speed = Float.parseFloat(args[0]);
            // 检查飞行速度是否在合理范围内
            if (speed < -1 || speed > 1) {
                ctrl.sendMessageWithColor(player, "&c飞行速度只能在 [-1,1] 之间！");
                return true;
            }

            player.setFlySpeed(speed);  // 设置飞行速度
            ctrl.sendMessageWithColor(player, "&a飞行速度已设置为 " + speed);
            return true;
        } catch (NumberFormatException e) {
            ctrl.sendMessageWithColor(player, "&c参数错误，请输入一个有效的数字！");
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command,
                                      @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            // 提供飞行速度选项
            return ImmutableList.of("-1", "0", "1");
        }
        return ImmutableList.of(); // 返回空列表
    }
}
