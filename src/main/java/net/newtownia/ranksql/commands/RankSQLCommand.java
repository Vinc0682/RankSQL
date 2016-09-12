package main.java.net.newtownia.ranksql.commands;

import main.java.net.newtownia.ranksql.RankSQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by Vinc0682 on 26.06.2016.
 */
public class RankSQLCommand implements CommandExecutor
{
    private RankSQL pl;
    private HashMap<String, SubCommand> commands;

    public RankSQLCommand(RankSQL pl)
    {
        this.pl = pl;

        commands = new HashMap<>();
        add(new Help());
        add(new Reload());
        add(new Sync());
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        String commandName = "help";
        if (args.length > 0) commandName = args[0];
        commandName = commandName.toLowerCase();
        SubCommand command = null;
        if (commands.containsKey(commandName)) command = commands.get(commandName);
        if (command == null) command = commands.get("help");
        command.execute(pl, cs, cmd, label, args);
        return true;
    }

    private void add(SubCommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

}
