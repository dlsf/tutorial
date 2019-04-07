package net.myplayplanet.tutorial;

import java.util.ArrayList;
import java.util.List;
import net.myplayplanet.commandframework.CommandFramework;
import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.tutorial.commands.TutorialCreateCommand;
import net.myplayplanet.tutorial.commands.TutorialHelpCommand;
import net.myplayplanet.tutorial.commands.TutorialListCommand;
import net.myplayplanet.tutorial.commands.TutorialModifyCommand;
import net.myplayplanet.tutorial.config.Config;
import net.myplayplanet.tutorial.dao.TutorialDao;
import net.myplayplanet.tutorial.listener.NpcRightClickListener;
import net.myplayplanet.tutorial.tutorial.TutorialCreator;
import net.myplayplanet.tutorial.tutorial.TutorialModifier;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Tutorial extends JavaPlugin {

  private static List<net.myplayplanet.tutorial.tutorial.Tutorial> tutorialList;

  private CommandFramework commandFramework;
  private Config config;
  private TutorialDao tutorialDao;
  private InventoryAPI inventoryAPI;
  private TutorialCreator tutorialCreator;
  private TutorialModifier tutorialModifier;

  public static net.myplayplanet.tutorial.tutorial.Tutorial getTutorialByName(String name) {

    return tutorialList.stream()
        .filter(tutorial -> tutorial.getName().equals(name))
        .findFirst()
        .orElse(null);

  }

  @Override
  public void onEnable() {

    System.out.println("[Tutorial] Gestartet!");

    tutorialList = new ArrayList<>();
    this.commandFramework = new CommandFramework(this);
    this.config = new Config("tutorials.yml", this);
    this.tutorialDao = new TutorialDao(config, tutorialList);
    this.inventoryAPI = new InventoryAPI(this);
    this.tutorialCreator = new TutorialCreator(tutorialDao);
    this.tutorialModifier = new TutorialModifier(inventoryAPI, tutorialDao);

    this.tutorialDao.reload();

    this.registerCommands();

  }

  private void registerCommands() {

    this.commandFramework
        .registerCommands(new TutorialCreateCommand(tutorialDao, tutorialCreator, inventoryAPI));
    this.commandFramework.registerCommands(new TutorialModifyCommand());
    this.commandFramework.registerCommands(new TutorialHelpCommand());
    this.commandFramework.registerCommands(new TutorialListCommand(inventoryAPI, tutorialList));

  }

  private void registerListener() {

    Bukkit.getPluginManager().registerEvents(new NpcRightClickListener(), this);

  }

}
