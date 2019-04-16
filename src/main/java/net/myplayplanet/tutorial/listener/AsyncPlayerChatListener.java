package net.myplayplanet.tutorial.listener;

import java.util.HashMap;
import net.myplayplanet.tutorial.dao.TutorialDao;
import net.myplayplanet.tutorial.tutorial.Tutorial;
import net.myplayplanet.tutorial.tutorial.data.IngameData;
import net.myplayplanet.tutorial.tutorial.data.TutorialData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

  private static HashMap<Player, TutorialData> changePlayerListener;
  private static HashMap<Player, Tutorial> addPlayerListener;

  private TutorialDao tutorialDao;

  public AsyncPlayerChatListener(TutorialDao tutorialDao) {

    changePlayerListener = new HashMap<>();
    addPlayerListener = new HashMap<>();
    this.tutorialDao = tutorialDao;

  }

  public static void registerChangePlayerListener(Player player, TutorialData tutorialData) {

    changePlayerListener.put(player, tutorialData);

  }

  public static void registerAddPlayerListener(Player player, Tutorial tutorial) {

    addPlayerListener.put(player, tutorial);

  }

  @EventHandler
  public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

    Player player = event.getPlayer();

    if (changePlayerListener.containsKey(player)) {

      TutorialData tutorialData = changePlayerListener.get(player);

      tutorialDao.changeMessage(tutorialData, event.getMessage());
      if (tutorialData instanceof IngameData) {
        tutorialDao.changeLocation(tutorialData, player.getLocation());
      }

      player.sendMessage("§aDu hast die Daten erfolgreich geändert!");
      changePlayerListener.remove(player);

    } else if (addPlayerListener.containsKey(player)) {

      //TODO

    }


  }

}
