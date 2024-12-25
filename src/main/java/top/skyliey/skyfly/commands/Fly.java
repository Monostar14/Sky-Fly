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

public class Fly implements CommandExecutor, TabCompleter {
    Player sender;
    @Override
    public boolean onCommand(@NotNull CommandSender Commandsender,@NotNull Command command,
                             @NotNull String label,@NotNull String[] args) {
        //fly <true/false>
        if(!(Commandsender instanceof Player)){
            sender.sendMessage("§c只有玩家可以执行该命令！");
            return true;
        }
        sender = (Player) Commandsender;
        if (sender.isOp() || sender.hasPermission("skyfly.fly")) {
            if (args.length == 1 && args[0].equals("true")) {
                sender.setAllowFlight(true);
                sender.sendMessage("§a已打开飞行模式！");
            }else if(args.length == 1 && args[0].equalsIgnoreCase("false")) {
                sender.setAllowFlight(false);
                sender.sendMessage("§c已关闭飞行模式！");
            }
            return true;
        }
        return false;
    }
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command,
                                      @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            return ImmutableList.of("true", "false");
        }
        return null;
    }
}
