package net.myplayplanet.tutorial.commands;

import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.tutorial.tutorial.TutorialCreator;
import net.myplayplanet.tutorial.tutorial.TutorialType;
import org.bukkit.entity.Player;

public class TutorialCreateCommand {

  @Command(name = "tutorial.create", permission = "tutorial.create", inGameOnly = true)
  public void createTutorialCommand(CommandArgs args) {

    Player player = args.getSender(Player.class);

    if (args.length() != 1) {
      player.sendMessage("§cBitte verwende §6/tutorial create <Name>");
      return;
    }

    //TODO
    TutorialCreator.createTutorial(args.getArgument(0), TutorialType.FORUM);

  }

}
