package main.java.net.newtownia.ranksql.commands;

import main.java.net.newtownia.ranksql.RankSQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Vinc0682 on 26.06.2016.
 */
public class Reload extends SubCommand
{
    public Reload() {
        super("reload");
    }

    @Override
    public void execute(RankSQL pl, CommandSender cs, Command cmd, String label, String[] args) {
        if (cs.hasPermission("ranksql.reload")) {
            pl.reload();
            cs.sendMessage("§7[§cRankSQL§7] §aSuccessfully reloaded");

        } else cs.sendMessage("§7[§cRankSQL§7] §cNot enough permissions.");
    }
}
