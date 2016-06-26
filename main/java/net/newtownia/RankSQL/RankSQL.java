package net.newtownia.RankSQL;

import org.bukkit.plugin.java.JavaPlugin;

public class RankSQL extends JavaPlugin
{
    private static RankSQL instance;

    @Override
    public void onEnable()
    {
        instance = this;
    }

    public static RankSQL getInstance()
    {
        return instance;
    }
}
