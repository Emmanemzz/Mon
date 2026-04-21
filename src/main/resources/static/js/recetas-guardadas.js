function confirmarQuitar(boton) {
  const id = boton.getAttribute('data-id');
  const titulo = boton.getAttribute('data-titulo');
  document.getElementById('tituloReceta').textContent = titulo;
  document.getElementById('formQuitar').action = '/recetas/' + id + '/quitarGuardado';
  const modal = new bootstrap.Modal(document.getElementById('modalQuitar'));
  modal.show();
}