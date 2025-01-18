// Select relevant elements
const editButton = document.getElementById("edit-button");
const saveButton = document.getElementById("save-button");
const resetButton = document.getElementById("reset-button");
const profileContainer = document.querySelector(".profile-container");

const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

// Store initial values for resetting
const initialValues = {};

// Function to enter edit mode
function enterEditMode() {
    // Hide all <span> and <p> elements, show <input> and <textarea> elements
    profileContainer.querySelectorAll("span, p").forEach(el => el.classList.add("d-none"));
    profileContainer.querySelectorAll("input, textarea").forEach(el => {
        el.classList.remove("d-none");

        const key = el.id;
        if (!initialValues[key]) {
            initialValues[key] = el.value; // Save initial values
        }
    });

    // Show save and reset buttons
    saveButton.classList.remove("d-none");
    resetButton.classList.remove("d-none");
    editButton.classList.add("d-none");
}

// Function to exit edit mode
function exitEditMode() {
    profileContainer.querySelectorAll("span, p").forEach(el => el.classList.remove("d-none"));
    profileContainer.querySelectorAll("input, textarea").forEach(el => el.classList.add("d-none"));

    // Hide save and reset buttons
    saveButton.classList.add("d-none");
    resetButton.classList.add("d-none");
    editButton.classList.remove("d-none");
}

// Function to reset to initial values
function resetValues() {
    profileContainer.querySelectorAll("input, textarea").forEach(el => {
        const key = el.id;
        el.value = initialValues[key];
    });
}

// Event listeners
editButton.addEventListener("click", enterEditMode);

saveButton.addEventListener("click", async (event) => {
    event.preventDefault(); // Prevent default form submission
    const formData = new FormData();

    // Update <span>/<p> with new values and collect form data
    profileContainer.querySelectorAll("input, textarea").forEach(el => {
        const id = el.id.replace("-input", "");
        const span = document.getElementById(`${id}-display`);
        const errorSpan = document.getElementById(`${id}-error`);

        if (span) {
            span.textContent = el.value;
        }

        // Add the input's value to the FormData
        formData.append(id, el.value);

        // Clear previous errors
        if (errorSpan) {
            errorSpan.textContent = "";
        }
    });

    try {
        // Send the updated profile data to the server
        const response = await fetch("/users/profile/edit", {
            method: "POST",
            body: formData,
            headers: {
                [csrfHeader]: csrfToken
            }
        });

        const result = await response.json();

        if (response.ok) {
            // Success: Exit edit mode
            exitEditMode();
        } else {
            // Validation errors returned from the server
            for (const [field, errorMessage] of Object.entries(result.errors)) {
                const errorSpan = document.getElementById(`${field}-error`);
                if (errorSpan) {
                    errorSpan.textContent = errorMessage;
                }
            }
        }
    } catch (error) {
        console.error("An error occurred:", error);
    }
});
resetButton.addEventListener("click", resetValues);

