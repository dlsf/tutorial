package net.myplayplanet.tutorial.tutorial.data;

import org.bukkit.Location;

public class IngameData extends TutorialData {

  private int counter;
  private String message;
  private Location location;

  public IngameData(int counter, String message, Location location) {
    this.counter = counter;
    this.message = message;
    this.location = location;
  }

  @Override
  public int getCounter() {
    return counter;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Location getLocation() {
    return location;
  }

}
