package main.java.net.newtownia.RankSQL.Executive;

import main.java.net.newtownia.RankSQL.RankSQL;
import main.java.net.newtownia.RankSQL.Utils.LogUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener
{
    private RankSQL pl;

    public Listeners(RankSQL pl)
    {
        this.pl = pl;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        LogUtils.debug("Player joining!");
        if (Boolean.valueOf(pl.getConfiguration().getString("Synchronisation.Triggers.Player-Join")))
            RankUpdater.synchroniseFromDatabase(pl, event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if (Boolean.valueOf(pl.getConfiguration().getString("Synchronisation.Triggers.Player-Leave")))
            RankUpdater.synchroniseToDatabase(pl, event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        if (Boolean.valueOf(pl.getConfiguration().getString("Synchronisation.Triggers.Player-Leave")))
            RankUpdater.synchroniseToDatabase(pl, event.getPlayer());
    }
}
