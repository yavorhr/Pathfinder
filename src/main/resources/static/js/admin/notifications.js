document.addEventListener("DOMContentLoaded", function () {
    const deleteButtons = document.querySelectorAll(".delete-btn");
    const enableButtons = document.querySelectorAll(".enable-btn");
    const userEmail = document.querySelector("#email").textContent.trim();

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // Change role
    document.querySelectorAll(".change-role-btn").forEach(button => {
        button.addEventListener("click", function () {
            const row = this.closest("tr");
            const roleSelect = row.querySelector(".role-select");
            const selectedRole = roleSelect.value;

            if (!selectedRole) {
                alert("Please select a role before changing.");
                return;
            }

            fetch("/admin/change-role", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({email: userEmail, role: selectedRole})
            })
                .then(response => response.json())
                .then(data => {
                    alert(data.message || "Role updated successfully!");
                })
                .catch(error => {
                    console.error("Error updating role:", error);
                    alert("Failed to update role.");
                });
        });
    });

    // Delete user by email
    deleteButtons.forEach(button => {
        button.addEventListener("click", (event) => {

            const emailElement = event.target.closest("tr").querySelector("#email");
            const email = emailElement.textContent;

            fetch(`/admin/remove-user/${encodeURIComponent(email)}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    [csrfHeader]: csrfToken
                }
            })
                .then(response => {
                    if (response.ok) {
                        alert("User deleted successfully.");
                        window.location.reload(); // Optionally refresh the page
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

    // Enable user
    enableButtons.forEach(b => {
        b.addEventListener("click", (e) => {
            const clickedButton = e.currentTarget;
            const tr = clickedButton.closest("tr");
            const username = tr.querySelector('td').textContent.trim();

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
                        console.log(data)
                        const isEnabled = data.enabled;

                        if (isEnabled) {
                            clickedButton.textContent = "Disable User";
                        } else {
                            clickedButton.textContent = "Enable User";
                        }
                    }
                )
                .catch(error => console.error("Error:", error));
        });
    })

});
