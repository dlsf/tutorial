package net.myplayplanet.tutorial.tutorial;

import java.util.HashMap;
import java.util.List;
import net.myplayplanet.tutorial.tutorial.data.IngameData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TutorialManager {

  private static TutorialManager instance;
  private List<Tutorial> tutorialList;
  private HashMap<Player, Tutorial> currentTutorial;
  private HashMap<Player, Integer> currentPosition;

  public TutorialManager(List<Tutorial> list, HashMap<Player, Tutorial> currentTutorial,
      HashMap<Player, Integer> currentPosition) {

    instance = this;
    this.tutorialList = list;
    this.currentTutorial = currentTutorial;
    this.currentPosition = currentPosition;

  }

  public static TutorialManager getInstance() {

    return instance;

  }

  public Tutorial getTutorialByName(String name) {

    return tutorialList.stream()
        .filter(tutorial -> tutorial.getName().equals(name))
        .findFirst()
        .orElse(null);

  }

  public void startTutorial(Player player, Tutorial tutorial) {

    if (tutorial.getData().size() == 0) {
      player.sendMessage("§cDas Tutorial wurde noch nicht richtig konfiguriert!");
      return;
    }

    if (tutorial.getType() == TutorialType.INGAME) {
      currentTutorial.put(player, tutorial);
      currentPosition.put(player, 0);

      IngameData ingameData = (IngameData) tutorial.getData().get(0);
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', ingameData.getMessage()));
      player.teleport(ingameData.getLocation());
      player.sendMessage("1");
    } else {
      player.sendMessage(
          ChatColor.translateAlternateColorCodes('&', tutorial.getData().getFirst().getMessage()));
      player.sendMessage("2");
    }

  }

  public void stopTutorial(Tutorial tutorial, Player player) {

    currentTutorial.remove(player);
    currentPosition.remove(player);

    player
        .sendMessage("§aDu hast das Tutorial §6" + tutorial.getName() + " §aerfolgreich beendet!");

  }

}
