package by.bsuir.bank.dao;

import by.bsuir.bank.exception.IllegalResourceException;

import java.util.List;

/**
 * Class provides access to entities through database.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public interface DataBaseDAO<T> {
    /**
     * @param id id of requested com.epam.au.entity
     * @return T found com.epam.au.entity
     * @throws IllegalResourceException if com.epam.au.entity did not found
     */
    T find(long id) throws IllegalResourceException;

    /**
     * @return List the list of found entities
     */
    List<T> findAll() throws IllegalResourceException;

    /**
     * @param entity object of com.epam.au.entity, that need to save in database
     */
    void create(T entity);

    /**
     * @param entity object of com.epam.au.entity, that need to delete from database
     * @throws IllegalResourceException if com.epam.au.entity did not found
     */
    void delete(T entity) throws IllegalResourceException;

    /**
     * @param entity object of com.epam.au.entity, that need to update in database. New
     *               object will be inserted in database instead of old
     * @throws IllegalResourceException if com.epam.au.entity did not found.
     */
    void update(T entity) throws IllegalResourceException;
}
