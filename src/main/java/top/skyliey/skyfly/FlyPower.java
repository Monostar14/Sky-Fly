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
import top.skyliey.skyfly.commands.CommandsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlyPower implements CommandExecutor, TabExecutor {

    private final Sky_Fly plugin;
    private final Ctrl ctrl; // 共享

    public FlyPower(Sky_Fly plugin) {
        this.plugin = plugin;
        this.ctrl = plugin.getCtrl(); // 获取
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        // flypower set <player> <number>
        if (args.length < 3 || !args[0].equalsIgnoreCase("set")) {
            CommandsManager.invalidNumbers(sender);
            return true;
        }

        // 获取玩家
        String playerName = args[1];
        Player player = Bukkit.getPlayer(playerName);

        // 检查玩家是否在线
        if (player == null) {
            String noPlayerMessage = Objects.requireNonNull(plugin.getConfig().getString
                    ("warnMessages.noPlayer")).replace("%player%", playerName);
            ctrl.sendMessageWithColor(sender, noPlayerMessage);
            return true;
        }

        // 获取飞行能量值
        try {
            int power = Integer.parseInt(args[2]);
            // 保存飞行能量到配置文件
            DataManager.setFlyPower(String.valueOf(player), power);
            plugin.saveDataConfig();
            ctrl.sendMessageWithColor(sender, "&e玩家 " + playerName + " 的飞行能量已设置为 " + power);
        } catch (NumberFormatException e) {
            CommandsManager.invalidNumbers(sender);
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        // Tab 补全第一部分为 "set"
        if (args.length == 1) {
            return ImmutableList.of("set");
        }

        // Tab 补全第二部分为在线玩家的名字
        if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
            return list; // 返回在线玩家名称列表
        }

        return new ArrayList<>(); // 如果没有补全项，返回空列表
    }


}
