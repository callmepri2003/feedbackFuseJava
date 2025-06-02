package models;

public class Feedback {
  public int id;
  public String message;
  public String created_at;

  @Override
  public String toString() {
    return message + "\n(" + created_at + ")";
  }
}
