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

   
});
