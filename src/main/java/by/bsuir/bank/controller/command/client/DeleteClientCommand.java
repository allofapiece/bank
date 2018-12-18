package by.bsuir.bank.controller.command.client;

import by.bsuir.bank.controller.Page;
import by.bsuir.bank.controller.command.Command;
import by.bsuir.bank.dao.AbstractFactory;
import by.bsuir.bank.dao.DataBaseDAOFactory;
import by.bsuir.bank.dao.impl.ClientDataBaseDAO;
import by.bsuir.bank.exception.IllegalResourceException;
import by.bsuir.bank.service.wrapper.HttpWrapper;

import javax.servlet.http.HttpServletResponse;

public class DeleteClientCommand implements Command {
    private ClientDataBaseDAO dao;

    public DeleteClientCommand() {
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
                dao.delete(wrapper.getLong("id"));
                wrapper.setPage(new Page(
                        "title.client.show.all",
                        "/fc?command=clients-show-all"));
                wrapper.setIsUpdated(true);
            } catch (IllegalResourceException e) {
                e.printStackTrace();
            }
        } else {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        }

        return wrapper;
    }
}
