# 🧭 Pathfinder  
*A Spring Boot & HTML/CSS application for sharing and exploring travel route experiences.*

---

## 📖 About the Project
Pathfinder is a full-stack application that allows users to **create, share, and explore travel routes** with detailed metadata.  
It includes a personalized profile system, social features like comments, and an **Admin Panel** for advanced user and content management.  

> ⚡ Built with **Java + Spring Boot**, **Thymeleaf**, and modern web integrations (Cloudinary, Weather API, Chart.js).  

---

## ✨ Features

### 👤 General Users
- 🔑 **Authentication** with Spring Security (Login/Logout)
- 🛤️ **Routes**
  - Add new routes with **categories** (Pedestrian, Bicycle, Motorcycle, Car)  
  - Attach **GPX coordinates**, YouTube video, and description  
  - Upload an **image gallery** (via Cloudinary API)  
  - Delete route (author or admin only)  
  - Browse all routes or filter by category  
  - Homepage highlights **Most Commented Route**  
- 🌦️ **Weather Integration**  
  - Current weather in Sofia & Plovdiv (via OpenWeather API)  
- 👤 **Profile**  
  - Customize profile fields (age, name, social links)  
  - Change or reset profile picture (Cloudinary API)  
  - Leveling system based on created routes  

### 🛠️ Admin Panel
- 📊 **Most viewed pages statistics** with reset functionality  
- 👥 **User Management**  
  - Delete, disable/enable, lock/unlock accounts  
  - Send emails to users  
  - Update user roles  
  - Search + pagination support  
- 🧪 Project includes **95%+ unit & integration test coverage**  

---

## 🖼️ Screenshots

> Screenshots will be added soon.  
*(e.g. Home Page, Route Details, Profile Page, Admin Panel, etc.)*  

---

## 🛠️ Tech Stack

- **Backend:** ![Java](https://img.shields.io/badge/Java-17-blue?logo=java&logoColor=white), Spring Boot, Spring Security  
- **Frontend:** HTML, CSS, Thymeleaf, Bootstrap, JavaScript  
- **Database:** MySQL  
- **Cloud Storage:** Cloudinary  
- **APIs:**  
  - OpenWeatherMap (Weather data)  
  - Chart.js (Statistics visualization)  
- **DevOps:** Docker (planned)  
- **Testing:** JUnit, Spring Boot Test (95% coverage)  

---

## 🚀 Getting Started

### Clone & Run (manual)
```bash
# Clone the repository
git clone https://github.com/your-username/pathfinder.git
cd pathfinder

# Configure application.properties with your MySQL + Cloudinary credentials

# Build & run
./mvnw clean install
./mvnw spring-boot:run
```

### 🐳 Run with Docker (coming soon)
A ready-to-use docker-compose.yml will be provided for easy setup.

---

## 📂 Project Structure

pathfinder/
 ├── src/main/java/...       # Spring Boot backend
 ├── src/main/resources/...  # Static resources, templates
 ├── src/test/java/...       # Unit & integration tests
 ├── pom.xml                 # Maven config
 └── README.md               # This file

---

 ## 🗺️ Roadmap

- Add audit logging for admin actions
- Enhance email templates
- Docker setup with docker-compose

---

## 📜 License

This project is currently unlicensed.
For a student/portfolio project, a license isn’t mandatory — but if you want others to use or build on your code, MIT is the simplest choice.

---

📬 Contact

Created by Yavor 👨‍💻

- LinkedIn
- GitHub
