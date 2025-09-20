document.addEventListener("DOMContentLoaded", function () {
    const qs = (s) => document.querySelector(s);
    const qsa = (s) => Array.from(document.querySelectorAll(s));

    const editButton = qs("#edit-button");
    const saveButton = qs("#save-button");
    const resetButton = qs("#reset-button");

// exclude upload, username, age and hidden id input from editable fields
    const inputFields = qsa("input:not(#upload-input):not(#username-input):not(#id-input), textarea")
        .filter(input => !input.closest("#age-wrapper")); // also exclude age (though it's span-only)

    // CSRF
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    // Upload elements
    const uploadButton = qs("#upload-button");
    const uploadInput = qs("#upload-input");
    const profileImage = qs("#profile-image");

    if (!editButton) {
        console.error("Missing #edit-button in DOM — profile editor will not initialize.");
        return;
    }

    // Save/Reset hidden at start
    if (saveButton) {
        saveButton.classList.add("d-none");
    }

    if (resetButton) {
        resetButton.classList.add("d-none")
    }

    // Snapshot of original values in enableMode
    let originalValues = {};

    // Upload handlers
    if (uploadButton && uploadInput) {
        uploadButton.addEventListener("click", () => uploadInput.click());
        uploadInput.addEventListener("change", async (event) => {
            const file = event.target.files[0];
            if (!file) return;

            const formData = new FormData();
            formData.append("file", file);

            try {
                const headers = {};
                if (csrfHeader && csrfToken) headers[csrfHeader] = csrfToken;

                const response = await fetch("/api/profile/image-upload", {
                    method: "POST",
                    headers,
                    body: formData,
                });

                if (!response.ok) {
                    const err = await response.json().catch(() => ({}));
                    alert(err.error || "Failed to upload profile picture.");
                    return;
                }

                const data = await response.json();
                const oldPublicInputElement = qs("#oldProfileImagePublicId");
                const oldPublicId = oldPublicInputElement ? oldPublicInputElement.value : null;

                if (oldPublicInputElement) oldPublicInputElement.value = data.publicId;
                if (profileImage) profileImage.src = data.url;

                if (oldPublicId) await deleteOldProfilePicture(oldPublicId);
            } catch (err) {
                console.error(err);
                alert("An error occurred while uploading the picture.");
            }
        });
    }

    function deleteOldProfilePicture(publicId) {
        const headers = {"Content-Type": "application/json"};
        if (csrfHeader && csrfToken) headers[csrfHeader] = csrfToken;

        return fetch("/api/profile/image-delete", {
            method: "DELETE",
            headers,
            body: JSON.stringify({publicId}),
        })
            .then(res => res.json().catch(() => ({})))
            .then(() => console.log("Old profile picture deleted."))
            .catch(e => console.error("Error deleting image:", e));
    }

    // Event listeners
    editButton.addEventListener("click", function (e) {
        e.preventDefault();
        enableEditMode();
    });

    if (saveButton) {
        saveButton.addEventListener("click", function (e) {
            e.preventDefault();
            saveChanges();
        })
    }

    if (resetButton) {
        resetButton.addEventListener("click", function (e) {
            e.preventDefault();
            resetChanges();
        })
    }

    // Enable edit mode
    function enableEditMode() {
        originalValues = {};

        // snapshot input values
        inputFields.forEach(input => {
            originalValues[input.id] = input.value;
        });

        // snapshot visible displays (first & last name)
        const firstD = qs("#firstName-display");
        const lastD = qs("#lastName-display");

        if (firstD) originalValues["firstName-display"] = firstD.textContent;
        if (lastD) originalValues["lastName-display"] = lastD.textContent;

        // toggle full name containers if present
        const fullDisplay = qs(".full-name-display");
        const fullEdit = qs(".full-name-edit");

        if (fullDisplay) fullDisplay.classList.add("d-none");
        if (fullEdit) fullEdit.classList.remove("d-none");

        // hide any display elements and show inputs
        inputFields.forEach(input => {
            const displayEl = qs(`#${input.id.replace("-input", "")}-display`);
            if (displayEl) displayEl.classList.add("d-none");
            input.classList.remove("d-none");
        });

        // toggle buttons
        editButton.classList.add("d-none");
        if (saveButton) saveButton.classList.remove("d-none");
        if (resetButton) resetButton.classList.remove("d-none");
    }

    // Save changes
    async function saveChanges() {
        // build payload defensively
        const payload = {
            id: qs("#id-input") ? qs("#id-input").value.trim() : "",
            firstName: qs("#firstName-input") ? qs("#firstName-input").value.trim() : "",
            lastName: qs("#lastName-input") ? qs("#lastName-input").value.trim() : "",
            aboutMe: qs("#description-input") ? qs("#description-input").value.trim() : "",
            facebookAcc: qs("#facebook-input") ? qs("#facebook-input").value.trim() : "",
            instagramAcc: qs("#instagram-input") ? qs("#instagram-input").value.trim() : "",
            linkedIn: qs("#linkedin-input") ? qs("#linkedin-input").value.trim() : ""
        };

        // clear previous validation messages
        qsa(".error-message").forEach(e => e.textContent = "");

        const socialList = qs(".social-list");
        if (socialList) socialList.classList.remove("max-width");

        // update display elements immediately (same behavior you had)
        inputFields.forEach(input => {
            const key = input.id.replace("-input", "");
            payload[key] = input.value.trim();
            const displayEl = qs(`#${key}-display`);
            if (displayEl) displayEl.textContent = input.value.trim();
        });

        try {
            const headers = {"Content-Type": "application/json"};
            if (csrfHeader && csrfToken) headers[csrfHeader] = csrfToken;

            const res = await fetch("/users/profile/edit", {
                method: "PATCH",
                headers,
                body: JSON.stringify(payload),
            });

            const data = await res.json().catch(() => ({}));

            if (!res.ok) {
                // show validation errors under fields
                Object.keys(data || {}).forEach(field => {
                    const errEl = qs(`#${field}-error`);
                    if (errEl) errEl.textContent = data[field];
                });
                console.warn("Validation failed", data);
                return;
            }

            alert("Profile updated successfully!");

            // refresh displayed values from server response (safe)
            if (data.firstName) qs("#firstName-display") && (qs("#firstName-display").textContent = data.firstName);
            if (data.lastName) qs("#lastName-display") && (qs("#lastName-display").textContent = data.lastName);
            if (data.username) qs("#username-display") && (qs("#username-display").textContent = data.username);
            if (data.aboutMe) qs("#description-display") && (qs("#description-display").textContent = data.aboutMe);
            if (data.facebookAcc) qs("#facebook-display") && (qs("#facebook-display").textContent = data.facebookAcc);
            if (data.instagramAcc) qs("#instagram-display") && (qs("#instagram-display").textContent = data.instagramAcc);
            if (data.linkedIn) qs("#linkedin-display") && (qs("#linkedin-display").textContent = data.linkedIn);

            // hide inputs and show displays
            inputFields.forEach(input => {
                input.classList.add("d-none");
                const displayEl = qs(`#${input.id.replace("-input", "")}-display`);
                if (displayEl) displayEl.classList.remove("d-none");
            });

            // update originalValues snapshot to new saved state (so future reset uses current saved values)
            inputFields.forEach(input => originalValues[input.id] = input.value);

            // toggle full-name containers
            const fullEdit = qs(".full-name-edit");
            const fullDisplay2 = qs(".full-name-display");
            if (fullEdit) fullEdit.classList.add("d-none");
            if (fullDisplay2) fullDisplay2.classList.remove("d-none");

            // restore labels/wrappers if you still use old markup (safeguard)
            const lastLabel = qs('.last-name-label');
            if (lastLabel) lastLabel.classList.add('d-none');
            const firstLabel = qs('.first-name-label');
            if (firstLabel) firstLabel.classList.add('d-none');
            qsa('.name-wrapper').forEach(e => e.classList.remove('dynamic-width'));

            // buttons back to view mode
            if (saveButton) saveButton.classList.add("d-none");
            if (resetButton) resetButton.classList.add("d-none");
            editButton.classList.remove("d-none");

        } catch (err) {
            console.error("Error while saving profile:", err);
            alert("An unexpected error occurred. Please try again.");
        }
    }

    // Reset changes using snapshot
    function resetChanges() {
        // Restore all input values from snapshot (if available)
        inputFields.forEach(input => {
            if (originalValues.hasOwnProperty(input.id)) {
                input.value = originalValues[input.id];
            }
            // hide input and show display element
            input.classList.add("d-none");
            const displayEl = qs(`#${input.id.replace("-input", "")}-display`);
            if (displayEl) displayEl.classList.remove("d-none");
        });

        // Restore first/last name text and toggle containers
        const fullEdit = qs(".full-name-edit");
        const fullDisplay = qs(".full-name-display");

        if (fullEdit) fullEdit.classList.add("d-none");
        if (fullDisplay) {
            if (originalValues["firstName-display"]) qs("#firstName-display").textContent = originalValues["firstName-display"];
            if (originalValues["lastName-display"]) qs("#lastName-display").textContent = originalValues["lastName-display"];
            fullDisplay.classList.remove("d-none");
        } else {
            // fallback: handle old labels/wrappers
            const title = qs(".full-name-title");
            if (title) title.classList.remove("d-none");
            const lastLabel = qs('.last-name-label');
            if (lastLabel) lastLabel.classList.add('d-none');
            const firstLabel = qs('.first-name-label');
            if (firstLabel) firstLabel.classList.add('d-none');
            qsa('.name-wrapper').forEach(e => e.classList.remove('dynamic-width'));
        }

        // clear errors
        qsa(".error-message").forEach(e => e.textContent = "");

        // restore buttons
        if (saveButton) saveButton.classList.add("d-none");
        if (resetButton) resetButton.classList.add("d-none");
        editButton.classList.remove("d-none");
    }
});
