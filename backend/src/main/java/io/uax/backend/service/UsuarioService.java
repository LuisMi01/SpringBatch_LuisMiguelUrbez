package io.uax.backend.service;

import io.uax.backend.domain.Usuario;
import io.uax.backend.model.UsuarioDTO;
import io.uax.backend.repos.UsuarioRepository;
import io.uax.backend.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .collect(Collectors.toList());
        System.out.println("Usuarios encontrados: " + usuarios);
        return usuarios;
    }

    /*
    public void getGroceryItemByName(String name) {
        System.out.println("Getting item by name: " + name);
        Usuario item = usuarioRepository.findItemByName(name);
        System.out.println(getItemDetails(item));
    }

    // 4. Get count of documents in the collection
    public void findCountOfGroceryItems() {
        long count = usuarioRepository.count();
        System.out.println("Number of documents in the collection: " + count);
    }


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
    usuarioDTO.setId(usuario.getId());
    usuarioDTO.setNombre(usuario.getNombre());
    usuarioDTO.setEmail(usuario.getEmail());
    usuarioDTO.setPassword(usuario.getPassword());
    usuarioDTO.setCuenta(usuario.getCuenta());
    usuarioDTO.setMovimientos(usuario.getMovimientos());
    return usuarioDTO;
}

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setId(usuarioDTO.getId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setCuenta(usuarioDTO.getCuenta());
        usuario.setMovimientos(usuarioDTO.getMovimientos());
        return usuario;
    }

}
