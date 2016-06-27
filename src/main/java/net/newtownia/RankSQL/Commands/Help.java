package main.java.net.newtownia.RankSQL.Commands;

import main.java.net.newtownia.RankSQL.RankSQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Vinc0682 on 26.06.2016.
 */
public class Help extends SubCommand
{
    public Help() {
        super("help");
    }

    @Override
    public void execute(RankSQL pl, CommandSender cs, Command cmd, String label, String[] args)
    {
        cs.sendMessage("§7[§cRankSQL§7] Use /ranksql <reload|sync>");
    }
}
