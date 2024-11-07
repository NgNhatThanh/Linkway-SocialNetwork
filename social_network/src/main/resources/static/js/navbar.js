document.addEventListener("DOMContentLoaded", function() {
    console.log("abcdeff");

    const avatarIcon = document.getElementById("avatar-icon");
    const dropdownMenu = document.getElementById("dropdown-menu");

    avatarIcon.addEventListener("click", function() {
        dropdownMenu.style.display = dropdownMenu.style.display === "block" ? "none" : "block";
    });

    document.addEventListener("click", function(event) {
        if (!avatarIcon.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.display = "none";
        }
    });
});
