
    document.addEventListener("DOMContentLoaded", function() {

    const checkboxes = document.querySelectorAll('.category-checkbox');
    const addRouteButton = document.getElementById('addRouteButton');

    function checkCheckboxes() {
    const isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

    addRouteButton.disabled = !isChecked;
}
    checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', checkCheckboxes);
});

    checkCheckboxes();
});
