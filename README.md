# Noteva — Cloud Notes App

Noteva is a fast, secure, and minimal cloud note-taking Android app that saves notes instantly and syncs them across devices in real time.

Built with modern Android development practices and powered by Firebase, Noteva focuses on simplicity, reliability, and performance.

![Platform](https://img.shields.io/badge/platform-Android-green)
![Language](https://img.shields.io/badge/language-Kotlin-purple)
![License](https://img.shields.io/badge/license-MIT-blue)

---

## ✨ Features

- Real-time cloud sync
- Automatic saving while typing
- Secure Firebase authentication
- Lightweight and fast performance
- Clean and distraction-free UI
- Reliable cloud storage
- Dark mode support

---

## 🛠 Tech Stack

- **Language:** Kotlin  
- **Architecture:** Activity-based (lightweight design)  
- **Backend:** Firebase Realtime Database  
- **Authentication:** Firebase Anonymous Auth  
- **Crash Reporting:** Firebase Crashlytics  
- **Analytics:** Firebase Analytics  
- **UI Components:** Material Design Components  

---

## 🔐 Security

- Data encrypted in transit (HTTPS via Firebase)
- User data isolated per UID
- Secure authentication handled by Firebase

---

## 📦 Installation

Clone repository:

```bash
git clone https://github.com/TutorialsAndroid/Application.git
````

Open in Android Studio and run:

```
Run → Run app
```

---

## ⚙ Configuration

Create a Firebase project and add:

```
google-services.json
```

inside:

```
app/
```

Enable:

* Authentication
* Realtime Database
* Crashlytics
* Analytics

---

## 🧪 Testing

Recommended tests:

* Login → Write note → Restart app → Note persists
* Offline → Write → Reconnect → Sync works
* Logout → Login → Notes restored

---

## 🚀 Release

Build release bundle:

```
Build → Generate Signed Bundle → Android App Bundle (.aab)
```

Upload to Google Play Console.

---

## 📄 Privacy Policy

Privacy policy available at:

```
https://noteva-android.web.app/privacy.html
```

---

## 🧾 Terms

```
https://noteva-android.web.app/terms.html
```

---

## 🗑 Account Deletion

```
https://noteva-android.web.app/delete-account.html
```

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

---

## 📬 Contact

Developer: **Akshay (TutorialsAndroid)**
Email: **[heaticdeveloper@gmail.com](mailto:heaticdeveloper@gmail.com)**

---

## 📜 License

MIT License — feel free to use and modify.

---

## ⭐ Support

If you like this project, consider giving it a star ⭐