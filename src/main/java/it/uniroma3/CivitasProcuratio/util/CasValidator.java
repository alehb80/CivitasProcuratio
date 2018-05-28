package it.uniroma3.CivitasProcuratio.util;

import it.uniroma3.CivitasProcuratio.model.Cas;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CasValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Cas.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "site", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capacity", "Non deve essere vuoto");

        // phone number validation
        Cas cas = (Cas) o;
        if (!(cas.getPhoneNumber() != null && cas.getPhoneNumber().isEmpty())) {
            //Pattern pattern = Pattern.compile("[0-9]{9}");
            Pattern pattern = Pattern.compile("\\d{2}-\\d{7}");
            Matcher matcher = pattern.matcher(cas.getPhoneNumber());
            if (!matcher.matches()) {
                errors.rejectValue("phoneNumber","Inserire caratteri numerici (9)");
            }
        }
    }

}
