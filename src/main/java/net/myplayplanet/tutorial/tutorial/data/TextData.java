package net.myplayplanet.tutorial.tutorial.data;

public class TextData extends TutorialData {

  private int counter;
  private String message;

  public TextData(int counter, String message) {
    this.counter = counter;
    this.message = message;
  }

  @Override
  public int getCounter() {
    return counter;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
