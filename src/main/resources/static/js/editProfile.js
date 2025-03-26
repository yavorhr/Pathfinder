document.addEventListener("DOMContentLoaded", function () {
    const editButton = document.getElementById("edit-button");
    const saveButton = document.getElementById("save-button");
    const resetButton = document.getElementById("reset-button");
    const inputFields = document.querySelectorAll("input, textarea");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    const uploadButton = document.getElementById("upload-button");
    const uploadInput = document.getElementById("upload-input");
    const profileImage = document.getElementById("profile-image");

    uploadButton.addEventListener("click", () => {
        uploadInput.click(); // Open file selection dialog
    });

    uploadInput.addEventListener("change", async (event) => {
        const file = event.target.files[0];

        if (file) {
            const formData = new FormData();
            formData.append("file", file);

            try {
                const response = await fetch("/api/profile/image-upload", {
                    method: "POST",
                    headers: {
                        [csrfHeader]: csrfToken
                    },
                    body: formData,
                });

                if (response.ok) {
                    const data = await response.json();
                    let oldPublicInputElement = document.getElementById("oldProfileImagePublicId");

                    const oldPublicId = oldPublicInputElement.value;

                    oldPublicInputElement.value = data.publicId;

                    profileImage.src = data.url;

                    if (oldPublicId) {
                        await deleteOldProfilePicture(oldPublicId);
                    }

                } else {
                    const error = await response.json();
                    alert(error.error || "Failed to upload profile picture.");
                }
            } catch (error) {
                alert("An error occurred while uploading the picture.");
            }
        }
    });

    function deleteOldProfilePicture(publicId) {
        fetch("/api/profile/image-delete", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify({publicId: publicId}),
        })
            .then(response => response.json())
            .then(data => console.log("Old profile picture deleted successfully."))
            .catch(error => console.error("Error deleting image:", error));
    }

    // Attach event listener to "Edit" button
    editButton.addEventListener("click", function (event) {
        event.preventDefault(); // Prevent form submission
        enableEditMode();
    });

    // Function to enable edit mode
    function enableEditMode() {
        inputFields.forEach(input => {
            const displayElement = document.getElementById(input.id.replace("-input", "-display"));

            // Hide age
            document.getElementById('age-wrapper').classList.add("d-none");

            if (displayElement) {
                displayElement.classList.add("d-none"); // Hide display element
            }
            input.classList.remove("d-none"); // Show input field
        });

        editButton.classList.add("d-none"); // Hide Edit button
        saveButton.classList.remove("d-none"); // Show Save button
        resetButton.classList.remove("d-none"); // Show Reset button
    }

    // Attach event listener to "Save" button
    saveButton.addEventListener("click", function (event) {
        event.preventDefault();
        saveChanges();
    });

    // Function to save changes
    function saveChanges() {
        const updatedData = {
            id: document.getElementById("id-input").value.trim(),
            fullName: document.getElementById("fullName-input").value.trim(),
            username: document.getElementById("username-input").value.trim(),
            email: document.getElementById("email-input").value.trim(),
            age: parseInt(document.getElementById("age-input").value.trim(), 10),
            aboutMe: document.getElementById("description-input").value.trim(),
            facebookAcc: document.getElementById("facebook-input").value.trim(),
            instagramAcc: document.getElementById("instagram-input").value.trim(),
            linkedIn: document.getElementById("linkedin-input").value.trim()
        };

        // Clear previous error messages
        document.querySelectorAll(".error-message").forEach(error => error.textContent = "");

        // Collect data from input fields
        inputFields.forEach(input => {
            const fieldName = input.id.replace("-input", ""); // Extract field name
            updatedData[fieldName] = input.value.trim(); // Add field value to data object

            // Update display elements
            const displayElement = document.getElementById(`${fieldName}-display`);
            if (displayElement) {
                displayElement.textContent = input.value.trim(); // Update display element
            }
        });

        // Send the updated data to the server using a POST request
        fetch("/users/profile/edit", {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken,
            },
            body: JSON.stringify(updatedData),
        })
            .then(response => {
                if (response.ok) {
                    alert("Profile updated successfully!");

                    // Reset to view mode
                    inputFields.forEach(input => {
                        const displayElement = document.getElementById(input.id.replace("-input", "-display"));
                        input.classList.add("d-none"); // Hide input fields
                        if (displayElement) {
                            displayElement.classList.remove("d-none"); // Show display elements
                        }
                    });

                    saveButton.classList.add("d-none"); // Hide Save button
                    resetButton.classList.add("d-none"); // Hide Reset button
                    editButton.classList.remove("d-none"); // Show Edit button
                } else {
                    return response.json().then(errors => {
                        console.log(errors)
                        // Render validation errors under the corresponding input fields
                        Object
                            .keys(errors)
                            .forEach(field => {
                                const errorMessage = errors[field];
                                const errorElement = document.getElementById(`${field}-error`);
                                if (errorElement) {
                                    errorElement.textContent = errorMessage;
                                }
                            });
                    });
                }
            })
            .catch(error => {
                console.error("Error while updating profile:", error);
                alert("An error occurred. Please try again.");
            });
    }

    // Attach event listener to "Reset" button
    resetButton.addEventListener("click", function (event) {
        event.preventDefault(); // Prevent default form submission
        resetChanges();
    });

    // Function to reset changes
    function resetChanges() {
        inputFields.forEach(input => {
            const displayElement = document.getElementById(input.id.replace("-input", "-display"));

            if (displayElement) {
                input.value = displayElement.textContent; // Reset input value to display value
            }
            input.classList.add("d-none"); // Hide input fields

            if (displayElement) {
                displayElement.classList.remove("d-none"); // Show display elements
            }

            document.querySelectorAll(".error-message").forEach(error => error.textContent = "");
        });

        saveButton.classList.add("d-none"); // Hide Save button
        resetButton.classList.add("d-none"); // Hide Reset button
        editButton.classList.remove("d-none"); // Show Edit button
    }
});
