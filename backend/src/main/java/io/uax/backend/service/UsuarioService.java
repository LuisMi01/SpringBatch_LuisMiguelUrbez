package io.uax.backend.service;

import io.uax.backend.domain.Usuario;
import io.uax.backend.model.UsuarioDTO;
import io.uax.backend.repos.UsuarioRepository;
import io.uax.backend.util.NotFoundException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MongoTemplate mongoTemplate;

    public UsuarioService(final UsuarioRepository usuarioRepository, final MongoTemplate mongoTemplate) {
        this.usuarioRepository = usuarioRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<UsuarioDTO> findAll() {
        System.out.println("Conectado a la base de datos: " + mongoTemplate.getDb().getName());
        System.out.println("Conectado a la colección: " + mongoTemplate.getCollectionName(Usuario.class));
        final List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }
/*
    public UsuarioDTO get(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public ObjectId create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).get_id();
    }

    public void update(final Long id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long id) {
        usuarioRepository.deleteById(id);
    }
*/
    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.set_id(usuario.get_id());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setCuenta(usuario.getCuenta());
        usuarioDTO.setMovimientos(usuario.getMovimientos());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setCuenta(usuarioDTO.getCuenta());
        usuario.setMovimientos(usuarioDTO.getMovimientos());
        return usuario;
    }

}
