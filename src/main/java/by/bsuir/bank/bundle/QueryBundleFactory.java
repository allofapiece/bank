package by.bsuir.bank.bundle;

import by.bsuir.bank.exception.IllegalResourceException;

public class QueryBundleFactory {

    public QueryBundle create(String queryBundle) throws IllegalResourceException {
        switch (queryBundle) {
            case "client":
                return ClientQueryBundle.getInstance();

            default:
                throw new IllegalResourceException();
        }
    }
}
