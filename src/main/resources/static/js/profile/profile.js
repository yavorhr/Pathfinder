document.addEventListener("DOMContentLoaded", function () {

    // Upload image button
    const addImageWrapper = document.querySelector(".add-image-wrapper");
    const uploadButton = document.querySelector("#upload-button");

    if (window.matchMedia("(max-width: 1024px)").matches) {
        addImageWrapper.addEventListener("click", function () {
            const isVisible = uploadButton.style.opacity === "1";
            uploadButton.style.opacity = isVisible ? "0" : "1";
            uploadButton.style.pointerEvents = isVisible ? "none" : "auto";
        });
    }

    // Social media
    if (window.matchMedia("(max-width: 1024px)").matches) {
        document.querySelectorAll(".social-list li").forEach(function (item) {
            const tooltip = item.querySelector("span");
            if (!tooltip) return;

            item.addEventListener("click", function (e) {
                e.stopPropagation(); // prevent closing immediately
                const isVisible = tooltip.style.opacity === "1";
                document.querySelectorAll(".social-list li span").forEach(span => {
                    span.style.opacity = "0";
                    span.style.visibility = "hidden";
                });
                if (!isVisible) {
                    tooltip.style.opacity = "1";
                    tooltip.style.visibility = "visible";
                }
            });
        });

        document.addEventListener("click", function () {
            document.querySelectorAll(".social-list li span").forEach(span => {
                span.style.opacity = "0";
                span.style.visibility = "hidden";
            });
        });
    }
});
