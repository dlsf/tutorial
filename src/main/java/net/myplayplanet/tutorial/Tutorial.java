package net.myplayplanet.tutorial;

import net.myplayplanet.commandframework.CommandFramework;
import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.tutorial.commands.CreateTutorialCommand;
import net.myplayplanet.tutorial.commands.ManageTutorialsCommand;
import net.myplayplanet.tutorial.config.Config;
import net.myplayplanet.tutorial.daos.TutorialDao;
import org.bukkit.plugin.java.JavaPlugin;

public class Tutorial extends JavaPlugin {

  private CommandFramework commandFramework;
  private Config config;
  private TutorialDao tutorialDao;
  private InventoryAPI inventoryAPI;

  @Override
  public void onEnable() {

    System.out.println("[Tutorial] Gestartet!");

    this.commandFramework = new CommandFramework(this);
    this.config = new Config("tutorials.yml", this);
    this.tutorialDao = new TutorialDao(config);

    this.registerCommands();

  }

  private void registerCommands() {

    this.commandFramework.registerCommands(new CreateTutorialCommand());
    this.commandFramework.registerCommands(new ManageTutorialsCommand());

  }

}
