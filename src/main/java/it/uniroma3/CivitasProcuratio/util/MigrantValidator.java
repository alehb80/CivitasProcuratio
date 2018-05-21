package it.uniroma3.CivitasProcuratio.util;

import it.uniroma3.CivitasProcuratio.model.Migrant;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MigrantValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Migrant.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "checkInDate", "Non deve essere vuoto");
    }

}
