package by.bsuir.bank.dao;

import by.bsuir.bank.exception.IllegalResourceException;

/**
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public interface Factory<T> {
    /**
     * @param type
     * @return T
     * @throws IllegalResourceException if implementations of this method throw child
     *                      exceptions of {@link IllegalResourceException}
     */
    T create(String type) throws IllegalResourceException;
}
