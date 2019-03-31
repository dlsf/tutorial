package net.myplayplanet.tutorial.tutorial;

public enum TutorialType {

  VIDEO("Video"),
  FORUM("Forum"),
  INGAME("Ingame");

  private String name;

  TutorialType(String name) {

    this.name = name;

  }

  public String getName() {
    return name;
  }

}
