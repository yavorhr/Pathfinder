document.addEventListener("DOMContentLoaded", () => {
    // Grab the one modal
    const modal       = document.getElementById("imageModal");
    const modalImg    = document.getElementById("modalImage");
    const prevBtn     = document.getElementById("prevBtn");
    const nextBtn     = document.getElementById("nextBtn");
    const closeBtn    = document.querySelector(".close-btn");

    //deleteBtn & publicId may be null
    const deleteBtn   = document.querySelector(".deletePictureBtn");
    const publicIdIn  = document.getElementById("modalPublicId");

    const images  = Array.from(document.querySelectorAll(".gallery-item"));
    let currentIndex  = 0;

    // Attach click to every image
    images.forEach((thumb, idx) => {
        thumb.addEventListener("click", () => {
            currentIndex = idx;
            openModal(thumb);
        });
    });

    function openModal(thumb) {
        modalImg.src = thumb.src;
        if (publicIdIn) {
            publicIdIn.value = thumb.dataset.publicId || "";
        }
        if (deleteBtn) {
            const hasId = publicIdIn && publicIdIn.value.trim() !== "";
            deleteBtn.style.display = hasId ? "inline-block" : "none";
        }
        modal.classList.add("show");
    }

    function closeModal() {
        modal.classList.remove("show");
    }

    function showImage(idx) {
        const thumb = images[idx];
        modalImg.src = thumb.src;
        if (publicIdIn) publicIdIn.value = thumb.dataset.publicId || "";
    }

    // prev/next
    prevBtn.addEventListener("click", () => {
        currentIndex = (currentIndex - 1 + images.length) % images.length;
        showImage(currentIndex);
    });
    nextBtn.addEventListener("click", () => {
        currentIndex = (currentIndex + 1) % images.length;
        showImage(currentIndex);
    });

    // close handlers
    closeBtn.addEventListener("click", closeModal);
    modal.addEventListener("click", e => {
        if (e.target === modal) closeModal();
    });
    document.addEventListener("keydown", e => {
        if (e.key === "Escape") closeModal();
    });
});
