# 📝 Feedback App

A simple, aesthetic JavaFX application that allows users to view and submit anonymous feedback via a REST API.

---

## ✨ Features

- ✅ View all feedback messages (newest first)
- 📨 Submit anonymous feedback (1–250 characters)
- ⚡ Modern, responsive JavaFX UI
- 🔗 Connects to REST API using plain `HttpURLConnection` and `Gson`

---

## 📦 Dependencies

- Java 17+
- JavaFX 21
- Gson (`com.google.code.gson:gson:2.10.1`)

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── api/              # API communication (GET & POST)
│   │   │   └── FeedbackService.java
│   │   ├── models/           # Feedback model class
│   │   │   └── Feedback.java
│   │   └── ui/               # JavaFX application UI
│   │       └── FeedbackApp.java
```

---

## 🛠️ Setup Instructions

### 1. Clone and Build

```bash
git clone <your-repo-url>
cd <project-directory>
```

### 2. Add this `build.gradle`:

```groovy
plugins {
    id 'application'
    id 'org.openjfx.javafxplugin' version '0.0.13'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.google.code.gson:gson:2.10.1'
}

javafx {
    version = "21"
    modules = [ 'javafx.controls' ]
}

application {
    mainClass = 'ui.FeedbackApp'
}
```

### 3. Run the App

```bash
./gradlew run
```

---

## 🌐 API Used

All data is sent to and retrieved from:

```
http://192.168.110.155:8000/api/feedback/
```

### GET `/feedback/`

- Retrieves all feedback messages
- Sample response:
```json
{
  "count": 2,
  "results": [
    {
      "id": 1,
      "message": "Great app!",
      "created_at": "2025-06-01T10:30:00Z"
    }
  ]
}
```

### POST `/feedback/`

- Submit anonymous feedback
- JSON body:
```json
{
  "message": "Your message here"
}
```

---

## 💡 Notes

- API must be running at `http://192.168.110.155:8000`
- Errors are handled gracefully with a status label
- Async threads ensure UI remains responsive

---

## 🖼️ Screenshot

> _(Add a screenshot of your app here if possible)_

---

## 📄 License

MIT – feel free to modify and use.
