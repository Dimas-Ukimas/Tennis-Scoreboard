// JavaScript for toggling the dropdown menu
document.addEventListener("DOMContentLoaded", function () {
    const navToggle = document.querySelector(".nav-toggle");
    const navLinks = document.querySelector(".nav-links");
    const input = document.getElementById("playerFilterInput");
    const resetButton = document.getElementById("resetFilterButton");

    navToggle.addEventListener("click", function () {
        navLinks.classList.toggle("active");
    });

    if (input) {
        input.addEventListener("keypress", function (event) {
            if (event.key === "Enter") {
                event.preventDefault();
                const playerName = input.value.trim();
                const url = playerName
                    ? `/matches?page=1&filter_by_player_name=${encodeURIComponent(playerName)}`
                    : `/matches?page=1`;
                window.location.href = url;
            }
        });
    }

    if (resetButton) {
        resetButton.addEventListener("click", function () {
            let url = new URL(window.location.href);

            let hadFilter = url.searchParams.has("filter_by_player_name");
            url.searchParams.delete("filter_by_player_name");

            if (!hadFilter) {
                window.location.href = url.toString();
                return;
            }

            if (!url.searchParams.has("page") || url.searchParams.get("page") === "1") {
                window.location.href = url.toString();
            } else {
                url.searchParams.set("page", "1");
                window.location.href = url.toString();
            }
        });
    }

});
