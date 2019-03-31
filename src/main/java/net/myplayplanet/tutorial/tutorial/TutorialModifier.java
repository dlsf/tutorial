package net.myplayplanet.tutorial.tutorial;

import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.tutorial.daos.TutorialDao;
import org.bukkit.entity.Player;

public class TutorialModifier {

  private InventoryAPI inventoryAPI;
  private TutorialDao tutorialDao;

  public TutorialModifier(InventoryAPI inventoryAPI, TutorialDao tutorialDao) {

    this.inventoryAPI = inventoryAPI;
    this.tutorialDao = tutorialDao;

  }

  public static void openModifyInventory(Player player, Tutorial tutorial) {

    //TODO

  }

}
