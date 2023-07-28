package br.com.cwi.crescer.api.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@Service
public class ScheduledDateValidate {
    private static final String MESSAGE_ERROR_DATE = "Data programada não pode ser menor que hoje.";

    public void validate(LocalDate scheduledDate) {
        if (now().isAfter(scheduledDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_ERROR_DATE);
        }
    }
}
