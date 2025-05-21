document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("imageModal");
    const modalImg = document.getElementById("modalImage");
    const publicIdInput = document.getElementById("modalPublicId");
    const images = Array.from(document.querySelectorAll(".gallery-img"));
    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");

    let currentIndex = 0;

    images.forEach((img, index) => {
        img.addEventListener("click", () => {
            currentIndex = index;
            openModal(images[currentIndex]);
        });
    });

    function openModal(imageElement) {
        modalImg.src = imageElement.src;
        publicIdInput.value = imageElement.dataset.publicId;
        modal.style.display = "flex";
    }

    window.closeModal = function () {
        modal.style.display = "none";
    }

    function showImage(index) {
        const img = images[index];
        if (img) {
            modalImg.src = img.src;
            publicIdInput.value = img.dataset.publicId;
        }
    }

    prevBtn.addEventListener("click", () => {
        currentIndex = (currentIndex - 1 + images.length) % images.length;
        showImage(currentIndex);
    });

    nextBtn.addEventListener("click", () => {
        currentIndex = (currentIndex + 1) % images.length;
        showImage(currentIndex);
    });

    // Close modal on background click
    modal.addEventListener("click", (e) => {
        if (e.target === modal) {
            closeModal();
        }
    });
});