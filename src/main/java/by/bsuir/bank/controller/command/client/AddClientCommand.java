package by.bsuir.bank.controller.command.client;

import by.bsuir.bank.controller.Page;
import by.bsuir.bank.controller.command.Command;
import by.bsuir.bank.entity.City;
import by.bsuir.bank.entity.Disability;
import by.bsuir.bank.entity.MaritalStatus;
import by.bsuir.bank.entity.Nationality;
import by.bsuir.bank.service.StaticDataProvider;
import by.bsuir.bank.service.handler.AddClientFormHandler;
import by.bsuir.bank.service.handler.FormHandler;
import by.bsuir.bank.service.wrapper.HttpWrapper;

public class AddClientCommand implements Command {
    private FormHandler formHandler;
    private StaticDataProvider staticDataProvider;

    public AddClientCommand() {
        formHandler = new AddClientFormHandler();
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
                wrapper.setPage("client.add");
                wrapper = populateStaticData(wrapper);
            }
        } else {
            wrapper.setPage("client.add");
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
