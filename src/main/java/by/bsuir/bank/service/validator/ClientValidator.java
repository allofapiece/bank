package by.bsuir.bank.service.validator;

import by.bsuir.bank.dao.AbstractFactory;
import by.bsuir.bank.dao.DataBaseDAOFactory;
import by.bsuir.bank.dao.impl.ClientDataBaseDAO;
import by.bsuir.bank.entity.Client;
import by.bsuir.bank.exception.IllegalResourceException;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator extends Validator {
    private ClientDataBaseDAO dao;

    public ClientValidator() {
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (ClientDataBaseDAO) factory.create("client");
        } catch (IllegalResourceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validate(Object object) {
        Client client = (Client) object;

        emptyValidate(client.getName(), "client.field.name", true);
        emptyValidate(client.getSurname(), "client.field.surname", true);
        emptyValidate(client.getPatronymic(), "client.field.patronymic", true);
        emptyValidate(client.getBirthday(), "client.field.birthday", true);
        emptyValidate(client.getPassportNumber(), "client.field.passport.number", true);
        emptyValidate(client.getPassportGivenDate(), "client.field.passport.given.date", true);
        emptyValidate(client.getPassportGivePlace(), "client.field.passport.given.place", true);
        emptyValidate(client.getPassportSeries(), "client.field.passport.series", true);
        emptyValidate(client.getIdentifyNumber(), "client.field.identify.number", true);
        emptyValidate(client.getLivingAddress(), "client.field.living.address", true);
        emptyValidate(client.getLivingPlace(), "client.field.living.place", true);
        emptyValidate(client.getDisability(), "client.field.disability", true);
        emptyValidate(client.getMaritalStatuses(), "client.field.marital.status", true);
            uniqueIdentifyNumberValidate(client.getIdentifyNumber(), "client.field.identify.number", false);

        return isValid();
    }

    private boolean uniqueIdentifyNumberValidate(String identifyNumber, String field, boolean isStacked) {
        List<String> messages = this.getErrors().getFieldErrors(field);

        if (!isStacked && messages != null) {
            return false;
        }

        if (messages == null) {
            messages = new ArrayList<>();
        }

        if (this.dao.findByIdentifyNumber(identifyNumber) != null) {
            messages.add("error.unique");
            setValid(false);
        }

        this.getErrors().setFieldErrors(field, messages);

        return isValid();
    }
}
