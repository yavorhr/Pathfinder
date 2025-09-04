document.addEventListener("DOMContentLoaded", function () {

    const checkboxes = document.querySelectorAll('.categories input[type="checkbox"]');
    const addRouteButton = document.getElementById('addRouteButton');

    function checkCheckboxes() {
        const isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

        if (isChecked) {
            addRouteButton.style.cursor = "pointer";
        } else {
            addRouteButton.style.cursor = "not-allowed";
        }

        addRouteButton.disabled = !isChecked;
    }

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', checkCheckboxes);
    });

    checkCheckboxes();
});
