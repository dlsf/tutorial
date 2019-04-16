package net.myplayplanet.tutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.myplayplanet.commandframework.CommandFramework;
import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.tutorial.commands.TutorialCreateCommand;
import net.myplayplanet.tutorial.commands.TutorialHelpCommand;
import net.myplayplanet.tutorial.commands.TutorialListCommand;
import net.myplayplanet.tutorial.commands.TutorialModifyCommand;
import net.myplayplanet.tutorial.config.Config;
import net.myplayplanet.tutorial.dao.TutorialDao;
import net.myplayplanet.tutorial.listener.AsyncPlayerChatListener;
import net.myplayplanet.tutorial.listener.NpcRightClickListener;
import net.myplayplanet.tutorial.listener.PlayerToggleSneakListener;
import net.myplayplanet.tutorial.tutorial.TutorialCreator;
import net.myplayplanet.tutorial.tutorial.TutorialManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Tutorial extends JavaPlugin {

  private static List<net.myplayplanet.tutorial.tutorial.Tutorial> tutorialList;

  private HashMap<Player, net.myplayplanet.tutorial.tutorial.Tutorial> currentTutorial;
  private HashMap<Player, Integer> currentPosition;
  private CommandFramework commandFramework;
  private Config config;
  private TutorialDao tutorialDao;
  private InventoryAPI inventoryAPI;
  private TutorialCreator tutorialCreator;
  private TutorialManager tutorialManager;

  @Override
  public void onEnable() {

    System.out.println("[Tutorial] Gestartet!");

    tutorialList = new ArrayList<>();
    this.currentTutorial = new HashMap<>();
    this.currentPosition = new HashMap<>();
    this.commandFramework = new CommandFramework(this);
    this.config = new Config("tutorials.yml", this);
    this.tutorialDao = new TutorialDao(config, tutorialList);
    this.inventoryAPI = new InventoryAPI(this);
    this.tutorialCreator = new TutorialCreator(tutorialDao);
    this.tutorialManager = new TutorialManager(tutorialList, currentTutorial, currentPosition);

    this.tutorialDao.reload();

    this.registerCommands();
    this.registerListener();

  }

  private void registerCommands() {

    this.commandFramework
        .registerCommands(new TutorialCreateCommand(tutorialDao, tutorialCreator, inventoryAPI));
    this.commandFramework.registerCommands(
        new TutorialModifyCommand(tutorialList, tutorialManager, tutorialDao, inventoryAPI));
    this.commandFramework.registerCommands(new TutorialHelpCommand());
    this.commandFramework
        .registerCommands(new TutorialListCommand(inventoryAPI, tutorialList, tutorialManager));

  }

  private void registerListener() {

    PluginManager pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new NpcRightClickListener(), this);
    pluginManager.registerEvents(
        new PlayerToggleSneakListener(tutorialManager, currentTutorial, currentPosition), this);
    pluginManager.registerEvents(new AsyncPlayerChatListener(tutorialDao), this);

  }

}
