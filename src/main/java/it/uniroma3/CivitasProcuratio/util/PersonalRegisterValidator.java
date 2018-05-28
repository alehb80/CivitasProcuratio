package it.uniroma3.CivitasProcuratio.util;

import it.uniroma3.CivitasProcuratio.model.PersonalRegister;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonalRegisterValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PersonalRegister.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nationality", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "checkInDate", "Non deve essere vuoto");
    }

}
