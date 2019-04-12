package net.myplayplanet.tutorial.commands;

import java.util.List;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.core.platform.bukkit.inventory.page.InventoryPageHolder;
import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.core.platform.bukkit.util.InventoryBuilder;
import net.myplayplanet.core.platform.bukkit.util.InventoryItem;
import net.myplayplanet.core.platform.bukkit.util.ItemStackBuilder;
import net.myplayplanet.tutorial.tutorial.Tutorial;
import net.myplayplanet.tutorial.tutorial.TutorialManager;
import net.myplayplanet.tutorial.tutorial.TutorialType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TutorialListCommand {

  private final int INVENTORY_SIZE = 3 * 9;

  private InventoryAPI inventoryAPI;
  private List<Tutorial> tutorialList;
  private TutorialManager tutorialManager;

  public TutorialListCommand(InventoryAPI inventoryAPI, List<Tutorial> tutorialList,
      TutorialManager tutorialManager) {

    this.inventoryAPI = inventoryAPI;
    this.tutorialList = tutorialList;
    this.tutorialManager = tutorialManager;

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

    inventoryBuilder.save();
    inventoryBuilder.open(player);

  }

  private void openVideoTutorialInventory(Player player) {

    InventoryPageHolder holder = new InventoryPageHolder(inventoryAPI,
        new ItemStackBuilder(Material.ARROW).name("§6Vorherige Seite").build(),
        new ItemStackBuilder(
            Material.ARROW).name("§6Nächste Seite").build(), 0, INVENTORY_SIZE);

    tutorialList.forEach(tutorial -> {
      if (tutorial.getType() == TutorialType.VIDEO) {
        holder.addItems(new InventoryItem((player1, itemStack) ->
            tutorialManager.startTutorial(player1, tutorialManager
                .getTutorialByName(itemStack.getItemMeta().getDisplayName().replaceAll("§a", "")))
            , new ItemStackBuilder(TutorialType.VIDEO.getMaterial()).name("§a" + tutorial.getName())
            .build()));
      }
    });

    holder.createAndInitPages("§aVideo-Tutorials");
    player.openInventory(holder.getFirstPage());

  }

  private void openForumTutorialInventory(Player player) {

    InventoryPageHolder holder = new InventoryPageHolder(inventoryAPI,
        new ItemStackBuilder(Material.ARROW).name("§6Vorherige Seite").build(),
        new ItemStackBuilder(
            Material.ARROW).name("§6Nächste Seite").build(), 0, INVENTORY_SIZE);

    tutorialList.forEach(tutorial -> {
      if (tutorial.getType() == TutorialType.FORUM) {
        holder.addItems(new InventoryItem((player1, itemStack) ->
            tutorialManager.startTutorial(player1, tutorialManager
                .getTutorialByName(itemStack.getItemMeta().getDisplayName().replaceAll("§a", "")))
            , new ItemStackBuilder(TutorialType.FORUM.getMaterial()).name("§a" + tutorial.getName())
            .build()));
      }
    });

    holder.createAndInitPages("§aForum-Tutorials");
    player.openInventory(holder.getFirstPage());

  }

  private void openIngameTutorialInventory(Player player) {

    InventoryPageHolder holder = new InventoryPageHolder(inventoryAPI,
        new ItemStackBuilder(Material.ARROW).name("§6Vorherige Seite").build(),
        new ItemStackBuilder(
            Material.ARROW).name("§6Nächste Seite").build(), 0, INVENTORY_SIZE);

    tutorialList.forEach(tutorial -> {
      if (tutorial.getType() == TutorialType.INGAME) {
        holder.addItems(new InventoryItem((player1, itemStack) ->
            tutorialManager.startTutorial(player1, tutorialManager
                .getTutorialByName(itemStack.getItemMeta().getDisplayName().replaceAll("§a", "")))
            ,
            new ItemStackBuilder(TutorialType.INGAME.getMaterial()).name("§a" + tutorial.getName())
                .build()));
      }
    });

    holder.createAndInitPages("§aIngame-Tutorials");
    player.openInventory(holder.getFirstPage());

  }

}
