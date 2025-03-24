document.addEventListener("DOMContentLoaded", function () {
    const enableButtons = document.querySelectorAll(".enable-btn");
    const checkboxes = document.querySelectorAll(".role-checkbox");
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // Keep the initial state of selected checkboxes for every user
    document.querySelectorAll(".change-role-wrapper").forEach(wrapper => {
        const checkboxes = wrapper.querySelectorAll(".role-checkbox");
        const changeRoleBtn = wrapper.querySelector(".change-role-btn");

        const tr = wrapper.closest("tr");
        const username = tr.querySelector(".username").textContent;

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
                changeRoleBtn.classList.add("btn-enabled");
                changeRoleBtn.classList.remove("btn-disabled");
            } else {
                changeRoleBtn.disabled = true;
                changeRoleBtn.classList.add("btn-disabled");
                changeRoleBtn.classList.remove("btn-enabled");
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

            updateRoles(username, selectedRoles, checkboxes, changeRoleBtn, tr);

        });

        changeRoleBtn.disabled = true;
    });


    function updateRoles(username, selectedRoles, checkboxes, changeRoleBtn, tr) {

        fetch("/admin/api/update-roles", {
            method: "PATCH",
            headers: {
                [csrfHeader]: csrfToken,
                "Content-Type": "application/json",

            },
            body: JSON.stringify({
                username: username,
                roles: selectedRoles,  // Pass the selected roles
            }),
        })
            .then(response => {
                if (response.ok) {
                    console.log("Roles updated successfully");

                    tr.dataset.initialStates = JSON.stringify(Array.from(checkboxes).map(checkbox => !!checkbox.checked));

                    // Lock button again
                    changeRoleBtn.disabled = true;
                    changeRoleBtn.classList.add("btn-disabled");
                    changeRoleBtn.classList.remove("btn-enabled");
                } else {
                    console.error("Failed to update roles");
                }
            })
            .catch(error => {
                console.error("Error updating roles:", error);
            });
    }

    // Delete user by email
    // deleteButtons.forEach(button => {
    //     button.addEventListener("click", (event) => {
    //
    //         const emailElement = event.target.closest("tr").querySelector("#email");
    //         const email = emailElement.textContent;
    //
    //         fetch(`/admin/remove-user/${encodeURIComponent(email)}`, {
    //             method: "POST",
    //             headers: {
    //                 "Content-Type": "application/json",
    //                 [csrfHeader]: csrfToken
    //             }
    //         })
    //             .then(response => {
    //                 if (response.ok) {
    //                     alert("User deleted successfully.");
    //                     window.location.reload(); // Optionally refresh the page
    //                 } else {
    //                     alert("Error deleting user.");
    //                 }
    //             })
    //             .catch(error => {
    //                 console.error("Error:", error);
    //                 alert("An unexpected error occurred.");
    //             });
    //     });
    // });

    // Enable user
    enableButtons.forEach(b => {
        b.addEventListener("click", (e) => {
            const clickedButton = e.currentTarget;

            const tr = clickedButton.closest("tr");
            const username = tr.querySelector('.username').textContent.trim();

            fetch(`/admin/change-user-access/${username}`, {
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

                        if (isEnabled) {
                            status.classList.add("enable-user-access");
                            status.classList.remove("disable-user-access");
                            status.textContent = "Active"
                            clickedButton.textContent = "Disable user"
                        } else {
                            status.classList.remove("enable-user-access");
                            status.classList.add("disable-user-access");
                            status.textContent = "Not active"
                            clickedButton.textContent = "Enable user"
                        }
                    }
                )
                .catch(error => console.error("Error:", error));
        });
    })

});
