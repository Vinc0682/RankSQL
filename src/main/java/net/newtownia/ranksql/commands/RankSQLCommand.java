package main.java.net.newtownia.ranksql.commands;

import main.java.net.newtownia.ranksql.RankSQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by Vinc0682 on 26.06.2016.
 */
public class RankSQLCommand implements CommandExecutor {

    private RankSQL pl;
    private final HashMap<String, SubCommand> commands = new HashMap<>();

    public RankSQLCommand(RankSQL pl) {
        this.pl = pl;
        add(new Help());
        add(new Reload());
        add(new Sync());
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        SubCommand command = commands.get((args.length > 0  ? args[0] : "help").toLowerCase());
        if (command == null) cs.sendMessage("§7[§cRankSQL§7] Use /ranksql <reload|sync>");
        else command.execute(pl, cs, cmd, label, args);
        return true;
    }

    private void add(SubCommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

}
