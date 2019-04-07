package net.myplayplanet.tutorial.tutorial;

import net.myplayplanet.tutorial.dao.TutorialDao;

public class TutorialCreator {

  private TutorialDao tutorialDao;

  public TutorialCreator(TutorialDao tutorialDao) {

    this.tutorialDao = tutorialDao;

  }

  public Tutorial createTutorial(String name, TutorialType type) {

    tutorialDao.createTutorial(name, type);
    return tutorialDao.loadTutorial(name);

  }

}
