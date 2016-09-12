package main.java.net.newtownia.ranksql.executive;

import main.java.net.newtownia.ranksql.RankSQL;
import main.java.net.newtownia.ranksql.data.RankData;
import main.java.net.newtownia.ranksql.utils.LogUtils;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

public class RankUpdater {

    public static void synchroniseFromDatabase(RankSQL pl, Player p) {
        RankSQL.runAsync(() -> {
            LogUtils.debug("Handling download for " + p.getName());
            PermissionUser permissionUser = PermissionsEx.getUser(p);

            List<String> toRemove = pl.getConfig().getStringList("Synchronisation.Remove-Ranks-If-Not-In-DB");
            for (RankData sqlRank : pl.dataLoader().fetchData(p)) {
                String rank = sqlRank.rankName();
                permissionUser.addGroup(rank);
                toRemove.remove(rank);
                LogUtils.debug("Added rank " + rank + " to " + p.getName());
            }

            for (String group : toRemove) {
                permissionUser.removeGroup(group);
                LogUtils.debug("Removed rank " + group + " from " + p.getName());
            }
        });
    }
}
