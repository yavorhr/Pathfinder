document.addEventListener("DOMContentLoaded", function () {
    const dropdownToggle = document.querySelector(".dropdown");
    const dropdownMenu = document.querySelector(".dropdown-menu");
    const menuItems = dropdownMenu.querySelectorAll(".dropdown-item");

    dropdownToggle.addEventListener("click", function (event) {
        event.preventDefault();

        dropdownMenu.classList.toggle("show");
    });

    document.addEventListener("click", function (event) {
        if (!dropdownToggle.contains(event.target) && !dropdownMenu.contains(event.target)) {
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


