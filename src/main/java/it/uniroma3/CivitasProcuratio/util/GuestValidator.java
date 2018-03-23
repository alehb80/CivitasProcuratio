package it.uniroma3.CivitasProcuratio.util;

import it.uniroma3.CivitasProcuratio.model.Guest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GuestValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Guest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "Non deve essere vuoto");
    }

}
