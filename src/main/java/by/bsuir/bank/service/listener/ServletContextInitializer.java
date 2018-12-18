package by.bsuir.bank.service.listener;

import by.bsuir.bank.exception.ConnectionPoolException;
import by.bsuir.bank.service.handler.SAXHandler.RouteHandler;
import by.bsuir.bank.service.pool.ConnectionPool;
import org.xml.sax.SAXException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class ServletContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initializeConnectionPool();
        initializeRoutes();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        closeConnectionPool();
    }

    private void initializeConnectionPool() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
        }
    }

    private void initializeRoutes() {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        ClassLoader classLoader = getClass().getClassLoader();
        RouteHandler routeHandler = new RouteHandler();
        try {
            SAXParser saxParser = parserFactory.newSAXParser();
            saxParser.parse(new File(classLoader.getResource("pages.xml").getFile()), routeHandler);
        } catch (ParserConfigurationException | SAXException e) {
        } catch (IOException e) {
        }
    }

    private void closeConnectionPool() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.dispose();
    }
}