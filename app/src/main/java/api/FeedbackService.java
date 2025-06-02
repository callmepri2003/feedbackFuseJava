package api;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.Feedback;

import java.io.*;
import java.net.*;
import java.util.*;

public class FeedbackService {
  private static final String BASE_URL = "http://192.168.110.155:8000/api/feedback/";
  private static final Gson gson = new Gson();

  public static List<Feedback> getFeedback() throws IOException {
    URL url = new URL(BASE_URL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Accept", "application/json");

    if (con.getResponseCode() != 200) {
      throw new IOException("Failed to fetch feedback: " + con.getResponseCode());
    }

    try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
      JsonObject json = JsonParser.parseReader(in).getAsJsonObject();
      return gson.fromJson(json.getAsJsonArray("results"), new TypeToken<List<Feedback>>() {
      }.getType());
    }
  }

  public static Feedback submitFeedback(String message) throws IOException {
    URL url = new URL(BASE_URL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Accept", "application/json");
    con.setDoOutput(true);

    JsonObject body = new JsonObject();
    body.addProperty("message", message);

    try (OutputStream os = con.getOutputStream()) {
      byte[] input = body.toString().getBytes("utf-8");
      os.write(input, 0, input.length);
    }

    if (con.getResponseCode() != 201) {
      try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
        JsonObject error = JsonParser.parseReader(errorReader).getAsJsonObject();
        throw new IOException(error.get("error").getAsString());
      }
    }

    try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
      return gson.fromJson(in, Feedback.class);
    }
  }
}
