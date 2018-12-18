package by.bsuir.bank.service.handler;

import by.bsuir.bank.dao.AbstractFactory;
import by.bsuir.bank.dao.DataBaseDAOFactory;
import by.bsuir.bank.dao.impl.ClientDataBaseDAO;
import by.bsuir.bank.entity.*;
import by.bsuir.bank.exception.IllegalResourceException;
import by.bsuir.bank.service.validator.ClientValidator;
import by.bsuir.bank.service.validator.Validator;
import by.bsuir.bank.service.wrapper.HttpWrapper;

public class AddClientFormHandler implements FormHandler {
    private Validator validator;
    private ClientDataBaseDAO dao;

    public AddClientFormHandler() {
        validator = new ClientValidator();
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
        Client client = new Client();

        client.setName(wrapper.getString("name"));
        client.setSurname(wrapper.getString("surname"));
        client.setPatronymic(wrapper.getString("patronymic"));
        client.setBirthday(wrapper.getTimestamp("client.field.birthday"));
        client.setPassportSeries(wrapper.getString("passport.series"));
        client.setPassportNumber(wrapper.getString("passport.number"));
        client.setPassportGivenDate(wrapper.getTimestamp("client.field.passport.given.date"));
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
        client.setMonthlyEarnings(wrapper.getBigDecimal("client.field.monthly.earnings"));
        client.setDisability((Disability) wrapper.getEnum("disability", Disability.class));

        if (!validator.validate(client)) {
            wrapper.addErrors(validator.getErrors());
            return false;
        }

        dao.create(client);

        return true;
    }
}
