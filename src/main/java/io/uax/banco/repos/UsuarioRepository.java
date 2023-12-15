package io.uax.banco.repos;

import io.swagger.v3.oas.annotations.links.Link;
import io.uax.banco.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
