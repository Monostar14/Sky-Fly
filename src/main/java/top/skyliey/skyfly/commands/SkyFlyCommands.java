package top.skyliey.skyfly.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.spigotmc.CustomTimingsHandler;
import top.skyliey.skyfly.Ctrl;
import top.skyliey.skyfly.utils.DataManager;
import top.skyliey.skyfly.Sky_Fly;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.reload;

public class SkyFlyCommands implements CommandExecutor, TabCompleter {

    private static Sky_Fly plugin;
    private static Ctrl ctrl;
    private  DataManager dataManager;
    public SkyFlyCommands(Plugin plugin, Ctrl ctrl) {
        SkyFlyCommands.plugin = (Sky_Fly) plugin;
        SkyFlyCommands.ctrl = ctrl;
        dataManager = new DataManager((Sky_Fly) plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //输入无效时逻辑
        if (args.length == 0) {
            ctrl.sendMessageWithColor(sender, invalidNumbers(sender));
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            if (args.length == 1) {
                long startTime = System.currentTimeMillis();
                reload();
                CustomTimingsHandler.reload();
                long endTime = System.currentTimeMillis();
                ctrl.sendMessageWithColor(sender, plugin.getConfig().getString
                        ("warn-messages.reload-success") + (endTime - startTime) + "ms");
                return true;
            }
        }else if(args[0].equalsIgnoreCase("set")) {
            if(args[1].equalsIgnoreCase("flyspeed")) {
                //flyspeed逻辑
                //skyfly set flyspeed [-1, 1]
                if (!(sender instanceof Player)) {
                    ctrl.sendMessageWithColor(sender, onlyPlayer(sender));
                    return true;
                }
                if(args.length == 3) {
                    Player player = (Player) sender;
                    try {
                        float speed = Float.parseFloat(args[2]);
                        if(speed < -1 || speed > 1) {
                            ctrl.sendMessageWithColor(sender, flySpeedError(sender));
                            return true;
                        }
                        player.setFlySpeed(speed);
                        ctrl.sendMessageWithColor(sender, "&a飞行速度已设置为 " + speed);
                        return true;
                    }catch (NumberFormatException e) {
                        invalidNumbers(sender);
                    }
                }else {
                    ctrl.sendMessageWithColor(sender, invalidNumbers(sender));
                }
            }else if(args[1].equalsIgnoreCase("flypower")) {
                //flypower逻辑
                //skyfly set flypower [player] [power]
                if(args.length == 4) {
                    String playerName = args[2];
                    Player player = Bukkit.getPlayer(playerName);
                    if(player == null) {
                        String noPlayerMessage = Objects.requireNonNull(plugin.getConfig().getString
                                ("warn-messages.no-player")).replace("%player%", playerName);
                        ctrl.sendMessageWithColor(sender, noPlayerMessage);
                        return true;
                    }
                    try {
                        int power = Integer.parseInt(args[3]);
                        DataManager dataManager = new DataManager(plugin);
                        dataManager.setFlyPower(String.valueOf(player), power);
                        plugin.saveDataConfig();
                        ctrl.sendMessageWithColor(sender, "&a玩家" + playerName + "的飞行能量已设置为 " + power);
                    }catch (NumberFormatException e) {
                        invalidNumbers(sender);
                    }
                }
            }
        }else if(args[0].equalsIgnoreCase("fly")) {
            //fly逻辑
            //skyfly fly [true/false]
            if (!(sender instanceof Player)) {
                ctrl.sendMessageWithColor(sender, onlyPlayer(sender));
                return true;
            }
            if(args.length == 2) {
                Player player = (Player) sender;
                if(args[1].equalsIgnoreCase("true")) {
                    player.setFlying(true);
                    ctrl.sendMessageWithColor(sender, "&a已打开飞行模式");
                }else if(args[1].equalsIgnoreCase("false")) {
                    player.setFlying(false);
                    ctrl.sendMessageWithColor(sender, "&a已关闭飞行模式");
                }
            }else{
                ctrl.sendMessageWithColor(sender, invalidNumbers(sender));
            }
        }else if(args[0].equalsIgnoreCase("flypower")) {
            //获取 flypower 逻辑
            //skyfly flypower [player]
            if (!(sender instanceof Player)) {
                ctrl.sendMessageWithColor(sender, onlyPlayer(sender));
                return true;
            }
            if(args.length == 2) {
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null) {
                    String noPlayerMessage = Objects.requireNonNull(plugin.getConfig().getString
                            ("warn-messages.no-player")).replace("%player%",args[1]);
                    ctrl.sendMessageWithColor(sender, noPlayerMessage);
                    return true;
                }
                dataManager.getFlyPower(String.valueOf(sender));
                plugin.saveDataConfig();
            }
        }else{
            ctrl.sendMessageWithColor(sender, invalidNumbers(sender));
        }
        return true;
    }
    //命令只能由玩家执行
    public static String onlyPlayer(CommandSender sender) {
        String onlyPlayer = plugin.getConfig().getString("warn-messages.only-player");
        ctrl.sendMessageWithColor(sender, onlyPlayer);
        return onlyPlayer;
    }

    //输入错误
    public static String invalidNumbers(CommandSender sender) {
        String invalidNumbers = plugin.getConfig().getString("warn-messages.invalid-numbers");
        ctrl.sendMessageWithColor(sender, invalidNumbers);
        return invalidNumbers;
    }
    //数字错误
    public static String flySpeedError(CommandSender sender) {
        String flySpeedError = plugin.getConfig().getString("warn-messages.fly-speed-error");
        ctrl.sendMessageWithColor(sender, flySpeedError);
        return flySpeedError;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        //TODO 补充tab补全逻辑
        return null;
    }
}
