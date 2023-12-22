function applyFilter(filterType) {
    fetch('/filter?filterType=' + filterType)
        .then(response => response.json())
        .then(users => {
            // Aquí debes implementar la lógica para actualizar la lista de usuarios en la página
            // con la lista filtrada de usuarios
        });
}