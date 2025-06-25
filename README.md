
<h1 align="center">🚗 Smart Parking System with Analytics</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-Swing-orange?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Data%20Structures-Queue%2C%20Heap%2C%20LinkedList-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/GUI-Interactive-green?style=for-the-badge" />
</p>

<p align="center">An intelligent, Java-based desktop application that manages parking slots with VIP prioritization, real-time GUI, persistent logging, and analytics.</p>

---

## 🧠 Project Summary

> “Smart Parking System with Analytics” is a Swing-based desktop application built in Java that efficiently manages 1000 parking slots (100 VIP). It supports real-time vehicle entry/exit, VIP prioritization, data persistence via logs, and analytics such as average stay time and peak hour.

---

## 📁 Repository Structure

```

📦 SmartParkingSystem
┣ 📄 SmartParkingSystem.java     ← Core implementation (All Classes + GUI)
┣ 📄 SPS\_report.docx             ← Design, Complexity & Class Analysis Report
┗ 📄 parking\_logs.txt            ← Runtime-generated log file (auto-created)

````

---

## 🔧 Features

- ✅ Real-time GUI for vehicle entry & exit
- ⭐ VIP prioritization using MaxHeap
- 🔄 CircularQueue for regular slot rotation
- 🧾 LinkedList-based persistent logging
- 📊 Analytics: Average Stay Time & Peak Hour
- 📋 View all parked vehicles (with log history)
- 🧠 DSA-focused structure with performance-optimized methods

---

## 🧩 System Design Overview

| Component         | Purpose                              | Data Structure   |
|------------------|--------------------------------------|------------------|
| Regular Slots     | FIFO slot management                 | `CircularQueue`  |
| VIP Slots         | Priority handling (lowest slot ID)   | `Min-Heap`       |
| Entry/Exit Logs   | Persistent tracking of records       | `LinkedList`     |
| Lookup Mechanism  | Map vehicle to assigned slot         | `HashMap`        |

🧠 *Log persistence is handled via `parking_logs.txt`.*

---

## 📊 Analytics Engine

| Metric              | Formula                                             | Time Complexity |
|---------------------|------------------------------------------------------|------------------|
| **Average Stay Time** | Total duration / No. of completed vehicles         | O(m)              |
| **Peak Hour**        | Most frequent hour of entry (0-23)                  | O(m + 24)         |

---

## 🧪 Class-Level Complexity

| Class               | Key Function                     | Time Complexity |
|---------------------|----------------------------------|------------------|
| `Slot`              | `occupy`, `free`                 | O(1)             |
| `Vehicle`           | Getters                          | O(1)             |
| `CircularQueue`     | `enqueue`, `dequeue`             | O(1)             |
| `MaxHeap`           | `insert`, `extractMax`           | O(log n)         |
| `EntryExitLog`      | `addEntry`, `addExit`            | O(m)             |
| `AnalyticsEngine`   | `getAverageStayTime`, `getPeakHour` | O(m), O(m + 24) |
| `ParkingManager`    | `checkIn`, `checkOut`            | O(n) worst case  |
| `SmartParkingSystem`| GUI functions                    | O(p), O(m)       |

---

## 📷 GUI Screenshots

<details>
<summary><b>Main Frame (Click to Expand)</b></summary>

![Main Frame](https://via.placeholder.com/700x300.png?text=Main+Frame+GUI+Screenshot)

</details>

<details>
<summary><b>Parked Vehicles Log</b></summary>

![Parked Vehicles](https://via.placeholder.com/700x300.png?text=Parked+Vehicles+Frame)

</details>

---

## 📜 Documentation

📄 **SPS_report.docx** (included in repo)

This document contains:

- 📌 Project Overview
- 🧠 Data Structure Justification
- 🧮 Algorithm & Time Complexity Analysis
- 🧱 Class Responsibilities
- 🖼️ Class Diagram (Placeholder)
- 🧾 GUI Screenshots

---

## 🚀 How to Run

1. Clone this repository:
```bash
git clone https://github.com/muz1058/SmartParkingSystem.git
````

2. Open `SmartParkingSystem.java` in any Java IDE (e.g., IntelliJ, Eclipse)

3. Run the file:

```bash
javac SmartParkingSystem.java
java SmartParkingSystem
```

---

## 👨‍💻 Authors

| Name             | Reg No.       |
| ---------------- | ------------- |
| Areef ul Rehman  | L1F23BSSE0389 |
| Talha Atif       | L1F23BSSE0065 |
| Muzaffar Ali     | L1F23BSSE0395 |
| Abdullah Maqbool | L1F23BSSE0391 |

📘 *Section: P4*
🧑‍🏫 *Supervised By: Ma’am Javaria Tanveer*

---

## 💡 Future Enhancements

* 📲 Mobile App version (Android)
* 📡 Real-time sensor integration (IoT)
* ☁️ Cloud-based analytics dashboard
* 🔐 Admin authentication system

---

## 📑 License

This project is for academic purposes under course **Data Structures and Algorithms**.
Unauthorized commercial use is discouraged.

---

> *“Efficiency is doing better what is already being done.” — Peter Drucker*

```

