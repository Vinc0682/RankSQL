package main.java.net.newtownia.RankSQL.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class LogUtils
{
    private static boolean debug = true;
    private static boolean warn = true;

    public static void debug(String message)
    {
        if (debug)
            console("&7[RankSQL][&4DEBUG&7] " + message);
    }

    public static void warn(String message)
    {
        if (warn)
            console("&7[RankSQL][&4WARNING&7] &e" + message);
    }

    private static void console(String message)
    {
        message = ChatColor.translateAlternateColorCodes('&', message);
        Bukkit.getConsoleSender().sendMessage(message);
    }
}
