package net.myplayplanet.tutorial.tutorial;

import org.bukkit.Material;

public enum TutorialType {

  VIDEO("Video", Material.RED_WOOL, "§6Ein Tutorial in Form eines Links\n§6zu einem Video"),
  FORUM("Forum", Material.BLUE_WOOL, "§6Ein Tutorial in Form eines Links\n§6zu einem Forumbeitrag"),
  INGAME("Ingame", Material.GRASS_BLOCK, "§6Ein Tutorial in Form eines Ingame-Tutorials");

  private String name;
  private Material material;
  private String lore;

  TutorialType(String name, Material material, String lore) {

    this.name = name;
    this.material = material;
    this.lore = lore;

  }

  public String getName() {
    return name;
  }

  public Material getMaterial() {
    return material;
  }

  public String getLore() {
    return lore;
  }

}
