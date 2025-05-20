document.addEventListener("DOMContentLoaded", () => {
    const fileInput = document.getElementById("picture-upload");
    const form = document.getElementById("picture-upload-form");
    const uploadSuccess = document.getElementById("uploadSuccess");
    const titleInput = document.getElementById("title");

    fileInput.addEventListener("change", () => {
        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];

            if (titleInput && !titleInput.value) {
                const fileName = file.name.split(".")[0];
                titleInput.value = fileName;
            }

            setTimeout(() => {
                form.submit();
            }, 200);

            // Show success message
            if (uploadSuccess) {
                uploadSuccess.style.opacity = 1;

                setTimeout(() => {
                    uploadSuccess.style.opacity = 0;
                }, 2000);
            }
        }
    });
});