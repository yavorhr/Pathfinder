
    // Wait for the DOM to fully load
    document.addEventListener("DOMContentLoaded", function() {
    // Get elements
    const checkboxes = document.querySelectorAll('.category-checkbox');
    const addRouteButton = document.getElementById('addRouteButton');

    // Function to check if at least one checkbox is selected
    function checkCheckboxes() {
    const isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

    addRouteButton.disabled = !isChecked;
}
    // Add event listeners to all checkboxes
    checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', checkCheckboxes);
});

    // Initial check in case the form is pre-filled
    checkCheckboxes();
});
