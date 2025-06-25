
````markdown
# 🚗 Smart Parking System with Analytics

A Java-based desktop application that intelligently manages vehicle entry/exit operations in a large-scale parking facility. It features dynamic slot allocation, VIP prioritization, persistent logging, and analytical insights — all wrapped in an interactive Swing GUI.

---

## 📘 Table of Contents

- [🔧 Features](#-features)
- [🧠 System Architecture](#-system-architecture)
- [🧩 Data Structures Used](#-data-structures-used)
- [🏗️ Class Design Overview](#-class-design-overview)
- [📊 Analytics Engine](#-analytics-engine)
- [🖥️ GUI Overview](#-gui-overview)
- [🚀 How to Run](#-how-to-run)
- [📁 Project Structure](#-project-structure)
- [📷 Screenshots](#-screenshots)
- [📚 Contributors](#-contributors)

---

## 🔧 Features

- ✅ Efficient parking slot management using CircularQueue and MaxHeap
- ✅ VIP vs Regular slot distinction
- ✅ Real-time check-in/check-out via GUI
- ✅ Persistent logging to `parking_logs.txt`
- ✅ Analytics: Average stay time & peak hour
- ✅ Search vehicle by license plate
- ✅ Dynamic vehicle listing
- ✅ Intuitive Swing-based user interface

---

## 🧠 System Architecture

```plaintext
                 +---------------------+
                 | SmartParkingSystem  |
                 |  (Main GUI Class)   |
                 +---------------------+
                           |
             +-------------+-------------+
             |                           |
  +-------------------+     +------------------------+
  |  ParkingManager    |<--->|   EntryExitLog         |
  +-------------------+     +------------------------+
             |
    +--------+--------+
    |                 |
+--------+       +-----------+
| MaxHeap|       |CircularQueue|
|  (VIP) |       |  (Regular)  |
+--------+       +-----------+
````

---

## 🧩 Data Structures Used

| Component       | Data Structure    | Purpose                          |
| --------------- | ----------------- | -------------------------------- |
| Regular Slots   | CircularQueue     | FIFO management of regular slots |
| VIP Slots       | MaxHeap           | Prioritized access for VIP users |
| Vehicle Mapping | HashMap           | Fast lookup by license plate     |
| Logs            | LinkedList + File | Persistent entry/exit logging    |

---

## 🏗️ Class Design Overview

### 🔹 Slot

Represents a parking slot.

| Method           | Time Complexity |
| ---------------- | --------------- |
| occupy(), free() | O(1)            |

---

### 🔹 Vehicle

Encapsulates vehicle info like license plate, VIP status, and entry time.

| Method                  | Time Complexity |
| ----------------------- | --------------- |
| getLicensePlate(), etc. | O(1)            |

---

### 🔹 CircularQueue

Handles regular slots.

| Method  | Time Complexity |
| ------- | --------------- |
| enqueue | O(1)            |
| dequeue | O(1)            |

---

### 🔹 MaxHeap

VIP slot manager with prioritized allocation (min slotId first).

| Method     | Time Complexity |
| ---------- | --------------- |
| insert     | O(log n)        |
| extractMax | O(log n)        |
| heapify    | O(log n)        |

---

### 🔹 EntryExitLog

Manages log entries and saves them to a file.

| Method    | Time Complexity |
| --------- | --------------- |
| addEntry  | O(m)            |
| addExit   | O(m)            |
| load/save | O(m)            |

---

### 🔹 AnalyticsEngine

Analyzes logs to extract parking metrics.

| Method             | Time Complexity |
| ------------------ | --------------- |
| getAverageStayTime | O(m)            |
| getPeakHour        | O(m + 24)       |

---

### 🔹 ParkingManager

Core coordinator that connects all components.

| Method               | Time Complexity |
| -------------------- | --------------- |
| checkInVehicle       | O(n)            |
| checkOutVehicle      | O(n)            |
| getAnalytics(), etc. | O(1)            |

---

## 📊 Analytics Engine

* **Average Stay Time Formula:**

  ```math
  Avg. Stay = (Sum of all completed parking durations in minutes) / (Total completed vehicles)
  ```

* **Peak Hour Formula:**

  ```math
  Peak Hour = Hour of day with max entry frequency
  ```

---

## 🖥️ GUI Overview

Built using **Java Swing**, the GUI contains:

* ✅ Vehicle Entry/Exit Panel
* ✅ Parked Vehicles List (JList)
* ✅ Status & Analytics Output (JTextArea)
* ✅ Log Viewer Table (JTable)
* ✅ Buttons: `Check In`, `Check Out`, `Show Analytics`, `Show All Parked Vehicles`

---

## 🚀 How to Run

### Prerequisites

* Java JDK 8 or above
* IDE (e.g., IntelliJ IDEA, Eclipse) or terminal

### Steps

```bash
javac SmartParkingSystem.java
java SmartParkingSystem
```

On launch, the Swing GUI will open for interaction.

---

## 📁 Project Structure

```plaintext
SmartParkingSystem/
├── SmartParkingSystem.java     # Main class (all other classes nested)
├── parking_logs.txt            # Log file (auto-generated)
├── README.md                   # Project documentation
└── SPS_report.docx             # Word-formatted documentation
```

---

## 📷 Screenshots

📌 *Screenshots available in the `SPS_report.docx` file.*

---

## 📚 Contributors

| Name             | Registration No. |
| ---------------- | ---------------- |
| Areef ul Rehman  | L1F23BSSE0389    |
| Talha Atif       | L1F23BSSE0065    |
| Muzaffar Ali     | L1F23BSSE0395    |
| Abdullah Maqbool | L1F23BSSE0391    |

🧑‍🏫 **Supervised By**: Ma’am Javaria Tanveer

---

```

---

Let me know if you'd like this in a `.md` file or Word format. I can also help convert it into a PDF or add diagrams/images.
```
