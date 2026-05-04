document.addEventListener("DOMContentLoaded", function () {
    const formsCerrarSesion = document.querySelectorAll("form[action*='logout']");

    formsCerrarSesion.forEach(function (form) {
        form.addEventListener("submit", function (e) {
            e.preventDefault();
            if (confirm("¿Seguro que quieres cerrar sesión?")) {
                form.submit();
            }
        });
    });
});