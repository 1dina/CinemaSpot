# CinemaSpot
# 🎬 Cinema Spot

Cinema Spot is a modern Android application built to enhance the movie-watching experience. Developed by a passionate team, the app allows users to explore trending films, manage their watchlist, and stay updated with detailed movie insights — all within an intuitive, Compose-powered UI.

---

## Table of Contents

1. [Overview](#overview)  
2. [Key Features](#key-features)  
3. [Architecture](#architecture)  
4. [Technology Stack](#technology-stack)  
5. [Installation Guide](#installation-guide)  
6. [Usage Instructions](#usage-instructions)  
7. [Screenshots](#screenshots)  
8. [Video Demonstration](#video-demonstration)  
9. [Contact Information](#contact-information)

---

## Overview

Cinema Spot provides a clean and engaging interface for discovering movies, searching by name, and saving favorites to a personal watchlist. It connects to a remote movie API and follows modern Android development practices for a smooth user experience.

---

## Key Features

### 🔐 Authentication

- Splash screen on launch.
- Login through the app using a valid account.
- New users are directed to the external website to create an account.

### 🏠 Home Screen

- Categorized lists of movies:
  - Upcoming
  - Popular
  - Top Rated
  - Now Playing
- Scrollable and visually rich layout powered by Jetpack Compose.

### 🔍 Search Screen

- Dynamic search by movie name.
- Real-time results with clean UI and loading states.

### 🎞️ Watchlist

- Save movies to your watchlist for quick access.
- Watchlist is persistent and easy to manage.

### 📄 Movie Details

- Overview of selected movie including:
  - Title, type, release date, duration
  - Rating, genres, and crew
  - User comments and feedback

---

## Architecture

Cinema Spot follows the **MVVM (Model-View-ViewModel)** pattern and is structured using **Clean Architecture** principles for scalability and maintainability.

### Core Components

- **Repository Pattern** – Abstracts data sources.
- **ViewModel + State Management** – Lifecycle-aware data handling using `StateFlow`.
- **Coroutines** – Smooth, async operations and data fetching.

---

## Technology Stack

- **Jetpack Compose** – Declarative UI
- **Kotlin** – Primary development language
- **Hilt** – Dependency Injection
- **Retrofit** – REST API consumption
- **Kotlin Coroutines** – Background processing
- **Coil** – Image loading
- **MVVM + Clean Architecture** – Scalable design

---

## Installation Guide

To run the app locally:

1. **Clone the repository**  
   ```bash
   git clone https://github.com/1dina/CinemaSpot
2. **Open in Android Studio**
3. **Sync Gradle and run the project**
4. **Set up your API keys** in the appropriate config file

---

## 📱 Usage Instructions

### 🔐 Login Activity
- Enter your credentials to log in.
- If you don’t have an account, the app will redirect you to the browser to create one.

### 🏠 Home Screen
- Browse trending and upcoming movies categorized by type.

### 🔍 Search
- Search for any movie by its title and view matching results instantly.

### 🎞️ Watchlist
- Save your favorite movies and access them conveniently in one place.

### 📄 Movie Details
- View detailed information including:
  - Rating
  - Duration
  - Genre
  - Cast and crew

---

## 📸 Screenshots

### 🏠 Home Screen
<img src="https://github.com/your-repo/assets/home.png" width="300"/>

### 🔍 Search Screen
<img src="https://github.com/your-repo/assets/search.png" width="300"/>

### 🎞️ Watchlist Screen
<img src="https://github.com/your-repo/assets/watchlist.png" width="300"/>

### 📄 Movie Details Screen
<img src="https://github.com/your-repo/assets/details.png" width="300"/>

---

## 🎥 Video Demonstration

> _(Embed or link to a YouTube or local video demo here)_

👉 [Watch Demo](https://your-demo-link.com)

---

## 📬 Contact Information

**Project Maintainers:**

- [Dina Fadel](https://github.com/1dina)
- [Mohamed Esam](https://github.com/MoEsam2)
