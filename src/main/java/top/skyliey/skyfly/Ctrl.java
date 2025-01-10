package top.skyliey.skyfly;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class Ctrl {

    private Logger logger = Logger.getLogger("Sky-Fly");

    public void sendMessageWithColor(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void logInfo(String m) {
        logger.info(m);
    }
    public void logWarn(String m) {
        logger.warning(m);
    }
    public void logError(String message, Exception e) {
        // 打印错误信息
        logger.severe(message);
        // 打印堆栈跟踪
        for (StackTraceElement element : e.getStackTrace()) {
            logger.severe(element.toString());
        }
    }

}
