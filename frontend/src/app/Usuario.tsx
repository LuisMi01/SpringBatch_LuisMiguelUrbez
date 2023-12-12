// Usuario.tsx
import React from 'react';

interface UsuarioProps {
    usuario: {
        id: string;
        nombre: string;
        email: string;
        // Define el resto de las propiedades del usuario aquí
    };
}

const Usuario: React.FC<UsuarioProps> = ({ usuario }) => (
    <div>
        <h2>{usuario.nombre}</h2>
        <p>{usuario.email}</p>
        {/* Mostrar más información del usuario aquí */}
    </div>
);

export default Usuario;