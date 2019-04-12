package net.myplayplanet.tutorial.listener;

import java.util.HashMap;
import net.myplayplanet.tutorial.tutorial.Tutorial;
import net.myplayplanet.tutorial.tutorial.TutorialManager;
import net.myplayplanet.tutorial.tutorial.TutorialType;
import net.myplayplanet.tutorial.tutorial.data.IngameData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {

  private TutorialManager tutorialManager;
  private HashMap<Player, Tutorial> currentTutorial;
  private HashMap<Player, Integer> currentPosition;

  public PlayerToggleSneakListener(TutorialManager tutorialManager,
      HashMap<Player, Tutorial> currentTutorial, HashMap<Player, Integer> currentPosition) {

    this.tutorialManager = tutorialManager;
    this.currentTutorial = currentTutorial;
    this.currentPosition = currentPosition;

  }

  @EventHandler
  public void onSneak(PlayerToggleSneakEvent event) {

    Player player = event.getPlayer();
    if (!player.isSneaking()) {
      return;
    }

    Tutorial tutorial = currentTutorial.get(player);
    if (tutorial == null || tutorial.getType() != TutorialType.INGAME) {
      return;
    }
    IngameData ingameData = (IngameData) tutorial.getData().get(currentPosition.get(player) + 1);

    if (currentPosition.get(player) + 1 != tutorial.getData().size()) {
      player.teleport(ingameData.getLocation());
      player.sendMessage(ingameData.getMessage());

      int currentPositionInt = currentPosition.get(player);
      currentPosition.remove(player);
      currentPosition.put(player, currentPositionInt + 1);
      return;
    }

    player.teleport(ingameData.getLocation());
    player.sendMessage(ingameData.getMessage());

    tutorialManager.stopTutorial(tutorial, player);

  }

}
