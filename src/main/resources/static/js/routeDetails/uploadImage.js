document.addEventListener("DOMContentLoaded", () => {
    const fileInput = document.getElementById("picture-upload");
    const form = document.getElementById("picture-upload-form");

    fileInput.addEventListener("change", () => {
        if (fileInput.files.length > 0) {
            const fileName = fileInput.files[0].name.split(".")[0];
            const titleInput = document.getElementById("title");
            if (titleInput && !titleInput.value) {
                titleInput.value = fileName;
            }
        }
    });

    fileInput.addEventListener("change", () => {
        if (fileInput.files.length > 0) {
            form.submit();
        }
    });

});