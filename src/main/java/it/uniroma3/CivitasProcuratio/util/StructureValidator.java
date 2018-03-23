package it.uniroma3.CivitasProcuratio.util;

import it.uniroma3.CivitasProcuratio.model.Structure;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StructureValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Structure.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "site", "Non deve essere vuoto");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "Non deve essere vuoto");

        // phone number validation
        Structure structure = (Structure) o;
        if (!(structure.getPhoneNumber() != null && structure.getPhoneNumber().isEmpty())) {
            //Pattern pattern = Pattern.compile("[0-9]{9}");
            Pattern pattern = Pattern.compile("\\d{2}-\\d{7}");
            Matcher matcher = pattern.matcher(structure.getPhoneNumber());
            if (!matcher.matches()) {
                errors.rejectValue("phoneNumber","Inserire caratteri numerici (9)");
            }
        }
    }

}
