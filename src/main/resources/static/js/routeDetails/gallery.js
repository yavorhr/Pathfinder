document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("imageModal");
    const modalImg = document.getElementById("modalImage");
    const publicIdInput = document.getElementById("modalPublicId");
    const images = Array.from(document.querySelectorAll(".gallery-item"));
    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");
    const closeBtn = document.querySelector(".close-btn");

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

        const deletePictureBtn = document.querySelector(".deletePictureBtn");

        if (publicIdInput.value && publicIdInput.value !== "undefined" && publicIdInput.value.trim() !== "") {
            deletePictureBtn.style.display = "block";
        } else {
            deletePictureBtn.style.display = "none";
        }
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

    modal.addEventListener("click", (e) => {
        if (e.target === modal) {
            closeModal();
        }
    });

    closeBtn.addEventListener("click", closeModal);
});


