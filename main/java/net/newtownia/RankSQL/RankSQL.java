package net.newtownia.RankSQL;

import net.newtownia.NTApi.Config.ConfigManager;
import net.newtownia.RankSQL.Data.DataLoader;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class RankSQL extends JavaPlugin
{
    private static RankSQL instance;
    private YamlConfiguration configuration;

    private DataLoader loader;

    @Override
    public void onEnable()
    {
        instance = this;

        reload();
    }

    public void reload()
    {
        configuration = ConfigManager.loadOrCreateConfigFile("config.yml", this);
        loader = new DataLoader(this);
    }

    public static RankSQL getInstance()
    {
        return instance;
    }
    public YamlConfiguration getConfiguration() {
        return configuration;
    }
}
