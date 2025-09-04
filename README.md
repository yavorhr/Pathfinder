
<p align="center">
  <img 
    src="https://github.com/user-attachments/assets/a0319615-169e-464f-a82a-14534689fb75" 
    alt="Pathfinder Banner" 
    style="max-width:100%; height:500px; border-radius:12px; box-shadow:0px 4px 12px rgba(0,0,0,0.2);" 
  />
</p>

<h1 align="center">🧭 Pathfinder</h1>

<p align="center">
  <b>A modern web Full-stack application for creating, sharing, and exploring routes</b><br>
  Built with <code>Spring Boot</code>, <code>Thymeleaf, HTML&CSS</code>, and <code>MySQL</code>
</p>

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
Pathfinder is a MVC full-stack application that allows users to **create, share, and explore travel routes** with detailed metadata. 

The application is fully responsive — optimized for desktop, tablet, and smartphones.
It leverages **JavaScript (Fetch API)** to handle many actions dynamically without page reloads, providing a smoother, SPA-like user experience on top of the MVC architecture.

> ⚡ Built with **Java + Spring Boot (MVC)** for the backend, and **Thymeleaf, JavaScript, HTML5, and CSS3** for the frontend visualizations.   
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
- **⚡Dynamic interactions with JavaScript Fetch API**
  - Comment routes, update profile fields, and more — all without refreshing the page

### 🛠️ Admin Panel
- 📊 **Most viewed pages statistics** with reset functionality  
- 👥 **User Management**  
  - Delete, disable/enable, lock/unlock accounts  
  - Send emails to users  
  - Update user roles  
  - Search + pagination support
- **⚡AJAX-style controls for user management and admin actions**
- Lock/unlock, change roles, remove accounts and send notifications dynamically
  
### 🧪 Testing
- **95%+ JUnit & Integration test coverage**  

---

## 🖼️ Screenshots

### Home Page

<table>
  <tr>
    <td valign="top">
      <!-- Images column -->
      <a href="https://github.com/user-attachments/assets/4d39db9a-5cc5-479e-87b6-da2bab844ef5">
        <img src="https://github.com/user-attachments/assets/4d39db9a-5cc5-479e-87b6-da2bab844ef5" style="height:300px; width:auto;"/>
      </a>
      <br/>
      <a href="https://github.com/user-attachments/assets/53e604da-3557-4e7a-942c-1a7219ba9da1">
        <img src="https://github.com/user-attachments/assets/53e604da-3557-4e7a-942c-1a7219ba9da1" style="height:300px; width:auto;"/>
      </a>
    </td>
    <td valign="top" style="padding-left:20px;">
      <!-- Description column -->
      <p>
        The homepage is the start point of the application. It includes the following features:
        <ul>
          <li>Quick link to all users routes</li>
          <li>Link tabs to routes, sorted by category (car, pedestrian, etc.)</li>
          <li>Live weather forecast</li>
          <li>Link to the most commented route</li>
          <li>Gallery with all recent route images</li>
        </ul>
      </p>
    </td>
  </tr>
</table>

### Admin control panel

<table>
  <tr>
    <td valign="top">
<a href="https://github.com/user-attachments/assets/9e06ce71-c6df-4609-9e71-3dd11491e5d1">
  <img src="https://github.com/user-attachments/assets/9e06ce71-c6df-4609-9e71-3dd11491e5d1" style="height:220px; width:auto;"/>
</a>
    </td>
    <td valign="top" style="padding-left:20px;">
      <!-- Description column -->
      <p>
        This is the user managmenet page. It provides the following features:
        <ul>
          <li>Remove user from the database</li>
          <li>Disable/enable user access</li>
          <li>Lock/unlock user account</li>
          <li>Send emails to users</li>
          <li>Update user roles</li>
           <li>Search + pagination support</li>
        </ul>
      </p>
    </td>
  </tr>
</table>

# Endpoints statistics (admin only)

<table>
  <tr>
    <td valign="top">
