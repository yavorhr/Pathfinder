function toggleButtonState() {
    const fileInput = document.getElementById("picture");
    const uploadButton = document.getElementById("upload-btn");

    if (fileInput) {
        uploadButton.disabled = !fileInput.value;
    }
}

toggleButtonState();