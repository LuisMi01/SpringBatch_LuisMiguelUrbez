package io.uax.backend.repos;

import io.uax.backend.domain.Usuario;
import io.uax.backend.service.PrimarySequenceService;
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
        if (event.getSource().get_id() == null) {
            event.getSource().set_id(primarySequenceService.getNextValue());
        }
    }

}
