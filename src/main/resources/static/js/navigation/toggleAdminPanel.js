document.addEventListener("DOMContentLoaded", function () {
    const dropdownToggle = document.querySelector(".dropdown");
    const dropdownMenu = document.querySelector(".dropdown-menu");
    const logoutButton = document.querySelector(".logout-item");
    const menuItems = dropdownMenu ? dropdownMenu.querySelectorAll(".dropdown-item") : [];

    if (dropdownToggle) {
        dropdownToggle.addEventListener("click", function (event) {
            event.preventDefault();

            dropdownMenu.classList.toggle("show");

            // To move below Logout and About buttons
            if (window.innerWidth <= 768) {
                if (dropdownMenu.classList.contains("show")) {
                    const dropdownHeight = dropdownMenu.scrollHeight;
                    logoutButton.style.marginTop = dropdownHeight + "px";
                } else {
                    logoutButton.style.marginTop = "0";
                }
            }
        });
    }

    document.addEventListener("click", function (event) {
        if (dropdownToggle && !dropdownToggle.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.classList.remove("show");
        }
    });

    menuItems.forEach(item => {
        item.addEventListener("click", function (event) {
            dropdownMenu.classList.remove("show");
            event.stopPropagation();
        });
    });

});


