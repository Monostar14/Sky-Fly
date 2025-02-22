package top.skyliey.skyfly.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import top.skyliey.skyfly.Sky_Fly;

import java.util.logging.Logger;


public class Ctrl {
    private Logger logger = Logger.getLogger("SkyFly");

    private static Sky_Fly plugin;

    public void sendMessageWithColor(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logWarn(String message) {
        logger.warning(message);
    }

    public void logError(String message,Exception e) {
        logger.severe(message);
        for (StackTraceElement element : e.getStackTrace()) {
            logger.severe(element.toString());
        }
    }

    public String noPermission() {
        return plugin.getConfig().getString("messages.no-permission");
    }
}
