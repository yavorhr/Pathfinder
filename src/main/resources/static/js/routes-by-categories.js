document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll(".card__article").forEach(function(card) {
        card.addEventListener("click", function() {
            // Close other cards first (optional)
            document.querySelectorAll(".card__article").forEach(c => {
                if (c !== card) c.classList.remove("active");
            });

            // Toggle current card active state
            card.classList.toggle("active");
        });
    });
});
