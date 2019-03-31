package net.myplayplanet.tutorial.tutorial;

import net.myplayplanet.tutorial.daos.TutorialDao;

public class TutorialCreator {

  private TutorialDao tutorialDao;

  public TutorialCreator(TutorialDao tutorialDao) {

    this.tutorialDao = tutorialDao;

  }

  public static Tutorial createTutorial(String name, TutorialType type) {

    //TODO
    return new TutorialBuilder().setName(name).setType(type).build();

  }

}