<a href="https://github.com/user-attachments/assets/731f5f9d-a8a4-439f-a144-bfa5262e6fbd">
  <img src="https://github.com/user-attachments/assets/731f5f9d-a8a4-439f-a144-bfa5262e6fbd" style="height:320px; width:auto;"/>
</a>
    </td>
    <td valign="top" style="padding-left:20px;">
      <p>
       This page provides an overview of the most visited endpoints from the end users.
        <ul>
          <li>The page is accessible only to users with admin role (Spring security)</li>
          <li>Using Chart.js API to visualize the statistics</li>
          <li>Reset functionality to clear the stats when needed</li>
          <li>Using reddis docker image to to persist the data. The data persist even after app shut down.</li>
        </ul>
      </p>
    </td>
  </tr>
</table>

---

### Register
<table>
  <tr>
    <td valign="top" width="50%">
      <a href="https://github.com/user-attachments/assets/e10280a0-acb1-46ee-b3b9-dec7daec94dc">
        <img src="https://github.com/user-attachments/assets/e10280a0-acb1-46ee-b3b9-dec7daec94dc" style="height:320px; width:auto;"/>
      </a>
    </td>
     <td valign="top" style="padding-left:20px;">
      <ul>
        <li>By default, newly registered users are <b>disabled</b>.</li>
        <li>A <b>notification event</b> is created and visible in the Admin Panel.</li>
        <li>Only after Admin approval the user can log in.</li>
        <li>Admin can also send an <b>email notification</b> to unapproved users.</li>
        <li>
          Includes <b>input validation</b>: minimal field length, unique email/username, required options (e.g., gender).  
          <br/>
          <a href="https://github.com/user-attachments/assets/e74f2622-e74c-49b8-9e4a-01dd3c56aa86">
            <img src="https://github.com/user-attachments/assets/e74f2622-e74c-49b8-9e4a-01dd3c56aa86" style="height:120px; width:auto; margin-top:6px; border:1px solid #ccc;"/>
          </a>
        </li>
        <li>After successful registration, users are redirected to <b>Login</b>.</li>
        <li>All user data is stored in <b>MySQL database</b>.</li>
      </ul>
    </td>
  </tr>
</table>

---

### Login

<table>
  <tr>
    <td valign="top" width="50%">
       <a href="https://github.com/user-attachments/assets/7074df9b-1109-4700-b704-561915fc32e8">
        <img src="https://github.com/user-attachments/assets/7074df9b-1109-4700-b704-561915fc32e8" style="height:300px; width:auto;"/>
      </a>
    </td>
     <td valign="top" style="padding-left:20px;">
       <p>
    <ul>
        <li>Implemented with <b>Spring Security filter chain</b>.</li>
        <li>Custom <code>loginSuccessHandler</code> and <code>loginFailureHandler</code>.</li>
        <li>Checks for: <b>Locked</b>, <b>Disabled</b>, or <b>Expired</b> accounts.</li>
        <li>If 5 failed attempts (valid username, wrong password) → account is <b>locked for 15 minutes</b> using Spring Scheduler.</li>
        <li>If account is locked 3 times → becomes <b>disabled</b> (admin-only reactivation).</li>
        <li>Detailed <b>error messages</b> appear on the login screen.</li>
       <br/>
          <a href="https://github.com/user-attachments/assets/b3b4cedf-e048-48b1-acc2-31ea3691e8ee">
            <img src="https://github.com/user-attachments/assets/b3b4cedf-e048-48b1-acc2-31ea3691e8ee" style="height:120px; width:auto; margin-top:6px; border:1px solid #ccc;"/>
          </a>
        <a href="https://github.com/user-attachments/assets/0d556747-b70f-4bb6-a38f-abd57af1178c">
            <img src="https://github.com/user-attachments/assets/0d556747-b70f-4bb6-a38f-abd57af1178c" style="height:120px; width:auto; margin-top:6px; border:1px solid #ccc;"/>
          </a>
      </ul>
       </p>
    </td>
  </tr>
</table>

---

### All routes

