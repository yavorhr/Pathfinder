document.addEventListener("DOMContentLoaded", function () {
    let daySelect = document.getElementById("day");
    let monthSelect = document.getElementById("month");
    let yearSelect = document.getElementById("year");

    // Disable months and days initially
    monthSelect.disabled = true;
    daySelect.disabled = true;
    yearSelect.disabled=true;

    yearSelect.innerHTML = '<option value="" disabled selected>Year</option>';

    // Get last selected values from Local Storage
    let selectedYear = localStorage.getItem("selectedYear");
    let selectedMonth = localStorage.getItem("selectedMonth");
    let selectedDay = localStorage.getItem("selectedDay");

    // Populate Year dropdown
    function populateYears() {
        let currentYear = new Date().getFullYear();
        let startYear = currentYear - 100;

        for (let year = currentYear; year >= startYear; year--) {
            let option = document.createElement("option");
            option.value = year;
            option.textContent = year;

            if (selectedYear && selectedYear == year) {
                option.selected = true;
            }
            yearSelect.appendChild(option);
        }
        yearSelect.disabled = false;
    }

    // Populate Months when Year is selected
    function enableMonths() {
        if (!yearSelect.value) return;

        localStorage.setItem("selectedYear", yearSelect.value); // Save the last selected year

        monthSelect.innerHTML = '<option value="" disabled selected>Month</option>';

        let months = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"];

        months.forEach((month, index) => {
            let option = document.createElement("option");
            option.value = index + 1;
            option.textContent = month;
            if (selectedMonth && selectedMonth == (index + 1)) {
                option.selected = true;
            }
            monthSelect.appendChild(option);
        });

        monthSelect.disabled = false;
        populateDays(); // Auto-populate days if month was pre-selected
    }

    // Populate Days when Month is selected
    function populateDays() {
        if (!monthSelect.value || !yearSelect.value) return;

        localStorage.setItem("selectedMonth", monthSelect.value); // Save last selected month

        let daysInMonth = new Date(yearSelect.value, monthSelect.value, 0).getDate();

        daySelect.innerHTML = '<option value="" disabled selected>Day</option>';

        for (let i = 1; i <= daysInMonth; i++) {
            let option = document.createElement("option");
            option.value = i;
            option.textContent = i;
            if (selectedDay && selectedDay == i) {
                option.selected = true;
            }
            daySelect.appendChild(option);
        }

        daySelect.disabled = false;
    }

    // Event Listeners
    populateYears(); // Populate years on page load
    yearSelect.addEventListener("change", enableMonths);
    monthSelect.addEventListener("change", populateDays);

    daySelect.addEventListener("change", function () {
        localStorage.setItem("selectedDay", daySelect.value); // Save last selected day
    });

    // Restore selected values after redirect
    if (selectedYear) {
        enableMonths(); // Populate months immediately
    }
});