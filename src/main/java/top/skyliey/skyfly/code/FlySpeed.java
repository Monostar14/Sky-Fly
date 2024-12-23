package top.skyliey.skyfly.code;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FlySpeed implements CommandExecutor, TabCompleter {
    Player sender;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        sender = (Player) commandSender;
        if (sender.isOp() ||sender.hasPermission("skyfly.flyspeed")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("flyspeed")) {
                try {
                    double speed = Double.parseDouble(args[0]);
                    if (speed < -1 || speed > 1) {
                        commandSender.sendMessage("§4飞行速度必须是一个数字，且必须在区间[-1,1]之间。");
                        return false;
                    }
                    commandSender.sendMessage("§a飞行速度被设置成" + speed + ".");
                    sender.setFlySpeed((float) speed);
                    return true;
                } catch (NumberFormatException e) {
                    commandSender.sendMessage("§4飞行速度必须是一个数字，且必须在区间[-1,1]之间。");
                    return false;
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command,
                                      @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            return ImmutableList.of("-1", "0", "1");
        }
        return null;
    }
}
