package br.com.cwi.crescer.api.validator;

import br.com.cwi.crescer.api.controller.dtos.request.task.AlterTaskRequest;
import br.com.cwi.crescer.api.shared.AllFieldsAreNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AlterTaskRequestValidate {
    private static final String MESSAGE_ERROR_EMPTY_REQUEST = "Deve alterar pelo menos 1 campo.";

    public void validate(AlterTaskRequest request) {
        if (AllFieldsAreNull.verifyFields(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_ERROR_EMPTY_REQUEST);
        }
    }
}
