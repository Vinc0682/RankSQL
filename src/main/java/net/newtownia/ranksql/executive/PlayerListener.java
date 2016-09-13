package main.java.net.newtownia.ranksql.executive;

import main.java.net.newtownia.ranksql.RankSQL;
import main.java.net.newtownia.ranksql.utils.LogUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final RankSQL rankSQL;

    public PlayerListener(RankSQL rankSQL)
    {
        this.rankSQL = rankSQL;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        LogUtils.debug("Player joining!");
        RankUpdater.synchroniseFromDatabase(rankSQL, event.getPlayer());
    }
}
