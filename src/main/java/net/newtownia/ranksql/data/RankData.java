package main.java.net.newtownia.ranksql.data;

import java.util.UUID;

public class RankData {

    private int id;
    private long until;
    private UUID playerUUID;
    private String rankName;

    public RankData(int id, UUID playerUUID, String rankName) {
        this(id, playerUUID, rankName, -1);
    }

    RankData(int id, UUID playerUUID, String rankName, long until) {
        this.id = id;
        this.until = until;
        this.rankName = rankName;
        this.playerUUID = playerUUID;
    }

    public int id() {
        return id;
    }

    public long until() {
        return until;
    }

    public String rankName() {
        return rankName;
    }

    public UUID playerUUID() {
        return playerUUID;
    }

}
