package net.newtownia.RankSQL.Data;

import com.zaxxer.hikari.HikariDataSource;
import net.newtownia.RankSQL.RankSQL;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            statement.executeQuery("CREATE TABLE IF NOT EXISTS `" + table +"` (`Id` int(11) NOT NULL AUTO_INCREMENT,`PlayerUUID` varchar(36) NOT NULL,`Rank` varchar(24) NOT NULL,`Until` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,PRIMARY KEY (`Id`))");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public RankData fetchData(UUID pUUID)
    {
        try
        {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM `" + table +
                    "` WHERE `PlayerUUID` like \"" + pUUID.toString() +"\"");

            if (results.next())
            {
                return new RankData(results.getInt("Id"),
                        UUID.fromString(results.getString("PlayerUUID")),
                        results.getString("Rank"),
                        results.getTimestamp("Until").getTime());
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public RankData fetchData(Player p)
    {
        return fetchData(p.getUniqueId());
    }
}
