# 📅 Meeting Scheduler UI – Compose Multiplatform (KMP)

A Kotlin Multiplatform app that mimics Calendly’s meeting scheduling interface using **Compose Multiplatform**. Users can view available meeting times, select a slot, enter personal details, and simulate event scheduling.

---

## ✨ Features

- 📆 Calendar-like view with available time slots (fetched via API)
- ⏰ Time slot selection
- 📝 Form to enter name and email
- 🧾 Displays the simulated payload to be sent to backend
- 🧩 Shared UI and logic for Android and iOS using Compose Multiplatform

---

## 💻 Technologies

- **Kotlin Multiplatform (KMP)**
- **Compose Multiplatform** (Jetpack Compose for Android + iOS)
- **MVVM architecture**
- **Ktor Client** for HTTP requests
- **Kotlinx Serialization** for JSON parsing

---

## 🧠 Architecture Overview

- `shared`: ViewModels, domain logic, API handling, and models
- `androidApp`: Android launcher with Compose integration
- `iosApp`: iOS launcher using Compose for iOS

---

## 🌐 Available Time Slots – Mock API Server

The app uses a mock API to fetch available time slots dynamically based on the current month.

### 🔗 Base URL

https://5b94bbb0-4b84-4173-8753-c9b46c84fc76.mock.pstmn.io/appointment_availabilities/available_times

### 🔖 Required Query Params

- `start_date_time`: Start of the visible month in **UTC**
- `end_date_time`: End of the visible month in **UTC**

### 🧩 Headers

- `x-mock-response-name: Apr2025` → Returns April 2025 data
- `x-mock-response-name: May2025` → Returns May 2025 data
- `x-mock-response-name: Default` → Used for any other month

### 📦 Example Request (April 2025)

```bash
curl \
  'https://5b94bbb0-4b84-4173-8753-c9b46c84fc76.mock.pstmn.io/appointment_availabilities/available_times?start_date_time=2025-04-01T07:00:00&end_date_time=2025-04-30T06:59:59' \
  -H 'x-mock-response-name: Apr2025'
```

---

### 📤 Simulated API Payload Output

After selecting a time and filling out the form, the app displays a JSON payload that would be sent to a backend service:

{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "time": "2025-04-10T14:00:00Z"
}

---

### 📲 Running the App

- Android

./gradlew :androidApp:installDebug

- iOS
	1.	Open iosApp/ in Xcode
	2.	Run on simulator or device

Requires Compose Multiplatform + Kotlin Native configured. Follow JetBrains’ Compose Multiplatform iOS setup if needed.

---

### 🔧 Key Dependencies

// Compose Multiplatform
implementation(compose.runtime)
implementation(compose.foundation)
implementation(compose.material)

// Ktor Client
implementation("io.ktor:ktor-client-core")
implementation("io.ktor:ktor-client-darwin") // for iOS
implementation("io.ktor:ktor-client-okhttp") // for Android
implementation("io.ktor:ktor-client-logging")

// Serialization
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")

---

### 📄 License

MIT License © 2025 Matheus Mesquita

---

### 🙌 Acknowledgements

Thanks to the OfferingTree team for the challenge prompt and inspiration!

---

Let me know if you want a matching `build.gradle.kts` file next, or need help scaffolding the shared module code!