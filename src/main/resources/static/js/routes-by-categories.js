document.addEventListener("DOMContentLoaded", function() {
    const cards = document.querySelectorAll(".card__article");

    cards.forEach(function(card) {
        card.addEventListener("click", function(e) {
            e.stopPropagation();

            cards.forEach(c => {
                if (c !== card) c.classList.remove("active");
            });
            card.classList.toggle("active");
        });
    });

    document.addEventListener("click", function() {
        cards.forEach(card => card.classList.remove("active"));
    });
});
