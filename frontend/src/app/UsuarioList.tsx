// UsuarioList.tsx
import React, { useEffect, useState } from 'react';
import Usuario from './Usuario';

interface Usuario {
    id: string;
    nombre: string;
    email: string;
    // Define el resto de las propiedades del usuario aquí
}

const UsuarioList: React.FC = () => {
    const [usuarios, setUsuarios] = useState<Usuario[]>([]);

    useEffect(() => {
        // Obtener los usuarios de la API aquí y establecerlos con setUsuarios
    }, []);

    return (
        <div>
            {usuarios.map(usuario => (
                <Usuario key={usuario.id} usuario={usuario} />
            ))}
        </div>
    );
};

export default UsuarioList;