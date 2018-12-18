package by.bsuir.bank.controller;

import by.bsuir.bank.controller.command.Command;
import by.bsuir.bank.controller.command.CommandProvider;
import by.bsuir.bank.dao.AbstractFactory;
import by.bsuir.bank.dao.DataBaseDAOFactory;
import by.bsuir.bank.dao.impl.ClientDataBaseDAO;
import by.bsuir.bank.exception.IllegalResourceException;
import by.bsuir.bank.service.wrapper.HttpWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import org.apache.log4j.Logger;

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandProvider commandProvider = new CommandProvider();
        String requestedCommand = req.getParameter("command");


//
//        DataBaseDAOFactory factory;
//
//        try {
//            factory = (DataBaseDAOFactory) new AbstractFactory().create("DataBaseDAO");
//            ((ClientDataBaseDAO) factory.create("client")).findAll();
//        } catch (IllegalResourceException e) {
//            e.printStackTrace();
//        }



        req.removeAttribute("errors");
        req.getSession().removeAttribute("errors");
        HttpWrapper wrapper = new HttpWrapper(req, resp);

        if (requestedCommand == null) {
            wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            try {
                Command command = commandProvider.getCommand(requestedCommand);
                command.execute(wrapper);
            } catch (IllegalResourceException e) {
                wrapper.setHttpError(HttpServletResponse.SC_NOT_FOUND);
            }
        }

        wrapper.go();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
