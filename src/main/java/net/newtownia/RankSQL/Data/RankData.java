package main.java.net.newtownia.RankSQL.Data;

import java.util.UUID;

public class RankData
{
    private int id;
    private UUID playerUUID;
    private String rankName;
    private long until;

    RankData(int id, UUID playerUUID, String rankName, long until)
    {
        this.id = id;
        this.playerUUID = playerUUID;
        this.rankName = rankName;
        this.until = until;
    }

    public RankData(int id, UUID playerUUID, String rankName)
    {
        this(id, playerUUID, rankName, -1);
    }

    public int getId() {
        return id;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getRankName() {
        return rankName;
    }

    public long getUntil() {
        return until;
    }
}
