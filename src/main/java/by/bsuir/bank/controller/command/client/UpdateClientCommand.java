package by.bsuir.bank.controller.command.client;

import by.bsuir.bank.controller.Page;
import by.bsuir.bank.controller.command.Command;
import by.bsuir.bank.dao.AbstractFactory;
import by.bsuir.bank.dao.DataBaseDAOFactory;
import by.bsuir.bank.dao.impl.ClientDataBaseDAO;
import by.bsuir.bank.entity.City;
import by.bsuir.bank.entity.Disability;
import by.bsuir.bank.entity.MaritalStatus;
import by.bsuir.bank.entity.Nationality;
import by.bsuir.bank.exception.IllegalResourceException;
import by.bsuir.bank.service.StaticDataProvider;
import by.bsuir.bank.service.handler.UpdateClientFormHandler;
import by.bsuir.bank.service.handler.FormHandler;
import by.bsuir.bank.service.wrapper.HttpWrapper;

public class UpdateClientCommand implements Command {
    private FormHandler formHandler;
    private StaticDataProvider staticDataProvider;
    private ClientDataBaseDAO dao;

    public UpdateClientCommand() {
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (ClientDataBaseDAO) factory.create("client");
        } catch (IllegalResourceException e) {
            e.printStackTrace();
        }
        formHandler = new UpdateClientFormHandler();
        staticDataProvider = new StaticDataProvider();
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        if (wrapper.isPost()) {
            if (formHandler.handle(wrapper)) {
                wrapper.setPage(new Page(
                        "title.client.show.all",
                        "/fc?command=clients-show-all"));
                wrapper.setIsUpdated(true);
            } else {
                wrapper.setPage("client.update");
                wrapper = populateStaticData(wrapper);
            }
        } else {
            wrapper.setPage("client.update");
            try {
                wrapper.addRequestAttribute("client", dao.find(wrapper.getLong("id")));
            } catch (IllegalResourceException e) {
                e.printStackTrace();
            }
            wrapper = populateStaticData(wrapper);
        }

        return wrapper;
    }

    private HttpWrapper populateStaticData(HttpWrapper wrapper) {
        wrapper.addRequestAttribute(
                "disabilitySelect",
                staticDataProvider.getStaticDataByClassName(Disability.class.getName())
        );
        wrapper.addRequestAttribute(
                "maritalStatusSelect",
                staticDataProvider.getStaticDataByClassName(MaritalStatus.class.getName())
        );
        wrapper.addRequestAttribute(
                "nationalitySelect",
                staticDataProvider.getStaticDataByClassName(Nationality.class.getName())
        );
        wrapper.addRequestAttribute(
                "livingPlaceSelect",
                staticDataProvider.getStaticDataByClassName(City.class.getName())
        );

        return wrapper;
    }
}
