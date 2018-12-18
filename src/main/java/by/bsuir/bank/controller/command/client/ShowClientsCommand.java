package by.bsuir.bank.controller.command.client;

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
import by.bsuir.bank.service.handler.AddClientFormHandler;
import by.bsuir.bank.service.handler.FormHandler;
import by.bsuir.bank.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowClientsCommand implements Command {
    private ClientDataBaseDAO dao;

    public ShowClientsCommand() {
        DataBaseDAOFactory factory;

        try {
            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
            dao = (ClientDataBaseDAO) factory.create("client");
        } catch (IllegalResourceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HttpWrapper execute(HttpWrapper wrapper) {
        if (wrapper.isGet()) {
            try {
                wrapper.addRequestAttribute("clients", dao.findAll());
                wrapper.setPage("client.show.all");
            } catch (IllegalResourceException e) {
                e.printStackTrace();
            }
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
