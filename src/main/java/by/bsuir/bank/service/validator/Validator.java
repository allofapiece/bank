package by.bsuir.bank.service.validator;

import by.bsuir.bank.bundle.BundleNamesStore;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO create the validation for type of value(int, string, double, etc.)
public abstract class Validator {
    private Errors errors;
    private boolean isValid = true;
    protected String scenario = "default";

    protected ResourceBundle regexps = ResourceBundle.getBundle(BundleNamesStore.EXPRESSION_BUNDLE);

    public Validator() {
        errors = new Errors();
    }

    public Validator(String scenario) {
        this();
        this.scenario = scenario;
    }

    public Validator(Errors errors) {
        this();
        this.errors = errors;
    }

    public Validator(Errors errors, String scenario) {
        this();
        this.errors = errors;
        this.scenario = scenario;
    }

    public abstract boolean validate(Object object);

    public boolean validate(Object object, Errors errors) {
        this.errors = errors;
        return validate(object);
    }

    public boolean validate(Object object, Errors errors, String scenario) {
        this.errors = errors;
        this.scenario = scenario;
        return validate(object);
    }

    public boolean validate(Object object, String scenario) {
        this.scenario = scenario;
        return validate(object);
    }

    public boolean emptyValidate(Object obj, String field, boolean isStacked) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (obj == null) {
            messages.add("error.empty");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean emptyValidate(String string, String field, boolean isStacked) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (string == null || string.trim().equals("")) {
            messages.add("error.empty");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean emptyValidate(double value, String field, boolean isStacked, double[] ... conditions) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (conditions.length == 0) {
            if (value == 0) {
                messages.add("error.empty");
                isValid = false;
            } else {
                return true;
            }
        } else {
             if (Arrays.asList(conditions).contains(value)) {
                 messages.add("error.empty");
                 isValid = false;
             } else {
                 return true;
             }
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean stringSizeValidate(
            String value,
            int min,
            int max,
            String field,
            boolean isStacked
    ) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (value.length() < min || value.length() > max) {
            messages.add("error.length");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean numberSizeValidate(
            double value,
            double max,
            String field,
            boolean isStacked
    ) {
        return numberSizeValidate(value, 0, max, field, isStacked);
    }

    public boolean numberSizeValidate(
            double value,
            double min,
            double max,
            String field,
            boolean isStacked
    ) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (value < min || value > max) {
            messages.add("error.length");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean stringPatternMatchingValidate(
            String value,
            String patternString,
            String field,
            boolean isStacked
    ) {
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && errors.getAllErrors().containsKey(field)) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            messages.add("error.pattern");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean emailValidate(
            String email,
            String field
    ) {
        emptyValidate(email, field, true);
        stringSizeValidate(email, 4, 50, field, false);
        stringPatternMatchingValidate(
                email,
                regexps.getString("validation.email"),
                field,
                false
        );

        return isValid;
    }

    public boolean passwordValidate(
            String password,
            String field
    ) {
        emptyValidate(password, field, true);
        stringSizeValidate(password, 6, 18, field, false);
        stringPatternMatchingValidate(
                password,
                regexps.getString("validation.user.password"),
                field,
                false
        );

        return isValid;
    }

    public boolean passwordConfirmationValidate(
            String password,
            String confirmedPassword,
            String field
    ) {
        emptyValidate(confirmedPassword, field, true);
        List<String> messages = errors.getFieldErrors(field);

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (!password.equals(confirmedPassword)) {
            messages.add("error.confirmation");
            isValid = false;
        }

        errors.setFieldErrors(field, messages);

        return isValid;
    }

    public boolean timeValidate(
            Date time,
            Date pointTime,
            long distance,
            boolean straightly,
            String field,
            boolean isStacked
    ) {
        if (!isStacked && errors.getAllErrors().containsKey(field)) {
            return false;
        }

        return timeValidate(time.getTime(), pointTime.getTime(), distance, straightly, field, isStacked);
    }

    public boolean timeValidate(
            Date time,
            long pointTime,
            long distance,
            boolean straightly,
            String field,
            boolean isStacked
    ) {
        if (!isStacked && errors.getAllErrors().containsKey(field)) {
            return false;
        }

        return timeValidate(time.getTime(), pointTime, distance, straightly, field, isStacked);
    }

    public boolean timeValidate(
            Date time,
            Date pointTime,
            Date distance,
            boolean straightly,
            String field,
            boolean isStacked
    ) {
        if (!isStacked && errors.getAllErrors().containsKey(field)) {
            return false;
        }

        return timeValidate(time.getTime(), pointTime.getTime(), distance.getTime(), straightly, field, isStacked);
    }

    public boolean timeValidate(
            long time,
            long pointTime,
            long distance,
            boolean straightly,
            String field,
            boolean isStacked
    ) {
        boolean v = true;
        List<String> messages = errors.getFieldErrors(field);

        if (!isStacked && errors.getAllErrors().containsKey(field)) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        long difference = straightly ? time - pointTime : pointTime - time;

        if (straightly ? (difference < distance) : (difference > distance)) {
            messages.add("error.length");
            v = false;
            setValid(v);
        }

        errors.setFieldErrors(field, messages);

        return v;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
