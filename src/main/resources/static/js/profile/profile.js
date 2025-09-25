document.addEventListener("DOMContentLoaded", function () {

    const addImageWrapper = document.querySelector(".add-image-wrapper");
    const uploadButton = document.querySelector("#upload-button");

    // Add image btn
    addImageWrapper.addEventListener("click", function () {
        const isVisible = uploadButton.style.opacity === "1";
        uploadButton.style.opacity = isVisible ? "0" : "1";
        uploadButton.style.pointerEvents = isVisible ? "none" : "auto";
    });

// Select all social list items
    document.querySelectorAll(".social-list li").forEach(function (item) {
        const tooltip = item.querySelector("span");
        if (!tooltip) return;

        item.addEventListener("click", function (e) {
            e.stopPropagation(); // Prevent document click

            const isVisible = tooltip.style.opacity === "1";

            // Hide all spans
            document.querySelectorAll(".social-list li span").forEach(span => {
                span.style.opacity = "0";
                span.style.visibility = "hidden";
            });

            // Show clicked span if it was hidden
            if (!isVisible) {
                tooltip.style.opacity = "1";
                tooltip.style.visibility = "visible";
            }
        });

        // Clicking the span itself should not hide it
        tooltip.addEventListener("click", function (e) {
            e.stopPropagation(); // Prevent document click from hiding it
        });
    });

// Hide all rest spans
    document.addEventListener("click", function () {
        document.querySelectorAll(".social-list li span").forEach(span => {
            span.style.opacity = "0";
            span.style.visibility = "hidden";
        });
    });
});

