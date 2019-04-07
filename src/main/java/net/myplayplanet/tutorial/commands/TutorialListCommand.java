package net.myplayplanet.tutorial.commands;

import java.util.List;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.core.platform.bukkit.util.InventoryBuilder;
import net.myplayplanet.core.platform.bukkit.util.InventoryItem;
import net.myplayplanet.core.platform.bukkit.util.ItemStackBuilder;
import net.myplayplanet.tutorial.tutorial.Tutorial;
import net.myplayplanet.tutorial.tutorial.TutorialType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TutorialListCommand {

  private final int INVENTORY_SIZE = 3 * 9;
  private InventoryAPI inventoryAPI;
  private List<Tutorial> tutorialList;

  public TutorialListCommand(InventoryAPI inventoryAPI, List<Tutorial> tutorialList) {

    this.inventoryAPI = inventoryAPI;
    this.tutorialList = tutorialList;

  }

  @Command(name = "tutorial.list", inGameOnly = true)
  public void listTutorialCommand(CommandArgs args) {

    Player player = args.getSender(Player.class);

    InventoryBuilder inventoryBuilder = new InventoryBuilder(inventoryAPI, "§aTutorials",
        INVENTORY_SIZE);
    inventoryBuilder.add(new InventoryItem((consumerPlayer, itemStack) -> {
      player.closeInventory();
      openVideoTutorialInventory(player);
    }, new ItemStackBuilder(TutorialType.VIDEO.getMaterial())
        .name("§a" + TutorialType.VIDEO.getName()).lore(TutorialType.VIDEO.getLore()).build()), 11);
    inventoryBuilder.add(new InventoryItem((consumerPlayer, itemStack) -> {
      player.closeInventory();
      openForumTutorialInventory(player);
    }, new ItemStackBuilder(TutorialType.FORUM.getMaterial())
        .name("§a" + TutorialType.FORUM.getName()).lore(TutorialType.FORUM.getLore()).build()), 13);
    inventoryBuilder.add(new InventoryItem((consumerPlayer, itemStack) -> {
          player.closeInventory();
          openIngameTutorialInventory(player);
        }, new ItemStackBuilder(TutorialType.INGAME.getMaterial())
            .name("§a" + TutorialType.INGAME.getName()).lore(TutorialType.INGAME.getLore()).build()),
        15);
    for (int i = 0; i < INVENTORY_SIZE - 1; i++) {
      if (inventoryBuilder.getItems().get(i) == null) {
        inventoryBuilder.add(new InventoryItem((consumerPlayer, itemStack) -> {
          //Do nothing
        }, new ItemStackBuilder(Material.GRAY_STAINED_GLASS).build()), i);
      }
    }

  }

  private void openVideoTutorialInventory(Player player) {

    //TODO

  }

  private void openForumTutorialInventory(Player player) {

    //TODO

  }

  private void openIngameTutorialInventory(Player player) {

    //TODO

  }

}
