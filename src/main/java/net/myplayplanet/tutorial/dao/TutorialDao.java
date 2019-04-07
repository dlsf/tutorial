package net.myplayplanet.tutorial.dao;

import java.util.List;
import net.myplayplanet.tutorial.config.Config;
import net.myplayplanet.tutorial.tutorial.Tutorial;
import net.myplayplanet.tutorial.tutorial.TutorialBuilder;
import net.myplayplanet.tutorial.tutorial.TutorialType;
import net.myplayplanet.tutorial.tutorial.data.IngameData;
import net.myplayplanet.tutorial.tutorial.data.TextData;
import net.myplayplanet.tutorial.tutorial.data.TutorialData;

public class TutorialDao {

  private Config config;
  private List<Tutorial> tutorialList;

  public TutorialDao(Config config, List<Tutorial> tutorialList) {

    this.config = config;
    this.tutorialList = tutorialList;

  }

  public void createTutorial(String name, TutorialType type, TutorialData... tutorialData) {

    List<String> tutorialList = config.getStringList("tutorial-names");
    tutorialList.add(name);

    config.set("tutorial-names", tutorialList);
    config.set(name + ".type", type.name());
    for (int i = 0; i < tutorialData.length; i++) {
      config.set(name + "." + i + ".message", tutorialData[i].getMessage());
      if (type == TutorialType.INGAME) {
        config.set(name + "." + i + ".location", ((IngameData) tutorialData[i]).getLocation());
      }
    }
    config.set(name + ".data-amount", tutorialData.length);
    config.save();

  }

  public void addTutorialData(String name, TutorialData... data) {

    for (int i = 0; i < data.length; i++) {
      if (data[i] instanceof IngameData) {
        config
            .set(name + "." + (getTutorialDataAmount(name) + i) + ".message", data[i].getMessage());
        config.set(name + "." + (getTutorialDataAmount(name) + i) + ".location",
            ((IngameData) data[i]).getLocation());
      } else {
        config.set(name + "." + getTutorialDataAmount(name) + ".message", data[i].getMessage());
      }
    }
    config.set(name + ".data-amount", getTutorialDataAmount(name) + data.length);
    config.save();

  }

  public void removeTutorial(String name) {

    List<String> tutorialNames = config.getStringList("tutorial-names");
    tutorialNames.remove(name);

    config.set("tutorial-names", tutorialNames);
    for (int i = 0; i < config.getInt(name + ".data-amount") - 1; i++) {
      config.set(name + "." + i + ".message", null);
      config.set(name + "." + i + ".location.world", null);
      config.set(name + "." + i + ".location.x", null);
      config.set(name + "." + i + ".location.y", null);
      config.set(name + "." + i + ".location.z", null);
      config.set(name + "." + i + ".location.yaw", null);
      config.set(name + "." + i + ".location.pitch", null);
      config.set(name + ".data-amount", null);
    }
    config.save();

  }

  public void removeTutorialData(String name, int id) {

    config.set(name + "." + id + ".message", null);
    config.set(name + "." + id + ".location.world", null);
    config.set(name + "." + id + ".location.x", null);
    config.set(name + "." + id + ".location.y", null);
    config.set(name + "." + id + ".location.z", null);
    config.set(name + "." + id + ".location.yaw", null);
    config.set(name + "." + id + ".location.pitch", null);
    config.set(name + ".data-amount", getTutorialDataAmount(name) - 1);
    config.save();

  }

  public List<String> getTutorialNames() {

    return config.getStringList("tutorial-names");

  }

  public boolean existsTutorial(String name) {

    return config.getString(name + ".type") != null;

  }

  private int getTutorialDataAmount(String name) {

    return config.getInt(name + ".data-amount");

  }

  public Tutorial loadTutorial(String name) {

    TutorialType tutorialType = TutorialType.valueOf(config.getString(name + ".type"));
    TutorialBuilder tutorialBuilder = new TutorialBuilder().setName(name)
        .setType(TutorialType.valueOf(config.getString(name + ".type")));
    for (int i = 0; i < config.getInt(name + ".data-amount"); i++) {
      if (tutorialType == TutorialType.INGAME) {
        tutorialBuilder.addTutorialData(
            new IngameData(i, config.getString(name + "." + i + ".message"),
                config.getLocation(name + "." + i + ".location")));
      } else {
        tutorialBuilder
            .addTutorialData(new TextData(i, config.getString(name + "." + i + ".message")));
      }
    }
    Tutorial tutorial = tutorialBuilder.build();
    tutorialList.add(tutorial);
    return tutorial;

  }

  public void reload() {

    tutorialList.clear();
    getTutorialNames().forEach(this::loadTutorial);

  }

}