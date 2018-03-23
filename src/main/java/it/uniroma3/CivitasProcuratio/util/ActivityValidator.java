package it.uniroma3.CivitasProcuratio.util;

import it.uniroma3.CivitasProcuratio.model.Activity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ActivityValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Activity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "value", "Non deve essere vuoto", "Inserire una presenza");
    }

}
