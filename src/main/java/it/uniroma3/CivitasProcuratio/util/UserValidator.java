package it.uniroma3.CivitasProcuratio.util;

import it.uniroma3.CivitasProcuratio.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Non deve essere vuoto");
    }

}
