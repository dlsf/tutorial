package net.myplayplanet.tutorial;

import net.myplayplanet.commandframework.CommandFramework;
import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.tutorial.commands.TutorialCreateCommand;
import net.myplayplanet.tutorial.commands.TutorialHelpCommand;
import net.myplayplanet.tutorial.commands.TutorialListCommand;
import net.myplayplanet.tutorial.commands.TutorialManageCommand;
import net.myplayplanet.tutorial.config.Config;
import net.myplayplanet.tutorial.daos.TutorialDao;
import net.myplayplanet.tutorial.tutorial.TutorialCreator;
import net.myplayplanet.tutorial.tutorial.TutorialModifier;
import org.bukkit.plugin.java.JavaPlugin;

public class Tutorial extends JavaPlugin {

  private CommandFramework commandFramework;
  private Config config;
  private TutorialDao tutorialDao;
  private InventoryAPI inventoryAPI;
  private TutorialCreator tutorialCreator;
  private TutorialModifier tutorialModifier;

  @Override
  public void onEnable() {

    System.out.println("[Tutorial] Gestartet!");

    this.commandFramework = new CommandFramework(this);
    this.config = new Config("tutorials.yml", this);
    this.tutorialDao = new TutorialDao(config);
    this.inventoryAPI = new InventoryAPI(this);
    this.tutorialCreator = new TutorialCreator(tutorialDao);
    this.tutorialModifier = new TutorialModifier(inventoryAPI, tutorialDao);

    this.registerCommands();

  }

  private void registerCommands() {

    this.commandFramework.registerCommands(new TutorialCreateCommand());
    this.commandFramework.registerCommands(new TutorialManageCommand());
    this.commandFramework.registerCommands(new TutorialHelpCommand());
    this.commandFramework.registerCommands(new TutorialListCommand());

  }

}
