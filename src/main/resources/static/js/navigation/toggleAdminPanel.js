document.addEventListener("DOMContentLoaded", function () {
    const adminBtn = document.getElementById("navbarDropdown");
    const popover = document.getElementById("adminPopover");
    const logoutButton = document.querySelector(".logout-item");

    adminBtn.addEventListener("click", function (e) {
        e.preventDefault();

        if (window.innerWidth > 768) {
            // Desktop / tablet floating popover
            popover.classList.toggle("show");
            const rect = adminBtn.getBoundingClientRect();
            popover.style.top = rect.bottom + window.scrollY + "px";
            popover.style.left = rect.left + window.scrollX + "px";

            const popoverRect = popover.getBoundingClientRect();
            if (popoverRect.right > window.innerWidth) {
                popover.style.left = window.innerWidth - popoverRect.width - 10 + "px";
            }
        } else {
            popover.classList.toggle("show");
        }
    });

    // Close popover when clicking outside
    document.addEventListener("click", function (e) {
        if (!adminBtn.contains(e.target) && !popover.contains(e.target)) {
            popover.classList.remove("show");
            logoutButton.style.marginTop = "0";

        }
    });
});
