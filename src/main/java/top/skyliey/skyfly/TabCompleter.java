package top.skyliey.skyfly;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleter implements TabExecutor {
    private final Plugin plugin;

    public TabCompleter(Plugin plugin, Ctrl ctrl) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd,
                                      @NotNull String label, String[] args) {
        //skyfly fly true/false
        //skyfly set flyspeed [-1, 1]
        //skyfly set flypower [player] [power]
        //skyfly reload
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions = Arrays.asList("fly", "set", "reload");
        }else if(args.length == 2 && args[0].equals("set")) {
            suggestions = Arrays.asList("flyspeed", "flypower");
        } else if (args.length == 2 && args[0].equals("fly")) {
            suggestions = Arrays.asList("true", "false");
        }
        if (args.length == 2 && args[1].equals("flypower")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
            return suggestions; // 返回在线玩家名称列表
        }
        return suggestions;
    }
}

