package main.java.net.newtownia.ranksql.data;

import com.zaxxer.hikari.HikariDataSource;
import main.java.net.newtownia.ranksql.utils.LogUtils;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySql {

    private String table;
    private final HikariDataSource hikariDataSource = new HikariDataSource();

    public MySql(String ip, String port, String database, String username, String password, String table) {
        this.table = table;
        hikariDataSource.setJdbcUrl("jdbc:mysql://" + ip + ':' + port + '/' + database);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        createTableIfNotExists();
    }


    public void disable() {
        hikariDataSource.close();
    }

    public List<RankData> fetchData(Player p)
    {
        return fetchData(p.getUniqueId());
    }

    private void createTableIfNotExists() {
        try (Connection conn = hikariDataSource.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (`Id` int(11) NOT NULL AUTO_INCREMENT,`PlayerUUID` varchar(36) NOT NULL,`Rank` varchar(24) NOT NULL,`Until` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,PRIMARY KEY (`Id`))");
            LogUtils.debug("Tried to create table!");

        } catch (SQLException e) {LogUtils.warn("Cannot create table, maybe it already exists or the user doesn't have the permission to create it!");}
    }

    private List<RankData> fetchData(UUID pUUID) {
        List<RankData> result = new ArrayList<>();
        try (Connection conn = hikariDataSource.getConnection()) {
            LogUtils.debug("Trying to fetch data of uuid: " + pUUID.toString());
            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM `" + table + "` WHERE `PlayerUUID` like \"" + pUUID.toString() +"\"");
            while (results.next()) {
                result.add(new RankData(results.getInt("Id"),
                        UUID.fromString(results.getString("PlayerUUID")),
                        results.getString("Rank"),
                        results.getTimestamp("Until").getTime()));
            }

            results.close();
            LogUtils.debug("Got " + result.size() + " ranks!");

        } catch (SQLException e) {e.printStackTrace();}

        return result;
    }
}
