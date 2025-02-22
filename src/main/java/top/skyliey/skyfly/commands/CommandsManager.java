package top.skyliey.skyfly.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.skyliey.skyfly.Sky_Fly;
import top.skyliey.skyfly.utils.Ctrl;
import top.skyliey.skyfly.utils.DataManager;


public class CommandsManager implements CommandExecutor {
    //skyfly help 显示帮助信息
    //skyfly energy set [player] [power] 设置某人的飞行能量
    //skyfly energy add [player] [power] 给某人增加飞行能量
    //skyfly energy remove [player] [power] 给某人减少飞行能量
    //skyfly energy get [player] 获取某人的飞行能量
    //skyfly speed [speed] 设置飞行速度
    //skyfly use [true/false] 设置是否允许飞行
    //skyfly reload
    private static Sky_Fly plugin;
    private static Ctrl ctrl;
    private static DataManager dataManager;

    public CommandsManager(Sky_Fly plugin) {
        CommandsManager.plugin = plugin;
        CommandsManager.ctrl = plugin.getCtrl();
        CommandsManager.dataManager = DataManager.getInstance();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if (!command.getName().equalsIgnoreCase("skyfly")) {
            return false;
        }
        if (args.length == 0) {
            ctrl.sendMessageWithColor(sender, "&4&l错误：&r&c请输入正确的指令！");
            return true;
        }
        if (args[0].equalsIgnoreCase("energy")) {
            if (args.length < 3) {
                ctrl.sendMessageWithColor(sender, "&4&l错误：&r&c请输入正确的指令！");
                return false;
            }
            String playerName = args[2];
            player = plugin.getServer().getPlayer(playerName);
            if (player == null) {
                ctrl.sendMessageWithColor(sender, "&4&l错误：&r&c玩家未在线！");
                return false;
            }
            switch (args[1].toLowerCase()) {
                case "set":
                    if (args.length < 4 || !sender.hasPermission("skyfly.energy.set")) {
                        ctrl.noPermission();
                        return false;
                    }
                    int setPower = Integer.parseInt(args[3]);
                    if (setPower < 0) {
                        ctrl.sendMessageWithColor(sender, "&4&l错误：&r&c飞行能量不能为负数！");
                        return true;
                    }
                    dataManager.setEnergy(player.getName(), setPower);
                    ctrl.sendMessageWithColor(sender, "&2&l成功：&r&a已将 " + player.getName() + " 的飞行能量设置为 " + setPower);
                    break;
                case "add":
                    if (args.length < 4 || !sender.hasPermission("skyfly.energy.add")) {
                        ctrl.noPermission();
                        return false;
                    }
                    int addPower = Integer.parseInt(args[3]);
                    if (addPower < 0) {
                        ctrl.sendMessageWithColor(sender, "&4&l错误：&r&c飞行能量不能为负数！");
                        return true;
                    }
                    dataManager.addEnergy(player.getName(), addPower);
                    ctrl.sendMessageWithColor(sender, "&2&l成功：&r&a已将 " + player.getName() + " 的飞行能量增加 " + addPower);
                    break;
                case "remove":
                    if (args.length < 4 || !sender.hasPermission("skyfly.energy.remove")) {
                        ctrl.noPermission();
                        return false;
                    }
                    int removePower = Integer.parseInt(args[3]);
                    dataManager.removeEnergy(player.getName(), removePower);
                    int newPower = dataManager.getEnergy(player.getName());
                    if (newPower < 0) {
                        dataManager.setEnergy(player.getName(), 0);
                        ctrl.sendMessageWithColor(sender, "&2&l成功：&r&a" + player.getName() + " 的飞行能量已为 0");
                    }
                    ctrl.sendMessageWithColor(sender, "&2&l成功：&r&a已将 " + player.getName() + " 的飞行能量减少 " + removePower);
                    break;
                case "get":
                    if (args.length < 4 || !sender.hasPermission("skyfly.energy.get")) {
                        ctrl.noPermission();
                        return false;
                    }
                    int getPower = dataManager.getEnergy(player.getName());
                    ctrl.sendMessageWithColor(sender, "&2&l成功：&r&a" + player.getName() + " 的飞行能量为 " + getPower);
                    break;
                default:
                    ctrl.sendMessageWithColor(sender, "&4&l错误：&r&a请输入正确的指令！");
                    break;
            }
        }else if(args[0].equalsIgnoreCase("speed")) {
            if (args.length < 2 || !sender.hasPermission("skyfly.speed")) {
                ctrl.noPermission();
                return false;
            }
            int speed = Integer.parseInt(args[1]);
            if (speed < -1 || speed > 1) {
                ctrl.sendMessageWithColor(sender, "&4&l错误：§r§c速度范围为[-1, 1],若为负数，则会倒着飞！");
                return false;
            }
            player.setFlySpeed(speed);
            ctrl.sendMessageWithColor(sender, "&2&l成功：&r&a已将飞行速度设置为 " + speed);
        }else if(args[0].equalsIgnoreCase("use")) {
            if (args.length < 2 || !sender.hasPermission("skyfly.use")) {
                ctrl.noPermission();
                return false;
            }
            boolean use = Boolean.parseBoolean(args[1]);
            player.setAllowFlight(use);
            ctrl.sendMessageWithColor(sender, "&2&l成功：&r&a已将飞行权限设置为 " + use);
        }else if(args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("skyfly.reload")) {
                ctrl.noPermission();
                return false;
            }
            plugin.reloadConfig();
            ctrl.sendMessageWithColor(sender, "&2&l成功：&r&a已重新加载配置文件！");
        }else if(args[0].equalsIgnoreCase("help")) {

        }
        return true;
    }
}
