package by.bsuir.bank.service.handler;

import by.bsuir.bank.dao.AbstractFactory;
import by.bsuir.bank.dao.DataBaseDAOFactory;
import by.bsuir.bank.dao.impl.ClientDataBaseDAO;
import by.bsuir.bank.entity.*;
import by.bsuir.bank.exception.IllegalResourceException;
import by.bsuir.bank.service.validator.ClientValidator;
import by.bsuir.bank.service.validator.Validator;
import by.bsuir.bank.service.wrapper.HttpWrapper;

public class UpdateClientFormHandler implements FormHandler {
    private Validator validator;
    private ClientDataBaseDAO dao;

    public UpdateClientFormHandler() {
        validator = new ClientValidator();
        validator.setScenario("update");

        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (ClientDataBaseDAO) factory.create("client");
        } catch (IllegalResourceException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean handle(HttpWrapper wrapper) {
        Client client = null;
        try {
            client = dao.find(wrapper.getLong("id"));

            if (client == null) {
                wrapper.addError("client.field.id", "error.bad");
                return false;
            }
        } catch (IllegalResourceException e) {
            return false;
        }

        client.setName(wrapper.getString("name"));
        client.setSurname(wrapper.getString("surname"));
        client.setPatronymic(wrapper.getString("patronymic"));
        client.setBirthday(wrapper.getTimestamp("birthday"));
        client.setPassportSeries(wrapper.getString("passport.series"));
        client.setPassportNumber(wrapper.getString("passport.number"));
        client.setPassportGivenDate(wrapper.getTimestamp("passport.given.date"));
        client.setPassportGivePlace(wrapper.getString("passport.given.place"));
        client.setIdentifyNumber(wrapper.getString("identify.number"));
        client.setLivingAddress(wrapper.getString("living.address"));
        client.setLivingPlace((City) wrapper.getEnum("living.place", City.class));
        client.setHomePhone(wrapper.getString("phone.home"));
        client.setMobilePhone(wrapper.getString("phone.mobile"));
        client.setEmail(wrapper.getString("email"));
        client.setWorkPlace(wrapper.getString("work.place"));
        client.setPost(wrapper.getString("work.post"));
        client.setMaritalStatuses((MaritalStatus) wrapper.getEnum("marital.status", MaritalStatus.class));
        client.setNationality((Nationality) wrapper.getEnum("nationality", Nationality.class));
        client.setRetiree(wrapper.getBoolean("is.retiree"));
        client.setLiableForMilitaryService(wrapper.getBoolean("is.liable.for.military.service"));
        client.setMonthlyEarnings(wrapper.getBigDecimal("monthly.earnings"));
        client.setDisability((Disability) wrapper.getEnum("disability", Disability.class));

        if (!validator.validate(client)) {
            wrapper.addErrors(validator.getErrors());
            return false;
        }

        try {
            dao.update(client);
        } catch (IllegalResourceException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
