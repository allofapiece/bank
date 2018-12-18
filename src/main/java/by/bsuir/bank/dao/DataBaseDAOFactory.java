package by.bsuir.bank.dao;

import by.bsuir.bank.dao.impl.ClientDataBaseDAO;
import by.bsuir.bank.exception.IllegalResourceException;

/**
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class DataBaseDAOFactory implements Factory {
    /**
     * @param type requested com.epam.au.entity type
     * @return DataBaseDAO specific database DAO implementation
     * @throws IllegalResourceException if requested type does not exist
     */
    public DataBaseDAO create(String type) throws IllegalResourceException {
        switch (type) {
            case "client":
                return new ClientDataBaseDAO();

            default:
                throw new IllegalResourceException();
        }
    }
}
