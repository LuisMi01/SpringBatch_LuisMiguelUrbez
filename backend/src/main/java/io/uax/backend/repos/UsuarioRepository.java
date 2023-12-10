package io.uax.backend.repos;

import io.uax.backend.domain.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UsuarioRepository extends MongoRepository<Usuario, Long> {
}
