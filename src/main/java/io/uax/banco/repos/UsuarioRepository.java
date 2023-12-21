package io.uax.banco.repos;

import io.swagger.v3.oas.annotations.links.Link;
import io.uax.banco.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByAmountBetween(Double minAmount, Double maxAmount);

    Optional<Usuario> findByAccountId(String accountId);


}
