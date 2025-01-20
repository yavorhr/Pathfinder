document.addEventListener("DOMContentLoaded", function () {
    const editButton = document.getElementById("edit-button");
    const saveButton = document.getElementById("save-button");
    const resetButton = document.getElementById("reset-button");
    const inputFields = document.querySelectorAll("input, textarea");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    // Attach event listener to "Edit" button
    editButton.addEventListener("click", function (event) {
        event.preventDefault(); // Prevent form submission
        enableEditMode();
    });

    // Function to enable edit mode
    function enableEditMode() {
        inputFields.forEach(input => {
            const displayElement = document.getElementById(input.id.replace("-input", "-display"));
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
            // id: document.getElementById("id-input").value.trim(),
            fullName: document.getElementById("fullName-input").value.trim(),
            username: document.getElementById("username-input").value.trim(),
            // email: document.getElementById("email-input").value.trim(),
            // age: parseInt(document.getElementById("age-input").value.trim(), 10),
            // description: document.getElementById("description-input").value.trim(),
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
            method: "POST",
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
                                console.log(errorMessage)
                                const errorElement = document.getElementById(`${field}-error`);
                                if (errorElement) {

                                    errorElement.textContent = errorMessage;
                                    // console.log(errorElement);
                                    // console.log(errorMessage);
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
        });

        saveButton.classList.add("d-none"); // Hide Save button
        resetButton.classList.add("d-none"); // Hide Reset button
        editButton.classList.remove("d-none"); // Show Edit button
    }
});
