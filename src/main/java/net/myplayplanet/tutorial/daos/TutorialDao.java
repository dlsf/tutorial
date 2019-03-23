package net.myplayplanet.tutorial.daos;

import net.myplayplanet.tutorial.tutorial.data.TutorialData;
import net.myplayplanet.tutorial.tutorial.TutorialType;
import net.myplayplanet.tutorial.config.Config;

public class TutorialDao {

  private Config config;

  public TutorialDao(Config config) {
    this.config = config;
  }

  public void createTutorial(String name, TutorialType type, TutorialData tutorialData) {

  }

  public Tutorial loadTutorial(String name) {

  }

}
