package io.bootify.prueba.repos;

import io.bootify.prueba.domain.Usuario;
import io.bootify.prueba.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class UsuarioListener extends AbstractMongoEventListener<Usuario> {

    private final PrimarySequenceService primarySequenceService;

    public UsuarioListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Usuario> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(primarySequenceService.getNextValue());
        }
    }

}
