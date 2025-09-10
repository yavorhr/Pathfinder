
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

## Table of Contents

| Section | Items |
|---------|-------|
| 📚 Project Overview | [📖 About the Project](#-about-the-project) • [✨ Features](#-features) • [🖼️ Screenshots](#-screenshots) |
| 🛠️ Technical Details | [🛠️ Tech Stack](#-tech-stack) • [📡 API Endpoints](#-api-endpoints) • [👤🔒 Roles, Permissions & Security](#-roles-permissions--security) • [🧪 Testing & Coverage](#-testing--coverage)|
| 🏗️ Project Structure | [📂 Folder Structure](#-folder-structure) • [🗄️ Database Design](#-database-design) |
| 🚀 Getting Started | [🔧 Configuration](#-configuration) • [Clone & Run (manual)](#clone--run-manual) • [🐳 Run with Docker (coming-soon)](#-run-with-docker-coming-soon) |
| 🗺️ Roadmap & License | [🗺️ Roadmap](#-roadmap) • [📜 License](#-license) |
| 📬 Contact / Support | [📬 Contact](#-contact) |

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
  - Attach **GPX coordinates** (render with **Leaflet.js** library)
  - **YouTube video embedded**
  - Upload an **image gallery** (via Cloudinary API)  
  - Delete route (author or admin only)  
  - Browse all routes or filter by category  
  - Homepage highlights **Most Commented Route**  
- 🌦️ **Weather API Integration**  
  - Current weather in Sofia & Plovdiv (via **OpenWeather API**)  
- 👤 **Profile**  
  - Customize profile fields (age, name, social links)  
  - Change or reset profile picture (**Cloudinary API**)  
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
- **90%+ coverage** with **JUnit 5** and **Spring MockMVC**.  
- Unit tests for services, repositories, and utility classes.  
- Integration tests for controllers and security configuration.  

---

## 🖼️ Screenshots

### Admin control panel

<table>
  <tr>
    <td valign="top" width="50%">
<a href="https://github.com/user-attachments/assets/9e06ce71-c6df-4609-9e71-3dd11491e5d1">
  <img src="https://github.com/user-attachments/assets/9e06ce71-c6df-4609-9e71-3dd11491e5d1" style="width:500px; height:auto;"/>
</a>
    </td>
    <td valign="top" style="padding-left:20px;">
      <p>This is the user management page. It provides the following features:</p>
        <ul>
          <li>Remove user from the database</li>
          <li>Disable/enable user access</li>
          <li>Lock/unlock user account</li>
          <li>Send emails to users</li>
          <li>Update user roles</li>
           <li>Search + pagination support</li>
        </ul>
    </td>
  </tr>
</table>

---

### Endpoints statistics (admin only)

<table>
  <tr>
    <td valign="top" width="50%">
<a href="https://github.com/user-attachments/assets/731f5f9d-a8a4-439f-a144-bfa5262e6fbd">
  <img src="https://github.com/user-attachments/assets/731f5f9d-a8a4-439f-a144-bfa5262e6fbd" style="width:500px; height:auto;"/>
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

### Home Page

<table>
  <tr>
    <td valign="top" width="50%">
      <!-- Images column -->
      <a href="https://github.com/user-attachments/assets/4d39db9a-5cc5-479e-87b6-da2bab844ef5">
        <img src="https://github.com/user-attachments/assets/4d39db9a-5cc5-479e-87b6-da2bab844ef5" style="width:500px; height:auto;"/>
      </a>
      <br/>
      <a href="https://github.com/user-attachments/assets/53e604da-3557-4e7a-942c-1a7219ba9da1">
        <img src="https://github.com/user-attachments/assets/53e604da-3557-4e7a-942c-1a7219ba9da1" style="width:500px; height:auto;"/>
      </a>
    </td>
    <td valign="top" style="padding-left:20px;">
      <p>The homepage is the start point of the application. It includes the following features:</p>
        <ul>
          <li>Quick link to all users routes</li>
          <li>Link tabs to routes, sorted by category (car, pedestrian, etc.)</li>
          <li>Live weather forecast</li>
          <li>Link to the most commented route</li>
          <li>Gallery with all recent route images</li>
        </ul>
    </td>
  </tr>
</table>

---

### Register
<table>
  <tr>
    <td valign="top" width="50%">
      <a href="https://github.com/user-attachments/assets/e10280a0-acb1-46ee-b3b9-dec7daec94dc">
        <img src="https://github.com/user-attachments/assets/e10280a0-acb1-46ee-b3b9-dec7daec94dc" style="width:500px; height:auto;"/>
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
        <img src="https://github.com/user-attachments/assets/7074df9b-1109-4700-b704-561915fc32e8" style="width:500px; height:auto;"/>
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
  <img src="https://github.com/user-attachments/assets/5b2bc253-725e-48c4-b380-f24f05ad6b7a" style="width:500px; height:auto;"/>
</a>
    <a href="https://github.com/user-attachments/assets/d18fdc17-e97c-40fd-8e24-1f34505e5622">
  <img src="https://github.com/user-attachments/assets/d18fdc17-e97c-40fd-8e24-1f34505e5622" style="width:500px; height:auto;"/>
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

### Route Details

<table>
  <tr>
   <td valign="top" width="50%">
<a href="https://github.com/user-attachments/assets/3f5ea2e2-381c-429a-baad-b44929908193">
  <img src="https://github.com/user-attachments/assets/3f5ea2e2-381c-429a-baad-b44929908193"  style="width:500px; height:auto;"/>
</a>
 <br/>
<a href="https://github.com/user-attachments/assets/96fbac8f-f164-47ba-8ca2-135a12ca505c">
  <img src="https://github.com/user-attachments/assets/96fbac8f-f164-47ba-8ca2-135a12ca505c"  style="width:500px; height:auto;"/>
</a>
    </td>
    <td valign="top" style="padding-left:20px;">
      <ul>
    <p>This is one of the main pages of the application, where specific route with its details are displayed.</p>
    <li>Main information section: Shows route details such as <b>name</b>, <b>categories</b>, <b>total distance</b>, and <b>difficulty level</b>.</li>
    <li>Author/Admin actions: If the current user is the <b>author</b> or <b>admin</b>, additional buttons appear for <b>deleting the route</b> and <b>adding images</b> to the gallery. Both functionalities are secured with <code>Spring security</code> in the backend.</li>
    <li>Dynamic GPX visualization: Displays route paths using <b>Leaflet.js</b>.</li>
    <li>YouTube integration: Optionally shows an embedded YouTube video for the route.</li>
    <li>Gallery images: All route-related images are displayed with a <b>modal preview</b> using JavaScript to show full-size images.</li>
    <li>Comments section: Users can post comments. Implemented with <b>JavaScript and Fetch API</b> so the page does not reload after posting for a smoother user experience.</li>
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
        <img src="https://github.com/user-attachments/assets/a98e0b03-5b57-40f5-b05a-216e36cd09e8"
              style="width:500px; height:auto;"/>
    </a>
</td>
     <td valign="top" style="padding-left:20px;">
       <p>This is the place where user can add route to the application.
       </p>
   <ul>
    <li><b>Access control:</b> <b>Only authenticated users</b> can access this page (enforced via Spring Security).</li>
    <li><b>Route information</b>: Users can provide a name and description for the route.</li>
    <li><b>GPX coordinates:</b> Users can upload route GPX coordinates, which are visualized later with <b>Leaflet JavaScript library</b>.</li>
    <li><b>YouTube integration:</b> Users can optionally embed a video to visualize the route.</li>
    <li><b>Categories selection:</b> Users can select one or multiple categories (e.g., Pedestrian, Bicycle, Car, Motorcycle).</li>
    <li><b>Difficulty level:</b> Users can choose the route’s difficulty (e.g., Beginner, Intermediate, Advanced).</li>
    <li><b>Input validation:</b> All fields are validated on the frontend and backend, including required fields, GPX data correctness, and valid YouTube URL format.</li>
     <a href="https://github.com/user-attachments/assets/c2c4045d-65c6-4a44-adeb-4146dec6b210">
        <br/>
            <img src="https://github.com/user-attachments/assets/c2c4045d-65c6-4a44-adeb-4146dec6b210" style="height:120px; width:auto; margin-top:6px; border:1px solid #ccc;"/>
          </a>
    <li>User feedback: <b>Invalid inputs</b> are displayed directly on the form to guide the user.</li>
    <li><b>Data persistence:</b> Submitted routes are saved in the <b>MySQL database</b>, linked to the submitting user.</li>
</ul>
    </td>
  </tr>
</table>

---

### Routes by category (Car, Pedestrian, Bike or Bicycle)

<table>
  <tr>
   <td valign="top" width="50%">
<a href="https://github.com/user-attachments/assets/08629c12-6e1c-4889-8919-0b9e3a973379">
  <img src="https://github.com/user-attachments/assets/08629c12-6e1c-4889-8919-0b9e3a973379" style="width:500px; height:auto;"/>
</a>
    </td>
    <td valign="top" style="padding-left:20px;">
    <ul>
  <p>This page represents category-based browsing.</p>
  <li>Users can explore routes by category (Car, Pedestrian, Bicycle, Motorcycle).</li>
  <li><b>Dynamic quotes</b>: Each category displays a random motivational quote, generated from the backend.</li>
  <li><b>Interactive UX</b>: Clicking on a route opens a modal with a link to the route details page, ensuring smooth navigation without page reload.</li>
  <li><b>Seamless integration</b>: Combines backend logic (random quote generation) with frontend JavaScript (modal functionality).</li>
</ul>
    </td>
  </tr>
</table>

---

### User profile

<table>
  <tr>
   <td valign="top" width="50%">
<a href="https://github.com/user-attachments/assets/9fa7a9bb-594b-4689-8bea-d3bf11b7a06c">
  <img src="https://github.com/user-attachments/assets/9fa7a9bb-594b-4689-8bea-d3bf11b7a06c" style="width:500px; height:auto;"/>
</a>
    </td>
    <td valign="top" style="padding-left:20px;">
   <ul>
  <p>This user's profile page brings the following features:</p>
  <li><b>Profile image</b>: Displays the user’s profile picture (or placeholder if none).</li>
  <li><b>Image upload</b>: Users can change their profile image via a button.  
      – Implemented with <b>Fetch API</b> and <b>Cloudinary</b> for seamless uploads without page reload. Image persists in the database.</li>
  <li><b>User level</b>: Displays user’s level, calculated dynamically based on the number of routes they have added.</li>
  <li><b>Personal information</b>: Shows user details (name, email, etc.) and connected <b>social accounts</b>.</li>
  <li><b>Enhanced UX</b>: Clean CSS styling for a visually appealing and user-friendly profile page.</li>
</ul>
    </td>
  </tr>
</table>

---

### User profile in "Edit mode"

<table>
  <tr>
   <td valign="top" width="50%">
<a href="https://github.com/user-attachments/assets/42919274-af59-4e9f-a37e-b08429455102">
  <img src="https://github.com/user-attachments/assets/42919274-af59-4e9f-a37e-b08429455102" style="width:500px; height:auto;"/>
</a></td>
    <td valign="top" style="padding-left:20px;">
      <p>User is able to edit dynamically own profile as follows:</p>
 <ul>
  <li>Clicking <b>Edit</b> opens a <b>modal window</b> without page reload, keeping the experience smooth and fast.</li>
  <li><b>Editable fields:</b> First Name, Last Name, About Me, and Social Accounts.</li>
  <li><b>Username:</b> Always visible but <i>non-editable</i> to preserve account identity.</li>
  <li><b>Actions:</b>
    <ul>
      <li><b>Save Changes</b> – Updates the user data asynchronously (via Fetch API) without refreshing the page.</li>
      <li><b>Reset</b> – Cancels edits and restores the profile to its last saved state.</li>
    </ul>
  </li>
  <li>All interactions are <b>asynchronous</b> for better UX, with immediate feedback on changes.</li>
</ul>
    </td>
  </tr>
</table>

### About page

<table>
  <tr>
   <td valign="top" width="50%">
<a href="https://github.com/user-attachments/assets/c3903367-3c7f-4624-ba8f-984cb9b1608e">
  <img src="https://github.com/user-attachments/assets/c3903367-3c7f-4624-ba8f-984cb9b1608e" style="width:500px; height:auto;"/>
</a>
    </td>
    <td valign="top" style="padding-left:20px;">
 <ul>
  <li>Provides <b>meta information</b> about the project and its purpose.</li>
  <li>Summarizes the <b>main features</b> of the application (routes, profiles, comments, admin tools, etc.).</li>
  <li>Includes a clear overview of the <b>tech stack</b> used (Spring Boot, Thymeleaf, JavaScript, MySQL, Redis, Cloudinary, Leaflet, etc.).</li>
  <li>Presents a <b>roadmap</b> with expected enhancements and future improvements.</li>
  <li>Styled with <b>custom CSS</b> to ensure readability and a pleasant user experience.</li>
</ul>
    </td>
  </tr>
</table>

---

## 🛠️ Tech Stack

| Category | Technologies |
|----------|--------------|
| 🔙 Backend | <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/intellij/intellij-original.svg" width="40" height="40"/> IntelliJ IDEA</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/> Java</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="40" height="40"/> Spring Boot</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="40" height="40"/> Spring Security</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/junit/junit-plain.svg" width="40" height="40"/> JUnit</span> |
| 🎨 Frontend | <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg" width="40" height="40"/> HTML5</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg" width="40" height="40"/> CSS3</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" width="40" height="40"/> JavaScript</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/bootstrap/bootstrap-original.svg" width="40" height="40"/> Bootstrap</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/thymeleaf/thymeleaf-original.svg" width="40" height="40"/> Thymeleaf</span> |
| 🗄️ Database | <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" width="40" height="40"/> MySQL</span> |
| ⚙️ DevOps / Tools | <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" width="40" height="40"/> Docker</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" width="40" height="40"/> Git</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg" width="40" height="40"/> GitHub</span> |
| 🌐 APIs / External Services | <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://img.icons8.com/fluency/48/api.png" width="40" height="40"/> REST API</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://img.icons8.com/color/48/cloud.png" width="40" height="40"/> Cloudinary</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://img.icons8.com/color/48/combo-chart--v1.png" width="40" height="40"/> Chart.js</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://img.icons8.com/fluency/48/clouds.png" width="40" height="40"/> OpenWeather API</span> &nbsp; <span style="background:#f0f0f0; padding:4px; border-radius:6px;"><img src="https://img.icons8.com/arcade/64/leaf.png" width="40" height="40"/> Leaflet JS</span> |

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
| POST   | `/routes`       | Create new route               | User        |
| DELETE | `/routes`  | Delete a route (author/admin only) | User/Admin  |

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
| PUT   | `/admin/api/change-user-access/{email}`  | Enable/disable user | Admin  |
| PUT   | `/admin/api/change-user-lock-status/{email}`| Lock/unlock user | Admin  |
| PATCH   | `/admin/api/update-roles`| Change user roles  | Admin  |
| DELETE   | `/admin/api/remove-user/{email}`| Remove user  | Admin  |

### 🏠 Home page
| Method | Endpoint                  | Description             | Access |
|--------|---------------------------|-------------------------|--------|
| GET    | `/`     | Get home page   | Public  |
| GET    | `/about`     | Get about page   | Public  |

### 🖼️ Cloudinary API
| Method | Endpoint                  | Description             | Access |
|--------|---------------------------|-------------------------|--------|
| POST    | `/pictures/add`     | User add picture to route | User |
| DELETE    | `/pictures/delete`     | User deletes picture from route | User |
| POST    | `/api/profile/image-upload`     | User uploads new profile image | User |
| DELETE    | `/api/profile/image-delete`     | User deletes old profile image  | User |

### 💬 Comments
| Method | Endpoint                  | Description             | Access |
|--------|---------------------------|-------------------------|--------|
| GET    | `/api/{routeId}/comments`     | Get all route comments   | User  |
| POST    | `/api/{routeId}/add-comment`     | Add comment to route   | User  |

---

### 👤🔒 Roles, Permissions & Security

## 🔐 Security Features  

- **Spring Security integration** – full authentication & authorization layer.  
- **Role-based access control (RBAC)** – Roles include `Guest`, `User`, `Author`, and `Admin` (see [Roles & Permissions](#-roles--permissions)).  
- **Account lockout policy** –  
  - 5 failed login attempts → account locked for 15 minutes.  
  - 3 lockouts → account automatically disabled (requires Admin reactivation).  
- **Secure registration flow** –  
  - New users are *disabled* by default until Admin approval.  
  - Admin receives a notification event and can approve/deny.  
  - Email notifications supported for unapproved users.  
- **Password handling** – all passwords are stored using strong **PasswordEncoder hashing**.  
- **CSRF protection** – enabled for all state-changing requests.  
- **Input validation** – both frontend & backend validation for forms (XSS / SQL Injection prevention).  
- **Authorization annotations** – critical actions like deleting routes or managing users are protected with `@PreAuthorize` checks.  
- **HTTPS-support** – the application can be deployed securely with SSL certificates to ensure encrypted communication between clients and the server.
- **Session management** – invalid sessions are prevented, and concurrent logins can be restricted.  
- **Admin-only tools** – sensitive features (user management, endpoint statistics, role updates) are restricted strictly to Admins.  

##👤 Roles & Permissions

| Feature / Permission       | Guest | User | Author | Admin |
|----------------------------|:-----:|:----:|:------:|:-----:|
| View Home & About page     | ✅    | ✅   | ✅     | ✅    |
| Register                   | ✅    | ❌   | ❌     | ❌    |
| View all routes            | ❌    | ✅   | ✅     | ✅    |
| Create new routes          | ❌    | ✅   | ✅     | ✅    |
| Comment on routes          | ❌    | ✅   | ✅     | ✅    |
| Edit own profile           | ❌    | ✅   | ✅     | ✅    |
| Upload profile image       | ❌    | ✅   | ✅     | ✅    |
| Edit own routes            | ❌    | ❌   | ✅     | ✅    |
| Delete own routes          | ❌    | ❌   | ✅     | ✅    |
| Upload images to own routes| ❌    | ❌   | ✅     | ✅    |
| Manage users (CRUD, roles) | ❌    | ❌   | ❌     | ✅    |
| Access Admin Panel         | ❌    | ❌   | ❌     | ✅    |
| Access Endpoint Stats      | ❌    | ❌   | ❌     | ✅    |
| Approve new registrations  | ❌    | ❌   | ❌     | ✅    |
| Send email notifications   | ❌    | ❌   | ❌     | ✅    |

---

### 🧪 Testing & Coverage

#### ✅ Overview
- **90%+ coverage** achieved with **JUnit 5** and **Spring MockMVC**.  
- Unit tests for services, repositories, and utility classes.  
- Integration tests for controllers and security configuration.  
- Coverage reports generated with **JaCoCo** (`mvn clean test`).

#### ⚡ Running Tests:
Open a terminal in the project root and run:
  ```bash
  ./mvnw clean test
  ```

📊 Coverage Report

- After running tests, the full coverage report is available at:

```bash
target/site/jacoco/index.html
```

📈 Coverage by Package

<details> <summary>Click to expand coverage table</summary>

| Package | Coverage | Coverage Bar |
|---------|-------------|--------------|
| **Total** | 90% ✅ | ██████████ |
| com.example.pathfinder.model.view | 83% 🟡 | █████████ |
| com.example.pathfinder.config | 80% 🟡 | ████████ |
| com.example.pathfinder.util.cloudinary | 28% ⚠️ | ███ |
| com.example.pathfinder.service.schedulers | 34% ⚠️ | ████ |
| com.example.pathfinder.service.impl | 96% ✅ | ██████████ |
| com.example.pathfinder.model.entity | 93% ✅ | ██████████ |
| com.example.pathfinder.web.exception | 21% ⚠️ | ██ |
| com.example.pathfinder.model.service | 93% ✅ | ██████████ |
| com.example.pathfinder.validation.register | 82% 🟡 | █████████ |
| com.example.pathfinder.model.binding | 98% ✅ | ██████████ |
| com.example.pathfinder.service.events | 83% 🟡 | █████████ |
| com.example.pathfinder.validation | 82% 🟡 | █████████ |
| com.example.pathfinder | 37% ⚠️ | ████ |
| com.example.pathfinder.web | 99% ✅ | ██████████ |
| com.example.pathfinder.web.interceptor | 94% ✅ | ██████████ |
| com.example.pathfinder.service.impl.principal | 99% ✅ | ██████████ |
| com.example.pathfinder.model.entity.enums | 100% ✅ | ██████████ |
| com.example.pathfinder.model.common | 100% ✅ | ██████████ |
| com.example.pathfinder.validation.route | 100% ✅ | ██████████ |
</details>

🖼️ Snapshot

   <a href="https://github.com/user-attachments/assets/109b1ad5-8416-4924-92da-38c04830d589">
            <img src="https://github.com/user-attachments/assets/109b1ad5-8416-4924-92da-38c04830d589" style="height:120px; width:auto; margin-top:6px; border:1px solid #ccc;"/>
          </a>


---

## 🚀 Getting Started

### 🔧 Configuration (`application.properties`)

Before running the application, update `src/main/resources/application.properties` with your own credentials:

```properties
# ===============================
# = DATABASE CONFIGURATION
# ===============================
spring.datasource.url=jdbc:mysql://localhost:3306/pathfinder?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# ===============================
# = CLOUDINARY CONFIGURATION
# ===============================
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret

# ===============================
# = MAIL CONFIGURATION (Optional)
# ===============================
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your_email@example.com
spring.mail.password=your_email_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

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

# Project Structure

## 📂 Folder Structure

<details>
<summary>📜 Show Full Project Tree</summary>

```bash
src/
├── main/
│   ├── java/com/example/pathfinder/
│   │   ├── config/                 # Application configuration (security, beans, handlers, web config, etc.)
│   │   ├── model/
│   │   │   ├── binding/            # DTOs for request binding/validation
│   │   │   ├── common/             # Shared enums/constants
│   │   │   ├── entity/             # JPA entities mapped to DB tables
│   │   │   ├── service/            # Service interfaces
│   │   │   └── view/               # View models for responses
│   │   ├── repository/             # Spring Data JPA repositories
│   │   ├── service/
│   │   │   ├── events/             # Event handlers/listeners
│   │   │   ├── impl/               # Service implementations
│   │   │   └── schedulers/         # Scheduled tasks
│   │   ├── util/                   # Utility classes
│   │   ├── validation/             # Custom validation logic
│   │   └── web/                    # Web controllers (REST + MVC)
│   │
│   │── PathfinderApplication.java   # Spring Boot application entry point
│   │
│   └── resources/
│       ├── META-INF/               # Persistence/ORM configs
│       ├── static/                 # Static assets (CSS, JS, images)
│       ├── templates/              # Thymeleaf templates
│       ├── application.properties  # Spring Boot config
│       └── data.sql                # Initial DB seed data
│
└── test/
    ├── java/com/example/pathfinder/
    │   └── PathfinderApplicationTests.java   # Base test class
    └── resources/                            # Test resources

```
</details>

<details> <summary>🌐 Presentation Layer</summary> <table> <tr><th>Project Tree</th><th>Description</th></tr> <tr><td>&nbsp;&nbsp;└── <code>web/</code></td><td>Web layer (controllers, REST endpoints, MVC handlers)</td></tr> <tr><td><code>src/main/resources/templates/</code></td><td>Thymeleaf templates for server-side rendering</td></tr> <tr><td><code>src/main/resources/static/</code></td><td>Static assets (CSS, JS, images)</td></tr> </table> </details>
<details> <summary>⚙️ Configuration Layer</summary> <table> <tr><th>Project Tree</th><th>Description</th></tr> <tr><td>&nbsp;&nbsp;└── <code>config/</code></td><td>Application configuration (security, beans, handlers, web config, etc.)</td></tr> <tr><td><code>PathfinderApplication</code></td><td>Spring Boot application entry point</td></tr> <tr><td><code>src/main/resources/application.properties</code></td><td>Spring Boot application configuration</td></tr> <tr><td><code>src/main/resources/data.sql</code></td><td>Initial database data seeding</td></tr> <tr><td><code>src/main/resources/META-INF/</code></td><td>Persistence and ORM configs (if needed)</td></tr> </table> </details>
<details> <summary>💼 Business Layer</summary> <table> <tr><th>Project Tree</th><th>Description</th></tr> <tr><td>&nbsp;&nbsp;└── <code>model/service/</code></td><td>Service interfaces defining business logic</td></tr> <tr><td>&nbsp;&nbsp;└── <code>service/</code></td><td>Service implementations and supporting logic</td></tr> <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;├── <code>events/</code></td><td>Application event handlers/listeners</td></tr> <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;├── <code>impl/</code></td><td>Concrete service implementations</td></tr> <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;└── <code>schedulers/</code></td><td>Scheduled tasks (e.g., admin notifications)</td></tr> <tr><td>&nbsp;&nbsp;└── <code>util/</code></td><td>Utility classes/helpers</td></tr> <tr><td>&nbsp;&nbsp;└── <code>validation/</code></td><td>Custom validation annotations and logic</td></tr> </table> </details>
<details> <summary>🗄️ Persistence Layer</summary> <table> <tr><th>Project Tree</th><th>Description</th></tr> <tr><td>&nbsp;&nbsp;└── <code>model/entity/</code></td><td>JPA entities mapped to database tables</td></tr> <tr><td>&nbsp;&nbsp;└── <code>repository/</code></td><td>Spring Data JPA repositories for DB access</td></tr> <tr><td>&nbsp;&nbsp;└── <code>model/binding/</code></td><td>DTOs for request binding/validation</td></tr> <tr><td>&nbsp;&nbsp;└── <code>model/view/</code></td><td>View models used for responses</td></tr> <tr><td>&nbsp;&nbsp;└── <code>model/common/</code></td><td>Shared enums/constants across the project</td></tr> </table> </details>
<details> <summary>🧪 Testing Layer</summary> <table> <tr><th>Project Tree</th><th>Description</th></tr> <tr><td><code>src/test/java/com/example/pathfinder/</code></td><td>Unit & integration tests</td></tr> <tr><td>&nbsp;&nbsp;└── <code>PathfinderApplicationTests</code></td><td>Base test class</td></tr> <tr><td><code>src/test/resources/</code></td><td>Test-specific resources/configuration</td></tr> </table> </details>
---

## 📂 Database Design

<img width="1208" height="1200" alt="pathfinder" src="https://github.com/user-attachments/assets/f5117a00-e7ab-408a-9d0b-a7371de31c61" />

<details>
<summary>Click to expand SQL ERD</summary>

```sql
create table categories
(
	id bigint auto_increment
		primary key,
	description tinytext null,
	name enum('BICYCLE', 'CAR', 'MOTORCYCLE', 'PEDESTRIAN') not null,
	constraint UKt8o6pivur7nn124jehx7cygw5
		unique (name)
);

create table roles
(
	id bigint auto_increment
		primary key,
	role enum('ADMIN', 'MODERATOR', 'USER') not null
);

create table users
(
	id bigint auto_increment
		primary key,
	about_me tinytext null,
	is_account_expired bit null,
	is_account_locked bit null,
	birthday date not null,
	disabled_time datetime(6) null,
	email varchar(255) not null,
	is_enabled bit not null,
	facebook_account varchar(255) null,
	failed_login_attempts int null,
	first_name varchar(255) not null,
	gender enum('FEMALE', 'MALE') not null,
	instagram_account varchar(255) null,
	last_failed_login datetime(6) null,
	last_login_time datetime(6) null,
	last_name varchar(255) not null,
	level enum('ADVANCED', 'BEGINNER', 'INTERMEDIATE') null,
	linked_in_account varchar(255) null,
	lock_time datetime(6) null,
	password varchar(255) not null,
	profile_image_public_id varchar(255) null,
	profile_image_url varchar(255) null,
	registration_date datetime(6) not null,
	times_locked int null,
	username varchar(255) not null,
	constraint UK6dotkott2kjsp8vw4d0m25fb7
		unique (email),
	constraint UKr43af9ap4edm43mmtq01oddj6
		unique (username)
);

create table messages
(
	id bigint auto_increment
		primary key,
	date_time datetime(6) not null,
	text_content tinytext null,
	author_id bigint null,
	recipient_id bigint null,
	constraint FKhdkwfnspwb3s60j27vpg0rpg6
		foreign key (recipient_id) references users (id),
	constraint FKowtlim26svclkatusptbgi7u1
		foreign key (author_id) references users (id)
);

create table routes
(
	id bigint auto_increment
		primary key,
	description mediumtext null,
	distance int not null,
	gpx_coordinates mediumtext null,
	level enum('ADVANCED', 'BEGINNER', 'INTERMEDIATE') not null,
	name varchar(255) not null,
	video_url varchar(255) null,
	author_id bigint null,
	constraint UKgclhuhdkauatimjoqj7kc210i
		unique (name),
	constraint FK718folur1fr8yq7n1rg56us16
		foreign key (author_id) references users (id)
);

create table comments
(
	id bigint auto_increment
		primary key,
	approved bit not null,
	created datetime(6) not null,
	text_content tinytext not null,
	author_id bigint null,
	parent_comment_id bigint null,
	route_id bigint null,
	constraint FK7h839m3lkvhbyv3bcdv7sm4fj
		foreign key (parent_comment_id) references comments (id),
	constraint FKk3vmpmcbl2rk8skwr6u88083p
		foreign key (route_id) references routes (id),
	constraint FKn2na60ukhs76ibtpt9burkm27
		foreign key (author_id) references users (id)
);

create table routes_categories
(
	route_id bigint not null,
	category_id bigint not null,
	primary key (route_id, category_id),
	constraint FKl5w79kvquf55leml6tsjemkj
		foreign key (route_id) references routes (id),
	constraint FKsvm6cu2e9y41d45ulbuayiaw4
		foreign key (category_id) references categories (id)
);

create table routes_pictures
(
	id bigint auto_increment
		primary key,
	public_id varchar(255) null,
	title varchar(255) null,
	url varchar(255) null,
	author_id bigint null,
	route_id bigint null,
	constraint FKfd7d35xgox56flr4lu3i8kj2x
		foreign key (author_id) references users (id),
	constraint FKj7v3h4c77afevntgwpfji2qpu
		foreign key (route_id) references routes (id)
);

create table users_roles
(
	user_id bigint not null,
	role_id bigint not null,
	primary key (user_id, role_id),
	constraint FK2o0jvgh89lemvvo17cbqvdxaa
		foreign key (user_id) references users (id),
	constraint FKj6m8fwv7oqv74fcehir1a9ffy
		foreign key (role_id) references roles (id)
);
```
</details>

The application uses a relational database to manage users, roles, routes, messages, and related content. Key tables and relationships are as follows:

- users – Stores user account information, profile details, status flags (enabled, locked, isAccountExpired etc.), and social media links.
- roles – Defines user roles (admin, moderator, user).
- users_roles – Join table connecting users to their assigned roles (many-to-many relationship).
- categories – Stores route categories (bicycle, car, motorcycle, pedestrian).
- routes – Contains all user-submitted routes, including descriptions, GPX coordinates, difficulty levels, and optional YouTube video links.
- routes_categories – Join table connecting routes to one or more categories (many-to-many).
- routes_pictures – Stores images uploaded for each route, with author and route references.
- comments – User comments for routes, supporting parent-child relationships for threaded discussions.
- messages – Stores private messages between users with timestamps, author, and recipient IDs.

Relationships:

<table> <tr> <th>Entity</th> <th>Related Entity</th> <th>Relationship</th> <th>Description</th> </tr> <tr> <td><code>users</code></td> <td><code>roles</code></td> <td>Many-to-Many</td> <td>Users can have multiple roles via <code>users_roles</code>.</td> </tr> <tr> <td><code>routes</code></td> <td><code>users</code></td> <td>Many-to-One</td> <td>Each route is authored by a single user (<code>author_id</code>).</td> </tr> <tr> <td><code>routes</code></td> <td><code>categories</code></td> <td>Many-to-Many</td> <td>A route can belong to multiple categories via <code>routes_categories</code>.</td> </tr> <tr> <td><code>routes</code></td> <td><code>routes_pictures</code></td> <td>One-to-Many</td> <td>Each route can have multiple pictures.</td> </tr> <tr> <td><code>routes</code></td> <td><code>comments</code></td> <td>One-to-Many</td> <td>Each route can have multiple comments. Supports parent-child threading.</td> </tr> <tr> <td><code>messages</code></td> <td><code>users</code></td> <td>Many-to-One</td> <td>Each message has an <b>author</b> and a <b>recipient</b> (both users).</td> </tr> </table>

---

 ## 🚧 In progress

- [ ] Docker setup with `docker-compose`
- [ ] Add social login (Google, GitHub)
- [ ] Admin to be able to delete users comments
- [ ] To add messages feature between users

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

📬 Contact

Created by Yavor 👨‍💻

- [LinkedIn](https://www.linkedin.com/in/yavorhr)  
- [GitHub](https://github.com/yavorhr)  
- [Website](https://yavorhr.github.io)  

