package main.java.net.newtownia.RankSQL.Executive;

import main.java.net.newtownia.RankSQL.Data.RankData;
import main.java.net.newtownia.RankSQL.RankSQL;
import main.java.net.newtownia.RankSQL.Utils.LogUtils;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

class RankUpdater
{
    static void synchroniseFromDatabase(RankSQL pl, Player p)
    {
        LogUtils.debug("Handling download for " + p.getName());
        Permission vault = pl.getVault();

        List<RankData> sqlRanks = pl.getLoader().fetchData(p);
        List<String> gotRanks = new ArrayList<>();
        for (RankData sqlRank : sqlRanks)
        {
            gotRanks.add(sqlRank.getRankName());
            vault.playerAddGroup(p, sqlRank.getRankName());
        }

        List<String> toRemove = pl.getConfiguration().getStringList("Synchronisation.Remove-Ranks-If-Not-In-DB");
        for (String rank : vault.getPlayerGroups(p))
        {
            if (toRemove.contains(rank) && ! gotRanks.contains(rank))
                vault.playerRemoveGroup(p, rank);
        }
    }

    static void synchroniseToDatabase(RankSQL pl, Player p)
    {
        // TODO: Implement
    }
}
