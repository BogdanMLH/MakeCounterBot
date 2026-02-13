# 🍕 Restaurant Operations Automation Bot

![Java](https://img.shields.io/badge/Language-Java-ed8b00)
![Telegram API](https://img.shields.io/badge/Integration-Telegram_Bot_API-26a5e4)
![Status](https://img.shields.io/badge/Status-Legacy_(Production_Use)-success)

A specialized Telegram Bot developed to digitize and automate routine counting processes in a high-volume restaurant environment (Pizza Hut).

> **Context:** Before this bot, staff had to manually track products and ingredients on paper, which led to errors and data loss. This tool automated the tracking logic directly in the messenger.

---

## 💼 Business Problem & Solution

* **The Problem:** Manual tracking of products was slow and error-prone during peak hours.
* **The Solution:** A Java-based bot that allows staff to input data via simple commands. The bot calculates totals and generates end-of-shift reports automatically.
* **The Result:** Reduced calculation errors and saved 30 minutes per shift for the manager and workers.
<img width="295.6" height="512" alt="image" src="https://github.com/user-attachments/assets/584ab64d-bb61-4677-9dbc-081ddc962bfa" />


## 🛠️ Technical Implementation

This project was built using **Java** (Standard Edition) and the **Telegram Bots API**.

* **Core Logic:** State machine to handle user inputs (Start -> Count -> Report).
* **Data Handling:** MySQL database on the private computer.
* **Deployment:** Heroku Servers.

## 📂 Project Structure

* `src/main/java` - Bot command logic and event listeners.
* `lib` - Dependencies (TelegramBots API).

---

### 💡 Why this matters?
This project demonstrates my transition from "just working" to **automating work**. Even before switching to professional development, I used code to solve real-world operational inefficiencies.

---

**Author:** Bogdan Malanchenko
