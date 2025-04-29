document.addEventListener("DOMContentLoaded", function () {
    const enableButtons = document.querySelectorAll(".enable-btn");
    const lockButtons = document.querySelectorAll(".lock-btn");
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const deleteButtons = document.querySelectorAll(".delete-btn");
    const emailButtons = document.querySelectorAll(".email-btn");

    // Disable buttons for logged in user
    document.querySelectorAll("tr").forEach(tr => {
        if (tr.contains(tr.querySelector(".email"))) {
            const userEmail = tr.querySelector(".email").textContent.trim();

            const lockBtn = tr.querySelector(".lock-btn");
            const enableBtn = tr.querySelector(".enable-btn");
            const deleteBtn = tr.querySelector(".delete-btn");
            const emailBtn = tr.querySelector(".send-email-btn");
            const changeRolesBtn = tr.querySelector(".change-role-btn");

            if (userEmail === loggedInUserEmail) {
                enableBtn.disabled = true;
                enableBtn.classList.add("btn-disabled");
                enableBtn.classList.remove("active", "inactive");

                lockBtn.disabled = true;
                lockBtn.classList.add("btn-disabled");
                lockBtn.classList.remove("active", "inactive");

                deleteBtn.disabled = true;
                deleteBtn.classList.add("btn-disabled");
                deleteBtn.classList.remove("active", "inactive");

                emailBtn.disabled = true;
                emailBtn.classList.add("btn-disabled");
                emailBtn.classList.remove("active", "inactive");

                changeRolesBtn.disabled = true;
                changeRolesBtn.classList.add("btn-disabled");
                changeRolesBtn.classList.remove("active", "inactive");
            }
        }
    });

    // Keep the initial state of selected checkboxes for every user
    document.querySelectorAll(".change-role-wrapper").forEach(wrapper => {
        const checkboxes = wrapper.querySelectorAll(".role-checkbox");
        const changeRoleBtn = wrapper.querySelector(".change-role-btn");

        const tr = wrapper.closest("tr");
        const email = tr.querySelector(".email").textContent;

        if (email === loggedInUserEmail) {
            checkboxes.forEach(checkbox => checkbox.disabled = true);
            changeRoleBtn.disabled = true;
            changeRoleBtn.classList.add("btn-disabled");
            return;
        }

        tr.dataset.initialStates = JSON.stringify(Array.from(checkboxes).map(checkbox => !!checkbox.checked));

        // Check for changes in roles
        function checkForChanges(e) {
            const tr = e.currentTarget.closest("tr"); // Find the user's row
            const checkboxes = tr.querySelectorAll(".role-checkbox");

            const initialStates = JSON.parse(tr.dataset.initialStates); // Get the initial state as Array
            const currentStates = Array.from(checkboxes).map(checkbox => !!checkbox.checked);

            const hasChanges = currentStates.some((state, index) => state !== initialStates[index]);

            if (hasChanges) {
                changeRoleBtn.disabled = false;
                changeRoleBtn.classList.add("change-role-enabled");
                changeRoleBtn.classList.remove("btn-disabled");
            } else {
                changeRoleBtn.disabled = true;
                changeRoleBtn.classList.remove("change-role-enabled");
                changeRoleBtn.classList.add("btn-disabled");
            }
        }

        // Add Event Listener to all checkboxes
        checkboxes.forEach(checkbox => checkbox.addEventListener("change", checkForChanges));

        changeRoleBtn.addEventListener("click", function () {
            if (changeRoleBtn.disabled) return;

            tr.dataset.initialStates = JSON.stringify(Array.from(checkboxes).map(checkbox => !!checkbox.checked));

            const selectedRoles = Array.from(checkboxes)
                .filter(checkbox => checkbox.checked)
                .map(checkbox => checkbox.value);

            updateRoles(email, selectedRoles, checkboxes, changeRoleBtn, tr);

        });

        changeRoleBtn.disabled = true;
    });


    function updateRoles(email, selectedRoles, checkboxes, changeRoleBtn, tr) {
        fetch("/admin/api/update-roles", {
            method: "PATCH",
            headers: {
                [csrfHeader]: csrfToken,
                "Content-Type": "application/json",

            },
            body: JSON.stringify({
                email: email,
                roles: selectedRoles,
            }),
        })
            .then(response => {
                if (response.ok) {
                    console.log("Roles updated successfully");

                    tr.dataset.initialStates = JSON.stringify(Array.from(checkboxes).map(checkbox => !!checkbox.checked));

                    // Lock button again
                    changeRoleBtn.disabled = true;
                    changeRoleBtn.classList.add("change-role-disabled");
                    changeRoleBtn.classList.remove("change-role-enabled");
                } else {
                    console.error("Failed to update roles");
                }
            })
            .catch(error => {
                console.error("Error updating roles:", error);
            });
    }

    // Delete user by email
    deleteButtons.forEach(button => {
        button.addEventListener("click", (event) => {

            const emailElement = event.target.closest("tr").querySelector(".email");
            const email = emailElement.textContent;

            fetch(`/admin/api/remove-user/${encodeURIComponent(email)}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    [csrfHeader]: csrfToken
                }
            })
                .then(response => {
                    if (response.ok) {
                        alert("User deleted successfully.");
                        window.location.reload();
                    } else {
                        alert("Error deleting user.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert("An unexpected error occurred.");
                });
        });
    });

    // Enable/Disable user
    enableButtons.forEach(b => {
        b.addEventListener("click", (e) => {
            const clickedButton = e.currentTarget;

            const tr = clickedButton.closest("tr");
            const email = tr.querySelector('.email').textContent.trim();

            fetch(`/admin/change-user-access/${email}`, {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                }
            }).then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
                .then(data => {
                        const isEnabled = data.enabled;
                        const status = tr.querySelector(".status");
                        const disabledTime = tr.querySelector(".disabled_time");

                        if (isEnabled) {
                            status.classList.add("active");
                            status.classList.remove("inactive");
                            status.textContent = "Active"
                            clickedButton.classList.add("inactive")
                            clickedButton.classList.remove("active")
                            clickedButton.textContent = "Disable user"
                            disabledTime.textContent = "N/A";
                        } else {
                            status.classList.add("inactive");
                            status.classList.remove("active");
                            status.textContent = "Not active"
                            clickedButton.classList.add("active")
                            clickedButton.classList.remove("inactive")
                            clickedButton.textContent = "Enable user"

                            const disabledDate = new Date(data.disabledTime);
                            disabledTime.textContent = disabledDate.toLocaleString();
                        }
                    }
                )
                .catch(error => console.error("Error:", error));
        });
    })

    lockButtons.forEach(b => {
        b.addEventListener("click", (e) => {
            const clickedButton = e.currentTarget;

            const tr = clickedButton.closest("tr");
            const email = tr.querySelector('.email').textContent.trim();

            fetch(`/admin/change-user-lock-status/${email}`, {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                }
            }).then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
                .then(data => {
                        const isLocked = data.accountLocked;
                        const status = tr.querySelector(".locked-status");

                        if (isLocked) {
                            status.classList.add("inactive");
                            status.classList.remove("active");
                            status.textContent = "Locked"
                            clickedButton.classList.add("active")
                            clickedButton.classList.remove("inactive")
                            clickedButton.textContent = "Unlock user"
                        } else {
                            status.classList.add("active");
                            status.classList.remove("inactive");
                            status.textContent = "Unlocked"
                            clickedButton.classList.add("inactive")
                            clickedButton.classList.remove("active")
                            clickedButton.textContent = "Lock user"
                        }
                    }
                )
                .catch(error => console.error("Error:", error));
        });
    })

    // Sent email to user
    emailButtons.forEach(button => {
        button.addEventListener("click", function () {
            const tr = this.closest("tr");
            const email = tr.querySelector(".email").textContent.trim();

            const subject = "Subject of the Email"; // Customize the subject
            const body = "Hello, \n\n........... ."; // Customize the body

            window.location.href = `mailto:${email}?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;
        });
    });
});
