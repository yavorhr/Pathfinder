function toggleButtonState() {
    const fileInput = document.getElementById("picture");
    const uploadButton = document.getElementById("upload-btn");

    uploadButton.disabled = !fileInput.value;
}

toggleButtonState();