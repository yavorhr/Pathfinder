// Select relevant elements
const editButton = document.getElementById("edit-button");
const saveButton = document.getElementById("save-button");
const resetButton = document.getElementById("reset-button");
const cancelButton = document.getElementById("cancel-button");
const profileContainer = document.querySelector(".profile-container");

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
saveButton.addEventListener("click", () => {
    // Add server-side validation and submission logic here
    exitEditMode();
});
resetButton.addEventListener("click", resetValues);
cancelButton.addEventListener("click", exitEditMode);
