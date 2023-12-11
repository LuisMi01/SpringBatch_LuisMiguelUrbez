package io.uax.backend.repos;

import com.mongodb.client.MongoDatabase;
import io.uax.backend.domain.Usuario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, ObjectId> {

}

