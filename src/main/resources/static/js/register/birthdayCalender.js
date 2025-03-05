document.addEventListener("DOMContentLoaded", function() {
    let daySelect = document.getElementById("day");
    let monthSelect = document.getElementById("month");
    let yearSelect = document.getElementById("year");

    // Populate Year Dropdown (from 1900 to current year)
    let currentYear = new Date().getFullYear();
    yearSelect.innerHTML = '<option value="" disabled selected>Year</option>';

    for (let i = currentYear; i >= 1900; i--) {
        let option = document.createElement("option");
        option.value = i;
        option.textContent = i;
        yearSelect.appendChild(option);
    }

    // Function to update days based on month & year
    function updateDays() {
        let selectedMonth = parseInt(monthSelect.value);
        let selectedYear = parseInt(yearSelect.value);
        let daysInMonth = 31; // Default to 31 days

        if (selectedMonth && selectedYear) {
            daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate(); // Get correct days

            // Clear existing days
            daySelect.innerHTML = '<option value="" disabled selected>Day</option>';

            // Populate new days
            for (let i = 1; i <= daysInMonth; i++) {
                let option = document.createElement("option");
                option.value = i;
                option.textContent = i;
                daySelect.appendChild(option);
            }
        }
    }
    // Update days when month or year changes
    monthSelect.addEventListener("change", updateDays);
    yearSelect.addEventListener("change", updateDays);
});