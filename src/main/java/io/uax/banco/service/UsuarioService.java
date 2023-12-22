package io.uax.banco.service;

import io.uax.banco.domain.Usuario;
import io.uax.banco.model.UsuarioDTO;
import io.uax.banco.repos.UsuarioRepository;
import io.uax.banco.util.NotFoundException;

import java.util.List;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("id"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

        public List<Usuario> findByGastoDesc() {
            return usuarioRepository.findByOrderByAmountDesc();
        }

        public List<Usuario> findByIngresoDesc() {
            return usuarioRepository.findByOrderByAmountAsc();
        }

        public List<Usuario> findByFechaAsc() {
            return usuarioRepository.findByOrderByTransactionDateAsc();
        }

        public List<Usuario> findByFechaDesc() {
            return usuarioRepository.findByOrderByTransactionDateDesc();
        }

        public List<Usuario> findByCuentaAsc() {
            return usuarioRepository.findByOrderByAccountIdAsc();
        }

        public List<Usuario> findByCuentaDesc() {
            return usuarioRepository.findByOrderByAccountIdDesc();
        }


    public UsuarioDTO get(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getId();
    }

    //buscar 1 id
    public UsuarioDTO get1(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
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

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setAccountId(usuario.getAccountId());
        usuarioDTO.setTransactionType(usuario.getTransactionType());
        usuarioDTO.setAmount(usuario.getAmount());
        usuarioDTO.setTransactionDate(usuario.getTransactionDate());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setAccountId(usuarioDTO.getAccountId());
        usuario.setTransactionType(usuarioDTO.getTransactionType());
        usuario.setAmount(usuarioDTO.getAmount());
        usuario.setTransactionDate(usuarioDTO.getTransactionDate());
        return usuario;
    }

}
