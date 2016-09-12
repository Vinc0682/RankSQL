package main.java.net.newtownia.ranksql.utils;

import org.bukkit.Bukkit;

public class LogUtils {

    private static boolean debug = false, warn = true;

    //TODO: You should probably remove the debug part and just use System.out.println() When you run into an issue.

    public static void debug(String message) {
        if (debug) console("&7[ranksql][&4DEBUG&7] " + message);
    }

    public static void warn(String message) {
        if (warn) console("&7[ranksql][&4WARNING&7] &e" + message);
    }

    private static void console(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatUtils.format(message));
    }

}
