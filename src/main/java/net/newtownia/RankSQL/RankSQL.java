package main.java.net.newtownia.RankSQL;

import main.java.net.newtownia.RankSQL.Commands.RankSQLCommand;
import main.java.net.newtownia.RankSQL.Data.DataLoader;
import main.java.net.newtownia.RankSQL.Executive.Listeners;
import net.newtownia.NTApi.Config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class RankSQL extends JavaPlugin
{
    private static RankSQL instance;
    private YamlConfiguration configuration;

    private DataLoader loader;
    private Listeners listeners;

    @Override
    public void onEnable()
    {
        instance = this;

        getCommand("ranksql").setExecutor(new RankSQLCommand(this));
        reload();
    }

    public void reload()
    {
        if (listeners != null)
            HandlerList.unregisterAll(this);
        if (loader != null)
            loader.unload();

        configuration = ConfigManager.loadOrCreateConfigFile("config.yml", this);
        loader = new DataLoader(this);

        listeners = new Listeners(this);
        Bukkit.getPluginManager().registerEvents(listeners, this);
    }

    public static RankSQL getInstance()
    {
        return instance;
    }
    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public DataLoader getLoader() {
        return loader;
    }
}
