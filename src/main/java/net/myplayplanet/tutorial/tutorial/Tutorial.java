package net.myplayplanet.tutorial.tutorial;

import java.util.LinkedList;
import net.myplayplanet.tutorial.tutorial.data.TutorialData;

public class Tutorial {

  private String name;
  private TutorialType type;
  private LinkedList<TutorialData> data;

  Tutorial(String name, TutorialType tutorialType, LinkedList<TutorialData> data) {

    this.name = name;
    this.type = tutorialType;
    this.data = data;

  }

  public String getName() {
    return name;
  }

  public TutorialType getType() {
    return type;
  }

  public LinkedList<TutorialData> getData() {
    return data;
  }

}
