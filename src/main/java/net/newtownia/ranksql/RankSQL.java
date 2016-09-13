package main.java.net.newtownia.ranksql;

import main.java.net.newtownia.ranksql.commands.RankSQLCommand;
import main.java.net.newtownia.ranksql.data.MySql;
import main.java.net.newtownia.ranksql.executive.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RankSQL extends JavaPlugin {

    private MySql dataLoader;

    private static RankSQL instance;

    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        getCommand("ranksql").setExecutor(new RankSQLCommand(instance));
        if (getConfig().getBoolean("Synchronisation.Triggers.Player-Join", true)) getServer().getPluginManager().registerEvents(new PlayerListener(instance), instance);
        dataLoader = new MySql(getConfig().getString("Database.Host", "localhost"), getConfig().getString("Database.Port", "3306"), getConfig().getString("Database.Database", "ranksql"), getConfig().getString("Database.Username", "NeverUseRoot"), getConfig().getString("Database.Password", "abcd1234"), getConfig().getString("Database.Table", "ranksql"));
    }

    @Override
    public void onDisable() {
        instance = null;
        dataLoader.disable();
        EXECUTOR.shutdownNow();
    }

    public MySql dataLoader() {
        return dataLoader;
    }

    public static RankSQL instance() {
        return instance;
    }

    public static ExecutorService executor() {
        return EXECUTOR;
    }

    public static void runAsync(Runnable runnable) {
        EXECUTOR.execute(runnable);
    }

    public void reload() {
        onDisable();
        reloadConfig();
        onEnable();
    }
}
