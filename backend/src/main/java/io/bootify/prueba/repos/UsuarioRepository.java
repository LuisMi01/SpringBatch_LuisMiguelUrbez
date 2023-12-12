package io.bootify.prueba.repos;

import io.bootify.prueba.domain.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UsuarioRepository extends MongoRepository<Usuario, Long> {
}
