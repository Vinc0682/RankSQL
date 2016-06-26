package main.java.net.newtownia.RankSQL.Executive;

import main.java.net.newtownia.RankSQL.Data.RankData;
import main.java.net.newtownia.RankSQL.RankSQL;
import main.java.net.newtownia.RankSQL.Utils.LogUtils;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

class RankUpdater
{
    static void synchroniseFromDatabase(RankSQL pl, Player p)
    {
        LogUtils.debug("Handling download for " + p.getName());
        PermissionUser permissionUser = PermissionsEx.getUser(p);

        List<RankData> sqlRanks = pl.getLoader().fetchData(p);
        List<String> toRemove = pl.getConfiguration().getStringList("Synchronisation.Remove-Ranks-If-Not-In-DB");
        for (RankData sqlRank : sqlRanks)
        {
            String rank = sqlRank.getRankName();
            permissionUser.addGroup(sqlRank.getRankName());

            if (toRemove.contains(rank))
                toRemove.remove(rank);

            LogUtils.debug("Added rank " + rank + " to " + p.getName());
        }
        for (String group : toRemove)
        {
            permissionUser.removeGroup(group);
            LogUtils.debug("Removed rank " + group + " from " + p.getName());
        }
    }

    static void synchroniseToDatabase(RankSQL pl, Player p)
    {
        // TODO: Implement
    }
}
