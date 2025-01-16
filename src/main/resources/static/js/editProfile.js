document.addEventListener("DOMContentLoaded", function () {
    const inputFields = document.querySelectorAll("input, textarea");
    const saveButton = document.getElementById("save-button");
    const cancelButton = document.getElementById("cancel-button");
    const editButtons = document.querySelectorAll(".edit-btn");

    // 1. Stored initial values for comparison and resetting
    const originalValues = {};
    inputFields.forEach(input => {
        originalValues[input.id] = input.value.trim();

        // Add event listener to track changes
        input.addEventListener("input", checkForChanges);
    });

    //2. Switch between display and edit mode
    function toggleEdit(fieldId) {
        const displayElement = document.getElementById(`${fieldId}-display`);
        const inputElement = document.getElementById(`${fieldId}-input`);

        if (inputElement.classList.contains("d-none")) {
            // Switch to edit mode
            inputElement.value = displayElement.textContent.trim(); // Pre-fill input with current value
            inputElement.classList.remove("d-none");
            displayElement.classList.add("d-none");
        } else {
            // Switch back to display mode
            const newValue = inputElement.value.trim(); // Get the new value
            if (newValue) {
                displayElement.textContent = newValue; // Update the display element
            }
            inputElement.classList.add("d-none");
            displayElement.classList.remove("d-none");
        }
    }

    // 3. Show/hide "Save" button if any change is made
    function checkForChanges() {
        const hasChanges = Array.from(inputFields).some(
            input => input.value.trim() !== originalValues[input.id]
        );
        saveButton.classList.toggle("d-none", !hasChanges); // Show or hide Save button
    }

    // 4. "Save" button function -> send POST request to server
    function saveProfile() {
        const updatedProfile = {};
        inputFields.forEach(input => {
            updatedProfile[input.id.replace("-input", "")] = input.value.trim();
        });

        fetch("/profile/update", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedProfile),
        })
            .then(response => {
                if (response.ok) {
                    alert("Profile updated successfully!");
                    inputFields.forEach(input => {
                        originalValues[input.id] = input.value.trim(); // Update original values
                        const displayElement = document.getElementById(input.id.replace("-input", "-display"));
                        if (displayElement) {
                            displayElement.textContent = input.value.trim(); // Update displayed content
                        }
                    });
                    saveButton.classList.add("d-none"); // Hide the Save button
                } else {
                    alert("Failed to update profile.");
                }
            })
            .catch(error => console.error("Error updating profile:", error));
    }

    // 4. "Cancel" button function -> reset initial values
    function cancelChanges() {
        inputFields.forEach(input => {
            const originalValue = originalValues[input.id];
            input.value = originalValue; // Reset input value

            const displayElement = document.getElementById(input.id.replace("-input", "-display"));
            if (displayElement) {
                displayElement.textContent = originalValue; // Reset displayed content
            }

            // Switch back to display mode
            input.classList.add("d-none");
            if (displayElement) {
                displayElement.classList.remove("d-none");
            }
        });

        saveButton.classList.add("d-none"); // Hide the Save button
        alert("Changes have been reverted.");
    }

    // Attach event listeners programmatically
    saveButton.addEventListener("click", saveProfile);
    cancelButton.addEventListener("click", cancelChanges);
    editButtons.forEach(btn => btn.addEventListener("click", toggleEdit))

    // Expose toggleEdit to the global scope for use in HTML
    // window.toggleEdit = toggleEdit;
});