<table>
  <tr>
     <td valign="top" width="50%">
    <a href="https://github.com/user-attachments/assets/5b2bc253-725e-48c4-b380-f24f05ad6b7a">
  <img src="https://github.com/user-attachments/assets/5b2bc253-725e-48c4-b380-f24f05ad6b7a" width="400"/>
</a>
    <a href="https://github.com/user-attachments/assets/d18fdc17-e97c-40fd-8e24-1f34505e5622">
  <img src="https://github.com/user-attachments/assets/d18fdc17-e97c-40fd-8e24-1f34505e5622" width="400"/>
</a>
        </td>
    <td valign="top" style="padding-left:20px;">
      <p>
     This page is rendering all user's routes.
          </p>
        <ul>
          <li>The content is loaded with Thymeleaf engine</li>
          <li>Pagination feature is added</li>
              <li>Search support by route name is added</li>
        </ul>
    </td>
  </tr>
</table>

---

### Create route

<table>
  <tr>
    <td valign="top" width="50%">
     <a href="https://github.com/user-attachments/assets/a98e0b03-5b57-40f5-b05a-216e36cd09e8">
  <img src="https://github.com/user-attachments/assets/a98e0b03-5b57-40f5-b05a-216e36cd09e8" width="400"/>
</a>
    </td>
     <td valign="top" style="padding-left:20px;">
       <p>This is the place where user can add route to the application.
       </p>
   <ul>
    <li>Access control: <b>Only authenticated users</b> can access this page (enforced via Spring Security).</li>
    <li><b>Route information</b>: Users can provide a name and description for the route.</li>
    <li>GPX coordinates: Users can draw or upload route paths using the <b>Leaflet JavaScript library</b>.</li>
    <li>YouTube integration: Users can optionally embed a video to visualize the route.</li>
    <li>Categories selection: Users can select one or multiple categories (e.g., Pedestrian, Bicycle, Car, Motorcycle).</li>
    <li>Difficulty level: Users can choose the route’s difficulty (e.g., Beginner, Intermediate, Advanced).</li>
    <li>Input validation: All fields are validated on the frontend and backend, including required fields, GPX data correctness, and valid YouTube URL format.</li>
    <li>User feedback: Invalid inputs are displayed directly on the form to guide the user.</li>
    <li>Data persistence: Submitted routes are saved in the <b>MySQL database</b>, linked to the submitting user.</li>
</ul>

    </td>
  </tr>
</table>

---

### Route Details

<a href="https://github.com/user-attachments/assets/3f5ea2e2-381c-429a-baad-b44929908193">
  <img src="https://github.com/user-attachments/assets/3f5ea2e2-381c-429a-baad-b44929908193" width="400"/>
</a>
<a href="https://github.com/user-attachments/assets/96fbac8f-f164-47ba-8ca2-135a12ca505c">
  <img src="https://github.com/user-attachments/assets/96fbac8f-f164-47ba-8ca2-135a12ca505c" width="400"/>
</a>

---

### Routes by category (Car, Pedestrian, Bike or Bicycle)

<a href="https://github.com/user-attachments/assets/08629c12-6e1c-4889-8919-0b9e3a973379">
  <img src="https://github.com/user-attachments/assets/08629c12-6e1c-4889-8919-0b9e3a973379" width="400"/>
</a>

---

### User profile

<a href="https://github.com/user-attachments/assets/367dedb1-a853-484b-9c55-fe5d91c5e009">
  <img src="https://github.com/user-attachments/assets/367dedb1-a853-484b-9c55-fe5d91c5e009" width="400"/>
</a>

### User profile in "Edit mode"

<a href="https://github.com/user-attachments/assets/38db2fca-ef13-4f13-9646-fe5d91c5e009">
  <img src="https://github.com/user-attachments/assets/38db2fca-ef13-4f13-9646-fe5d91c5e009" width="400"/>
</a>

### About page

<a href="https://github.com/user-attachments/assets/c3903367-3c7f-4624-ba8f-984cb9b1608e">
  <img src="https://github.com/user-attachments/assets/c3903367-3c7f-4624-ba8f-984cb9b1608e" width="400"/>
</a>

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
