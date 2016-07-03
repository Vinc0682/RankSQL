package main.java.net.newtownia.RankSQL.Data;

import com.zaxxer.hikari.HikariDataSource;
import main.java.net.newtownia.RankSQL.RankSQL;
import main.java.net.newtownia.RankSQL.Utils.LogUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataLoader
{
    private RankSQL pl;
    private HikariDataSource dataSource;
    private String table = "";

    public DataLoader(RankSQL pl)
    {
        this.pl = pl;

        createDataSource();
        createTableIfNotExists();
    }

    private void createDataSource()
    {
        YamlConfiguration config = pl.getConfiguration();

        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://" + config.getString("Database.Host") + ":" +
                config.getString("Database.Port") + "/" + config.getString("Database.Database"));
        dataSource.setUsername(config.getString("Database.Username"));
        dataSource.setPassword(config.getString("Database.Password"));

        table = config.getString("Database.Table");
    }

    private void createTableIfNotExists()
    {
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + table +"` (`Id` int(11) NOT NULL AUTO_INCREMENT,`PlayerUUID` varchar(36) NOT NULL,`Rank` varchar(24) NOT NULL,`Until` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,PRIMARY KEY (`Id`))");
            LogUtils.debug("Tried to create table!");
        }
        catch (SQLException e)
        {
            LogUtils.warn("Cannot create table, maybe it already exists or the user doesn't have the permission to create it!");
        }
    }

    private List<RankData> fetchData(UUID pUUID)
    {
        List<RankData> result = new ArrayList<>();
        try
        {
            LogUtils.debug("Trying to fetch data of uuid: " + pUUID.toString());
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM `" + table +
                    "` WHERE `PlayerUUID` like \"" + pUUID.toString() +"\"");

            while (results.next())
            {
                result.add(new RankData(results.getInt("Id"),
                        UUID.fromString(results.getString("PlayerUUID")),
                        results.getString("Rank"),
                        results.getTimestamp("Until").getTime()));
            }
            results.close();
            LogUtils.debug("Got " + result.size() + " ranks!");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public List<RankData> fetchData(Player p)
    {
        return fetchData(p.getUniqueId());
    }

    public void unload()
    {
        dataSource.close();
    }
}
