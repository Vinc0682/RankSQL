package main.java.net.newtownia.RankSQL;

import net.milkbowl.vault.permission.Permission;
import net.newtownia.NTApi.Config.ConfigManager;
import main.java.net.newtownia.RankSQL.Data.DataLoader;
import main.java.net.newtownia.RankSQL.Executive.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class RankSQL extends JavaPlugin
{
    private static RankSQL instance;
    private YamlConfiguration configuration;

    private Permission vault;

    private DataLoader loader;
    private Listeners listeners;

    @Override
    public void onEnable()
    {
        instance = this;

        reload();
    }

    private void reload()
    {
        if (listeners != null)
            HandlerList.unregisterAll(this);
        if (loader != null)
            loader.unload();

        configuration = ConfigManager.loadOrCreateConfigFile("config.yml", this);
        loader = new DataLoader(this);

        listeners = new Listeners(this);
        Bukkit.getPluginManager().registerEvents(listeners, this);

        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null)
            vault = permissionProvider.getProvider();
        else
        {
            Bukkit.getConsoleSender().sendMessage("§4VAULT IS REQUIRED TO RUN RankSQL!");
            getPluginLoader().disablePlugin(this);
        }
    }

    public static RankSQL getInstance()
    {
        return instance;
    }
    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public Permission getVault() {
        return vault;
    }

    public DataLoader getLoader() {
        return loader;
    }
}
