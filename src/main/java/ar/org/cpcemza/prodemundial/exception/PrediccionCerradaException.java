package ar.org.cpcemza.prodemundial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PrediccionCerradaException extends RuntimeException {
    public PrediccionCerradaException() {
        super("El tiempo para cargar predicciones de este partido ha finalizado.");
    }
}
