package net.myplayplanet.tutorial.commands;

import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import org.bukkit.command.CommandSender;

public class TutorialHelpCommand {

  @Command(name = "tutorial.help", aliases = {
      "tutorial"}, permission = "tutorial.help")
  public void helpTutorialCommand(CommandArgs args) {

    CommandSender commandSender = args.getSender(CommandSender.class);
    commandSender.sendMessage("§7----------[§6Tutorials§7]----------");
    commandSender.sendMessage("§7/tutorial help §f| §6Zeigt diese Nachricht an");
    commandSender.sendMessage("§7/tutorial create <Name> §f| §6Erstellt ein Tutorial");
    commandSender.sendMessage("§7/tutorial modify <Name> §f| §6Verwaltet ein Tutorial");
    commandSender.sendMessage("§7/tutorial list §f| §6Zeigt alle Tutorials an");

  }

}
