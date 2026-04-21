function confirmarEliminar(boton) {
  const id = boton.getAttribute('data-id');
  const titulo = boton.getAttribute('data-titulo');
  document.getElementById('tituloReceta').textContent = titulo;
  document.getElementById('formEliminar').action = '/recetas/' + id + '/eliminar';
  const modal = new bootstrap.Modal(document.getElementById('modalEliminar'));
  modal.show();
}