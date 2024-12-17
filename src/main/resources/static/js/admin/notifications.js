document.addEventListener("DOMContentLoaded", function () {
    const deleteButtons = document.querySelectorAll(".delete-btn");
    const enableButtons = document.querySelectorAll(".enable-btn");
    const roleSelects = document.querySelectorAll(".role-select");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

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

    // Enable User
    enableButtons.forEach(button => {
        button.addEventListener("click", () => {
            alert("User enabled.");
            // Add backend call here
        });
    });

    // Role Change
    roleSelects.forEach(select => {
        select.addEventListener("change", (event) => {
            alert("Role changed to: " + event.target.value);
            // Add backend call here
        });
    });
});
