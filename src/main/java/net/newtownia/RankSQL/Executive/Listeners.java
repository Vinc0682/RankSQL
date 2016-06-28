package main.java.net.newtownia.RankSQL.Executive;

import main.java.net.newtownia.RankSQL.RankSQL;
import main.java.net.newtownia.RankSQL.Utils.LogUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
}
