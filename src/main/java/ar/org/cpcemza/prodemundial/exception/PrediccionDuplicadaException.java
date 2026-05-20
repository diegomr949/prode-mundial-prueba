package ar.org.cpcemza.prodemundial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PrediccionDuplicadaException extends RuntimeException {
    public PrediccionDuplicadaException(Long partidoId) {
        super("Ya existe una predicción para el partido con id " + partidoId + ".");
    }
}
