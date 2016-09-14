package main.java.net.newtownia.ranksql.data;

import com.zaxxer.hikari.HikariDataSource;
import main.java.net.newtownia.ranksql.RankSQL;
import main.java.net.newtownia.ranksql.utils.Call;
import main.java.net.newtownia.ranksql.utils.LogUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.UUID;

public class MySql {

    private final String table;

    private final HikariDataSource hikariDataSource = new HikariDataSource();

    public MySql(String ip, String port, String database, String username, String password, String table) {
        this.table = table;
        hikariDataSource.setJdbcUrl("jdbc:mysql://" + ip + ':' + port + '/' + database);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        sendUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (`Id` int(11) NOT NULL AUTO_INCREMENT,`PlayerUUID` varchar(36) NOT NULL,`Rank` varchar(24) NOT NULL,`Until` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,PRIMARY KEY (`Id`))");
    }

    public void disable() {
        hikariDataSource.close();
    }

    public void sendUpdate(String query) {
        RankSQL.runAsync(() -> {
            try (Connection connection = hikariDataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) { statement.execute(); }
            catch (Exception e) { System.out.println("Could not send update to database, reason: " + e.getMessage()); e.printStackTrace(); }
        });
    }

    public void sendQuery(String query, Call<ResultSet> call) {
        RankSQL.runAsync(() -> {
            try (Connection connection = hikariDataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query))  { call.call(statement.executeQuery()); }
            catch (Exception e) { System.out.println("Could not send query to database, reason: " + e.getMessage()); e.printStackTrace(); }
        });
    }

    public void fetchData(UUID uuid, Call<LinkedHashSet<RankData>> call) {
            sendQuery("SELECT * FROM `" + table + "` WHERE `PlayerUUID` like \"" + uuid.toString() + "\"", resultSet -> {
                try {
                    LinkedHashSet<RankData> result = new LinkedHashSet<>();
                    LogUtils.debug("Trying to fetch data of uuid: " + uuid.toString());
                    while (resultSet.next()) {
                        result.add(new RankData(resultSet.getInt("Id"),
                                UUID.fromString(resultSet.getString("PlayerUUID")),
                                resultSet.getString("Rank"),
                                resultSet.getTimestamp("Until").getTime()));
                    }

                    LogUtils.debug("Got " + result.size() + " ranks!");
                    call.call(result);

                } catch (Exception e) {e.printStackTrace();}
            });
    }
}
