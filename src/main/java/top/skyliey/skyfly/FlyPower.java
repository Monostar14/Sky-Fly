package top.skyliey.skyfly;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FlyPower implements CommandExecutor, TabExecutor {
    private final Sky_Fly plugin;
    public FlyPower(Sky_Fly plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        //flypower set <player> <number>
        //检查命令格式
        if(args.length < 3 || !args[0].equalsIgnoreCase("set")) {
            sender.sendMessage("用法: /flypower set <玩家> <数值>");
            return true;
        }
        //获取玩家
        String playerName = args[1];
        Player player = Bukkit.getPlayer(playerName);
        //检查玩家是否存在
        if(player == null) {
            sender.sendMessage("玩家" + playerName + "不在线或不存在！");
            return true;
        }
        //获取数值
        try {
            int power = Integer.parseInt(args[2]);
            plugin.getDataConfig().set(playerName + ".flypower", power);
            plugin.saveDataConfig();
            sender.sendMessage("玩家" + playerName + "的飞行能力已设置为" + power);
        } catch(NumberFormatException e) {
            sender.sendMessage("输入有效的数字！");
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            return ImmutableList.of("set");
        }
        if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }
        return null;
    }
}

