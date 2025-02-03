package top.skyliey.skyfly.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import top.skyliey.skyfly.Ctrl;
import top.skyliey.skyfly.DataManager;
import top.skyliey.skyfly.Sky_Fly;

import java.util.Objects;

import static org.bukkit.Bukkit.reload;

public class CommandsManager implements CommandExecutor {

    private static Sky_Fly plugin;
    private static Ctrl ctrl;
    public CommandsManager(Plugin plugin, Ctrl ctrl) {
        CommandsManager.plugin = (Sky_Fly) plugin;
        CommandsManager.ctrl = ctrl;
    }

    @Override
    //skyfly fly true/false
    //skyfly set flyspeed [-1, 1]
    //skyfly set flypower [player] [power]
    //skyfly reload
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            onlyPlayer(sender);
            return true;
        }
        if (args.length == 0) {
            ctrl.sendMessageWithColor(sender, CommandsManager.invalidNumbers(sender));
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            if (args.length == 1) {
                long startTime = System.currentTimeMillis();
                reload();
                long endTime = System.currentTimeMillis();
                ctrl.sendMessageWithColor(sender, plugin.getConfig().getString
                        ("warnMessages.reloadSuccess") + (endTime - startTime) + "ms");
                return true;
            }
        }else if(args[0].equalsIgnoreCase("set")) {
            if(args[1].equalsIgnoreCase("flyspeed")) {
                Player player = (Player) sender;
                try {
                    float speed = Float.parseFloat(args[2]);
                    // 检查飞行速度是否在合理范围内
                    if (speed < -1 || speed > 1) {
                        ctrl.sendMessageWithColor(player, "&c飞行速度只能在区间 [-1,1] 之间！");
                        return true;
                    }

                    player.setFlySpeed(speed);  // 设置飞行速度
                    ctrl.sendMessageWithColor(player, "&a飞行速度已设置为 " + speed);
                    return true;
                } catch (NumberFormatException e) {
                    //ctrl.sendMessageWithColor(player, "&c参数错误，请输入一个有效的数字！");
                    CommandsManager.invalidNumbers(sender);
                    return true;
                }
            }else if(args[1].equalsIgnoreCase("flypower")) {
                //skyfly set flypower [player] [power]
                String playerName = args[2];
                Player player = Bukkit.getPlayer(playerName);
                if (player == null) {
                    String noPlayerMessage = Objects.requireNonNull(plugin.getConfig().getString
                            ("warnMessages.noPlayer")).replace("%player%", playerName);
                    ctrl.sendMessageWithColor(sender, noPlayerMessage);
                    return true;
                }
                // 获取飞行能量值
                try {
                    int power = Integer.parseInt(args[3]);
                    // 保存飞行能量到配置文件
                    DataManager.setFlyPower(String.valueOf(player), power);
                    plugin.saveDataConfig();
                    ctrl.sendMessageWithColor(sender, "&e玩家 " + playerName + " 的飞行能量已设置为 " + power);
                } catch (NumberFormatException e) {
                    CommandsManager.invalidNumbers(sender);
                }
            }
        }
        return true;
    }
    //命令只能由玩家执行
    public static void onlyPlayer(CommandSender sender) {
        String onlyPlayer = plugin.getConfig().getString("messages.onlyPlayer");
        ctrl.sendMessageWithColor(sender, onlyPlayer);
    }

    //输入错误
    public static String invalidNumbers(CommandSender sender) {
        String invalidNumbers = plugin.getConfig().getString("messages.invalidNumbers");
        ctrl.sendMessageWithColor(sender, invalidNumbers);
        return invalidNumbers;
    }
}
