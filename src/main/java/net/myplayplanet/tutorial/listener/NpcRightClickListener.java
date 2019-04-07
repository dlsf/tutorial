package net.myplayplanet.tutorial.listener;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NpcRightClickListener implements Listener {

  @EventHandler
  public void onNpcRightClick(NPCRightClickEvent event) {

    //TODO: Change name
    if (!event.getNPC().getName().equalsIgnoreCase("Tom")) {
      return;
    }

    event.getClicker().performCommand("tutorial list");

  }

}
