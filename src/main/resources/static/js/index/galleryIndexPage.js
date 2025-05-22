document.addEventListener("DOMContentLoaded", () => {
    const modal    = document.getElementById("imageModal");
    const modalImg = document.getElementById("modalImage");
    const prevBtn  = document.getElementById("prevBtn");
    const nextBtn  = document.getElementById("nextBtn");
    const closeBtn = document.querySelector(".close-btn");

    // Grab thumbnails, **not** the modal’s image
    const images = Array.from(document.querySelectorAll(".gallery-item"));

    let currentIndex = 0;

    images.forEach((img, idx) => {
        img.addEventListener("click", () => {
            currentIndex = idx;
            modalImg.src = img.src;
            modal.classList.add("show");
        });
    });

    function closeModal() {
        modal.classList.remove("show");
    }

    function showImage(idx) {
        modalImg.src = images[idx].src;
    }

    prevBtn.addEventListener("click", () => {
        currentIndex = (currentIndex - 1 + images.length) % images.length;
        showImage(currentIndex);
    });
    nextBtn.addEventListener("click", () => {
        currentIndex = (currentIndex + 1) % images.length;
        showImage(currentIndex);
    });

    closeBtn.addEventListener("click", closeModal);

    modal.addEventListener("click", e => {
        if (e.target === modal) closeModal();
    });
    document.addEventListener("keydown", e => {
        if (e.key === "Escape") closeModal();
    });
});
