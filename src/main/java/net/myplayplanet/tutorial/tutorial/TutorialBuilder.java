package net.myplayplanet.tutorial.tutorial;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import net.myplayplanet.tutorial.tutorial.data.TutorialData;

public class TutorialBuilder {

  private String name;
  private TutorialType type;
  private LinkedList<TutorialData> data;

  public TutorialBuilder setName(String name) {

    this.name = name;
    return this;

  }

  public TutorialBuilder setType(TutorialType type) {

    this.type = type;
    return this;

  }

  public TutorialBuilder addTutorialData(TutorialData... data) {

    this.data.addAll(Arrays.asList(data));
    return this;

  }

  public Tutorial build() {

    Objects.requireNonNull(name);
    Objects.requireNonNull(type);
    data = data == null ? new LinkedList<>() : data;

    return new Tutorial(name, type, data);

  }

}
