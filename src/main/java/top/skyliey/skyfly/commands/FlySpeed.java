package top.skyliey.skyfly.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FlySpeed implements CommandExecutor, TabCompleter {
    Player sender;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        //flyspeed <speed>
        if(!(commandSender instanceof Player)){
            sender.sendMessage("§c只有玩家可以执行该命令！");
            return true;
        }
        Player player = (Player) commandSender;
        if (args.length == 0) {
            player.setFlySpeed(1f);
            player.sendMessage("§a飞行速度已恢复为默认值！");
            return true;
        }
        try {
            float speed = Float.parseFloat(args[0]);
            if (speed < -1 || speed > 1) {
                player.sendMessage("§c飞行速度只能在[-1,1]之间！");
                return true;
            }
            player.setFlySpeed(speed);
            player.sendMessage("§a飞行速度已设置为" + speed);
            return true;
        } catch (NumberFormatException e) {
            player.sendMessage("§c参数错误");
            return true;
        }
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
