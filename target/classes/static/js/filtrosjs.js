function applyFilter(filterType) {
    fetch('/api/filtros?filterType=' + filterType)
        .then(response => response.json())
        .then(users => {
            // Aquí debes implementar la lógica para actualizar la lista de usuarios en la página
            // con la lista filtrada de usuarios
            var userList = document.querySelector('.overflow-x-auto');
            userList.innerHTML = ''; // Limpiar la lista de usuarios existente
            users.forEach(user => {
                // Crear un nuevo elemento para cada usuario y añadirlo a la lista
                var userElement = document.createElement('div');
                userElement.textContent = user.accountId + ' ' + user.transactionType + ' ' + user.amount + ' ' + user.transactionDate;
                userList.appendChild(userElement);
            });
        });
}