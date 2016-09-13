package main.java.net.newtownia.ranksql.commands;

import main.java.net.newtownia.ranksql.executive.RankUpdater;
import main.java.net.newtownia.ranksql.RankSQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sync extends SubCommand {

    public Sync()
    {
        super("sync");
    }

    @Override
    public void execute(RankSQL pl, CommandSender cs, Command cmd, String label, String[] args) {
        if (!cs.hasPermission("ranksql.sync")) { cs.sendMessage("§7[§cRankSQL§7] §cNot enough permissions."); return; }
        for (Player p : Bukkit.getOnlinePlayers()) RankUpdater.synchroniseFromDatabase(pl, p);
        cs.sendMessage("§7[§cRankSQL§7] §aSynchronised ranks.");
    }
}
