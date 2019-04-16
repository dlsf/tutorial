package net.myplayplanet.tutorial.commands;

import java.util.List;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.core.platform.bukkit.inventory.page.InventoryPageHolder;
import net.myplayplanet.core.platform.bukkit.util.InventoryAPI;
import net.myplayplanet.core.platform.bukkit.util.InventoryBuilder;
import net.myplayplanet.core.platform.bukkit.util.InventoryItem;
import net.myplayplanet.core.platform.bukkit.util.ItemStackBuilder;
import net.myplayplanet.tutorial.dao.TutorialDao;
import net.myplayplanet.tutorial.listener.AsyncPlayerChatListener;
import net.myplayplanet.tutorial.tutorial.Tutorial;
import net.myplayplanet.tutorial.tutorial.TutorialManager;
import net.myplayplanet.tutorial.tutorial.TutorialType;
import net.myplayplanet.tutorial.tutorial.data.IngameData;
import net.myplayplanet.tutorial.tutorial.data.TutorialData;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TutorialModifyCommand {

  private static int INVENTORY_SIZE = 3 * 9;
  private InventoryAPI inventoryAPI;
  private TutorialManager tutorialManager;
  private TutorialDao tutorialDao;
  private List<Tutorial> tutorialList;

  public TutorialModifyCommand(List<Tutorial> tutorialList, TutorialManager tutorialManager,
      TutorialDao tutorialDao, InventoryAPI inventoryAPI) {

    this.inventoryAPI = inventoryAPI;
    this.tutorialManager = tutorialManager;
    this.tutorialDao = tutorialDao;
    this.tutorialList = tutorialList;

  }

  @Command(name = "tutorial.modify", permission = "tutorial.modify", inGameOnly = true)
  public void tutorialManageCommand(CommandArgs args) {

    Player player = args.getSender(Player.class);

    InventoryPageHolder holder = new InventoryPageHolder(inventoryAPI,
        new ItemStackBuilder(Material.ARROW).name("§6Vorherige Seite").build(),
        new ItemStackBuilder(
            Material.ARROW).name("§6Nächste Seite").build(), 0, INVENTORY_SIZE);
    tutorialList.forEach(tutorial ->
        holder.addItems(new InventoryItem((consumerPlayer, itemStack) -> {
          Tutorial clickedTutorial = tutorialManager
              .getTutorialByName(itemStack.getItemMeta().getDisplayName().replaceAll("§a", ""));
          openTutorialInventory(clickedTutorial, consumerPlayer);
        }, new ItemStackBuilder(tutorial.getType().getMaterial()).name("§a" + tutorial.getName())
            .lore(tutorial.getType().getLore()).build()))
    );

    holder.createAndInitPages("§aTutorials §f| §aVerwaltung");
    player.openInventory(holder.getFirstPage());

  }

  private void openTutorialInventory(Tutorial tutorial, Player player) {

    InventoryBuilder inventoryBuilder = new InventoryBuilder(inventoryAPI,
        "§aTutorials §f| §a" + tutorial.getName(), INVENTORY_SIZE);

    inventoryBuilder.add(new InventoryItem(
        ((player1, itemStack) -> addData(player1, tutorial)),
        new ItemStackBuilder(Material.GREEN_CONCRETE_POWDER).name("§6Tutorial-Daten hinzufügen")
            .lore("§aErstellt neue Tutorial-Daten")
            .lore("§ades Tutorials an")
            .build()), 11);
    inventoryBuilder.add(new InventoryItem(
        (consumerPlayer, itemStack) -> openShowDataInventory(consumerPlayer, tutorial.getName()),
        new ItemStackBuilder(Material.PAPER)
            .name("§6Tutorial-Daten anzeigen")
            .lore("§aZeigt alle Tutorial-Daten")
            .lore("§ades Tutorials an")
            .build()), 13);
    inventoryBuilder.add(new InventoryItem(
        (player1, itemStack1) -> openRemoveTutorialInventory(player1, tutorial),
        new ItemStackBuilder(Material.REDSTONE_BLOCK)
            .name("§6Tutorial löschen")
            .lore("§aLöscht das Tutorial")
            .build()), 15);

    inventoryBuilder.save();
    inventoryBuilder.open(player);

  }

  private void addData(Player player, Tutorial tutorial) {

    if (tutorial.getType() == TutorialType.INGAME) {
      AsyncPlayerChatListener.registerAddPlayerListener(player, tutorial);
      player.closeInventory();
      player.sendMessage(
          "§aBitte stelle dich auf die Location und gebe die Chatnachricht in den Chat ein!");
    } else if (tutorial.getData().isEmpty()) {
      AsyncPlayerChatListener.registerAddPlayerListener(player, tutorial);
      player.closeInventory();
      player.sendMessage("§aBitte gebe die Nachricht ein, die man erhalten soll!");
    } else {
      player.closeInventory();
      player.sendMessage("§cDas Tutorial wurde bereits fertig konfiguriert!");
    }

  }

  private void openShowDataInventory(Player player, String tutorialName) {

    Tutorial currentTutorial = tutorialManager.getTutorialByName(tutorialName);

    if (currentTutorial == null || currentTutorial.getData() == null || currentTutorial.getData()
        .isEmpty()) {
      player.closeInventory();
      player.sendMessage(
          "§cDas Tutorial wurde noch nicht richtig konfiguriert, bitte füge zuerst einen Punkt hinzu!");
      return;
    }

    InventoryPageHolder holder = new InventoryPageHolder(inventoryAPI,
        new ItemStackBuilder(Material.ARROW).name("§6Vorherige Seite").build(),
        new ItemStackBuilder(
            Material.ARROW).name("§6Nächste Seite").build(), 0, INVENTORY_SIZE);

    for (int i = 0; i < currentTutorial.getData().size(); i++) {
      TutorialData currentData = currentTutorial.getData().get(i);
      holder.addItems(new InventoryItem((consumerPlayer2, itemStack2) ->
          openSpecialDataModifyTutorial(consumerPlayer2,
              tutorialManager.getTutorialByName(tutorialName), currentData)
          , new ItemStackBuilder(Material.PAPER).name("§a" + i)
          .lore("§6Message: §a" + currentData.getMessage()).build()));
    }

    holder.createAndInitPages("§a" + currentTutorial.getName() + " §f| §aDaten");
    player.openInventory(holder.getFirstPage());

  }

  private void openRemoveTutorialInventory(Player consumerPlayer, Tutorial tutorial) {

    if (!consumerPlayer.hasPermission("tutorial.delete")) {
      consumerPlayer.closeInventory();
      consumerPlayer.sendMessage("§cDazu hast du keine Rechte!");
      return;
    }

    consumerPlayer.closeInventory();

    InventoryBuilder deleteInventoryBuilder = new InventoryBuilder(inventoryAPI,
        "§aTutorial löschen?", INVENTORY_SIZE);
    deleteInventoryBuilder.add(new InventoryItem((consumerPlayer2, itemStack2) -> {

      tutorialDao.removeTutorial(tutorial.getName());
      tutorialList.remove(tutorial);
      consumerPlayer2.closeInventory();
      consumerPlayer2.sendMessage("§aDas Tutorial wurde erfolgreich gelöscht!");
    }, new ItemStackBuilder(Material.EMERALD_BLOCK).name("§cLöschen").build()), 11);
    deleteInventoryBuilder.add(new InventoryItem((consumerPlayer2, itemStack2) -> {
      consumerPlayer2.closeInventory();
      consumerPlayer2.sendMessage("§cDu hast den Vorgang abgebrochen!");
    }, new ItemStackBuilder(Material.REDSTONE_BLOCK).name("§cNicht löschen").build()), 15);

    deleteInventoryBuilder.save();
    deleteInventoryBuilder.open(consumerPlayer);

  }

  private void openSpecialDataModifyTutorial(Player player, Tutorial tutorial,
      TutorialData tutorialData) {

    InventoryBuilder inventoryBuilder = new InventoryBuilder(inventoryAPI,
        "§aDaten §f| §aVerwaltung", INVENTORY_SIZE);

    inventoryBuilder.add(new InventoryItem((player1, itemStack1) -> {
      player1.closeInventory();
      player1.sendMessage("§aBitte gebe die neue Nachricht in den Chat ein!");
      AsyncPlayerChatListener.registerChangePlayerListener(player1, tutorialData);
    }, new ItemStackBuilder(Material.PAPER).name("§6Nachricht ändern").build()), 11);

    if (tutorialData instanceof IngameData) {
      inventoryBuilder.add(new InventoryItem((player1, itemStack1) -> {
        player1.closeInventory();
        player1.sendMessage(
            "§aBitte stelle dich auf die neue Location und gebe die neue Chatnachricht in den Chat ein!");
        AsyncPlayerChatListener.registerChangePlayerListener(player1, tutorialData);
      }, new ItemStackBuilder(Material.GRASS_BLOCK).name("§6Ort + Nachricht ändern").build()), 13);
    }

    inventoryBuilder.add(new InventoryItem((player1, itemStack1) -> {
      InventoryBuilder deleteInventoryBuilder = new InventoryBuilder(inventoryAPI,
          "§aTutorialdaten löschen?", INVENTORY_SIZE);
      deleteInventoryBuilder.add(new InventoryItem((consumerPlayer2, itemStack2) -> {
        tutorialDao
            .removeTutorialData(tutorial.getName(), tutorial.getData().indexOf(tutorialData));
        consumerPlayer2.sendMessage("§aDie Daten wurden erfolgreich gelöscht!");
      }, new ItemStackBuilder(Material.EMERALD_BLOCK).name("§cLöschen").build()), 11);
      deleteInventoryBuilder.add(new InventoryItem((consumerPlayer2, itemStack2) -> {
        consumerPlayer2.closeInventory();
        consumerPlayer2.sendMessage("§cDu hast den Vorgang abgebrochen!");
      }, new ItemStackBuilder(Material.REDSTONE_BLOCK).name("§cNicht löschen").build()), 15);

      deleteInventoryBuilder.save();
      deleteInventoryBuilder.open(player1);
    }, new ItemStackBuilder(Material.REDSTONE_BLOCK).name("§6Daten löschen")
        .lore("§aLöscht alle Daten für diesen Punkt!").build()), 15);

    inventoryBuilder.save();
    inventoryBuilder.open(player);

  }

}
