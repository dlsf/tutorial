package net.myplayplanet.tutorial.commands;

import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.core.platform.bukkit.util.InventoryBuilder;
import net.myplayplanet.core.platform.bukkit.util.InventoryItem;
import net.myplayplanet.core.platform.bukkit.util.ItemStackBuilder;
import net.myplayplanet.tutorial.dao.TutorialDao;
import net.myplayplanet.tutorial.tutorial.TutorialCreator;
import net.myplayplanet.tutorial.tutorial.TutorialType;
import org.bukkit.entity.Player;

public class TutorialCreateCommand {

  private final int INVENTORY_SIZE = 3 * 9;
  private TutorialDao tutorialDao;
  private TutorialCreator tutorialCreator;
  private InventoryAPI inventoryAPI;

  public TutorialCreateCommand(TutorialDao tutorialDao, TutorialCreator tutorialCreator,
      InventoryAPI inventoryAPI) {

    this.tutorialDao = tutorialDao;
    this.tutorialCreator = tutorialCreator;
    this.inventoryAPI = inventoryAPI;

  }

  @Command(name = "tutorial.create", permission = "tutorial.create", inGameOnly = true)
  public void createTutorialCommand(CommandArgs args) {

    Player player = args.getSender(Player.class);

    if (args.length() != 1) {
      player.sendMessage("§cBitte verwende §6/tutorial create <Name>");
      return;
    }
    String name = args.getArgument(0);

    if (tutorialDao.existsTutorial(name)) {
      player.sendMessage("§cEin Tutorial mit diesem Namen existiert bereits!");
      return;
    }

    InventoryBuilder inventoryBuilder = new InventoryBuilder(inventoryAPI,
        "§aTutorial erstellen §7| §aTutorialtyp", INVENTORY_SIZE);
    inventoryBuilder.add(new InventoryItem((consumerPlayer, itemStack) -> {
      player.closeInventory();
      tutorialCreator.createTutorial(name, TutorialType.VIDEO);
      consumerPlayer.sendMessage("§aDu hast das Tutorial erfolgreich erstellt!");
      consumerPlayer.sendMessage("§aBearbeite das Tutorial mit §6/tutorial modify§a!");
    }, new ItemStackBuilder(TutorialType.VIDEO.getMaterial())
        .name("§a" + TutorialType.VIDEO.getName()).lore(TutorialType.VIDEO.getLore()).build()), 11);
    inventoryBuilder.add(new InventoryItem((consumerPlayer, itemStack) -> {
      player.closeInventory();
      tutorialCreator.createTutorial(name, TutorialType.FORUM);
      consumerPlayer.sendMessage("§aDu hast das Tutorial erfolgreich erstellt!");
      consumerPlayer.sendMessage("§aBearbeite das Tutorial mit §6/tutorial modify§a!");
    }, new ItemStackBuilder(TutorialType.FORUM.getMaterial())
        .name("§a" + TutorialType.FORUM.getName()).lore(TutorialType.FORUM.getLore()).build()), 13);
    inventoryBuilder.add(new InventoryItem((consumerPlayer, itemStack) -> {
          player.closeInventory();
          tutorialCreator.createTutorial(name, TutorialType.INGAME);
          consumerPlayer.sendMessage("§aDu hast das Tutorial erfolgreich erstellt!");
          consumerPlayer.sendMessage("§aBearbeite das Tutorial mit §6/tutorial modify§a!");
        }, new ItemStackBuilder(TutorialType.INGAME.getMaterial())
            .name("§a" + TutorialType.INGAME.getName()).lore(TutorialType.INGAME.getLore()).build()),
        15);

    inventoryBuilder.save();
    inventoryBuilder.open(player);

  }

}
