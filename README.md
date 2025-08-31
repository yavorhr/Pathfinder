<!-- Banner Image -->
<p align="center">
  <img src="https://github.com/user-attachments/assets/a0319615-169e-464f-a82a-14534689fb75" alt="Pathfinder Banner" width="100%">
</p>


# 🧭 Pathfinder  
*A Spring Boot & HTML/CSS application for sharing and exploring travel route experiences.*

---

# 📑 Table of Contents

- [📖 About the Project](#-about-the-project)
- [✨ Features](#-features)
- [🖼️ Screenshots](#-screenshots)
- [🛠️ Tech Stack](#️-tech-stack)
- [📡 API Endpoints](#-api-endpoints)  
- [🚀 Getting Started](#-getting-started)
  - [Clone & Run (manual)](#clone--run-manual)
  - [🐳 Run with Docker (coming-soon)](#-run-with-docker-coming-soon)
- [📂 Project Structure](#-project-structure)
- [🗺️ Roadmap](#️-roadmap)
- [📜 License](#-license)
- [📬 Contact](#-contact)

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

### 🏠 Home Page


![home1](https://github.com/user-attachments/assets/4d39db9a-5cc5-479e-87b6-da2bab844ef5)

![home2](https://github.com/user-attachments/assets/53e604da-3557-4e7a-942c-1a7219ba9da1)


> Screenshots will be added soon.  
*(e.g. Home Page, Route Details, Profile Page, Admin Panel, etc.)*  

---

## 🛠️ Tech Stack

### 🔙 Backend
<p align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40" alt="Java"/> **Java**
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="40" height="40" alt="Spring Boot"/> **Spring Boot**
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="40" height="40" alt="Spring Security"/> **Spring Security**
</p>

### 🎨 Frontend
<p align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg" width="40" height="40" alt="HTML5"/> **HTML5**
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg" width="40" height="40" alt="CSS3"/> **CSS3**
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" width="40" height="40" alt="JavaScript"/> **JavaScript**
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/bootstrap/bootstrap-original.svg" width="40" height="40" alt="Bootstrap"/> **Bootstrap**
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/thymeleaf/thymeleaf-original.svg" width="40" height="40" alt="Thymeleaf"/> **Thymeleaf**
</p>

### 🗄️ Database
<p align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" width="40" height="40" alt="MySQL"/> **MySQL**
</p>

### ⚙️ DevOps / Tools
<p align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" width="40" height="40" alt="Docker"/> **Docker (coming soon 🐳)**
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" width="40" height="40" alt="Git"/> **Git**
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg" width="40" height="40" alt="GitHub"/> **GitHub**
</p>

### 🌐 APIs / External Services
<p align="left">
  <img src="https://img.icons8.com/fluency/48/api.png" width="40" height="40" alt="API Integration"/> **REST API Integration**
  &nbsp;&nbsp;
  <img src="https://img.icons8.com/color/48/cloud.png" width="40" height="40" alt="Cloudinary"/> **Cloudinary API**
  &nbsp;&nbsp;
  <img src="https://img.icons8.com/color/48/combo-chart--v1.png" width="40" height="40" alt="Chart.js"/> **Chart.js**
  &nbsp;&nbsp;
  <img src="https://img.icons8.com/fluency/48/clouds.png" width="40" height="40" alt="OpenWeather API"/> **OpenWeather API**
</p>

---

## 📡 API Endpoints

### 🔑 Authentication
| Method | Endpoint         | Description       | Access  |
|--------|------------------|-------------------|---------|
| GET    | `/users/login`   | Get login page    | Public  |
| POST   | `/users/login`   | User login       | Public  |
| POST   | `/users/register` | User registration | Public  |
| POST   | `/users/logout` | User logout | User  |

### 🛤️ Routes
| Method | Endpoint        | Description                        | Access      |
|--------|-----------------|------------------------------------|-------------|
| GET    | `/routes`       | Get all routes                     | User        |
| GET    | `/routes/{category}`  | Get routes by category       | User        |
| GET    | `/routes/details/{id}`  | Get route by ID            | User        |
| GET    | `/routes/most-commented` | Get the routes with most comments | User |
| POST   | `/routes/add`       | Create new route               | User        |
| DELETE | `/routes/delete`  | Delete a route (author/admin only) | User/Admin  |

### 👤 Profile
| Method | Endpoint             | Description             | Access |
|--------|----------------------|-------------------------|--------|
| GET    | `/users/profile`| Get user profile        | User   |
| PATCH    | `/users/profile/edit`  | Update profile details  | User   |

### ⚙️ Admin
| Method | Endpoint                  | Description             | Access |
|--------|---------------------------|-------------------------|--------|
| GET    | `/admin/notifications`     | Get users  table    | Admin  |
| GET    | `/admin/statistics`     | Get most visited endpoints    | Admin  |
| POST    | `/admin/statistics/reset`     | Reset most visited endpoints    | Admin  |
| PUT   | `/admin//api/change-user-access/{email}`  | Enable/disable user's account | Admin  |
| PUT   | `/admin/api/change-user-lock-status/{email}`| Change user roles  | Admin  |
| PATCH   | `/admin/api/update-roles`| Change user roles  | Admin  |
| DELETE   | `/admin//api/remove-user/{email}`| Change user roles  | Admin  |

### 🏠 Home page
| Method | Endpoint                  | Description             | Access |
|--------|---------------------------|-------------------------|--------|
| GET    | `/`     | Get home page   | Public  |
| GET    | `/about`     | Get about page   | Public  |

### 🖼️ Cloudinary API
| Method | Endpoint                  | Description             | Access |
|--------|---------------------------|-------------------------|--------|
| POST    | `/pictures/add`     | User (author) add picture to route | User |
| DELETE    | `/pictures/delete`     | User (author) deletes picture from route | User |
| POST    | `/api/profile/image-upload`     | User uploads new profile image | User |
| DELETE    | `/api/profile/image-delete`     | User deletes old profile image  | User |

### 💬 Comments
| Method | Endpoint                  | Description             | Access |
|--------|---------------------------|-------------------------|--------|
| GET    | `/api/{routeId}/comments`     | Get all route comments   | User  |
| POST    | `/api/{routeId}/add-comment`     | Add comment to route   | User  |

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

 ## 🚧 In progress

- [ ] Docker setup with `docker-compose`
- [ ] Add social login (Google, GitHub)
- [ ] Admin to be able to delete users comments
- [ ] To add messages feature between users

---

## 📜 License

This project is currently unlicensed.
For a student/portfolio project, a license isn’t mandatory — but if you want others to use or build on your code, MIT is the simplest choice.

---

📬 Contact

Created by Yavor 👨‍💻

- LinkedIn
- GitHub
