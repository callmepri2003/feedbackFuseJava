package ui;

import api.FeedbackService;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Feedback;

import java.util.List;

public class FeedbackApp extends Application {
  private TextArea feedbackInput;
  private ListView<Feedback> feedbackList;
  private Label statusLabel;

  @Override
  public void start(Stage stage) {
    feedbackInput = new TextArea();
    feedbackInput.setPromptText("Write your feedback here (max 250 characters)");
    feedbackInput.setWrapText(true);

    Button submitBtn = new Button("Submit");
    submitBtn.setOnAction(e -> submitFeedback());

    feedbackList = new ListView<>();
    feedbackList.setPrefHeight(300);

    statusLabel = new Label();

    VBox layout = new VBox(10,
        new Label("Submit Feedback"), feedbackInput,
        submitBtn, new Label("All Feedback:"), feedbackList, statusLabel);
    layout.setPadding(new Insets(20));
    layout.setAlignment(Pos.TOP_LEFT);

    loadFeedback();

    stage.setScene(new Scene(layout, 500, 600));
    stage.setTitle("Feedback App");
    stage.show();
  }

  private void loadFeedback() {
    new Thread(() -> {
      try {
        List<Feedback> feedback = FeedbackService.getFeedback();
        javafx.application.Platform.runLater(() -> {
          feedbackList.getItems().setAll(feedback);
          statusLabel.setText("Loaded " + feedback.size() + " feedback messages.");
        });
      } catch (Exception e) {
        javafx.application.Platform.runLater(() -> statusLabel.setText("Failed to load feedback: " + e.getMessage()));
      }
    }).start();
  }

  private void submitFeedback() {
    String message = feedbackInput.getText().trim();
    if (message.isEmpty() || message.length() > 250) {
      statusLabel.setText("Message must be between 1 and 250 characters.");
      return;
    }

    new Thread(() -> {
      try {
        Feedback newFeedback = FeedbackService.submitFeedback(message);
        javafx.application.Platform.runLater(() -> {
          feedbackList.getItems().add(0, newFeedback);
          feedbackInput.clear();
          statusLabel.setText("Feedback submitted successfully!");
        });
      } catch (Exception e) {
        javafx.application.Platform.runLater(() -> statusLabel.setText("Error: " + e.getMessage()));
      }
    }).start();
  }
}
