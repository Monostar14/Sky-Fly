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
        if(!(commandSender instanceof Player)){
            sender.sendMessage("§c只有玩家可以执行该命令！");
            return true;
        }
        Player player = (Player)commandSender;
        if (player.isOp() || player.hasPermission("skyfly.flyspeed")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("flyspeed")) {
                try{
                    double speed = Double.parseDouble(args[0]);
                    if (speed >= -1 && speed <= 2) {
                        commandSender.sendMessage("§a飞行速度已设置为" + speed);
                        player.setFlySpeed((float) speed);
                        return true;
                    }else{
                        commandSender.sendMessage("§c飞行速度只能在-1到2之间.");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    commandSender.sendMessage("§4参数错误，请输入数字！");
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
            return ImmutableList.of("-1", "0", "1", "2");
        }
        return null;
    }
}
