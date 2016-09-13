package main.java.net.newtownia.ranksql.commands;

import main.java.net.newtownia.ranksql.RankSQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Vinc0682 on 26.06.2016.
 */
public abstract class SubCommand {

    private final String name;

    public SubCommand(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(RankSQL pl, CommandSender cs, Command cmd, String label, String[] args);

}
