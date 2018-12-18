package by.bsuir.bank.dao.impl;

import by.bsuir.bank.bundle.QueryBundle;
import by.bsuir.bank.bundle.QueryBundleFactory;
import by.bsuir.bank.dao.DataBaseDAO;
import by.bsuir.bank.entity.*;
import by.bsuir.bank.exception.ConnectionPoolException;
import by.bsuir.bank.exception.IllegalResourceException;
import by.bsuir.bank.service.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Specific impl of file com.epam.au.dao for gift com.epam.au.entity.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class ClientDataBaseDAO implements DataBaseDAO {
    private QueryBundle queryBundle;
    private ConnectionPool connectionPool;

    /**
     * Default constructor.
     */
    public ClientDataBaseDAO() {
        connectionPool = ConnectionPool.getInstance();
        try {
            QueryBundleFactory queryBundleFactory = new QueryBundleFactory();
            queryBundle = queryBundleFactory.create("client");
        } catch (IllegalResourceException e) {
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client find(long id) throws IllegalResourceException {
        Client client = new Client();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(queryBundle.getQuery("select.one"));
            statement.setLong(1, id);
            rs = statement.executeQuery();

            while (rs.next()) {
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setSurname(rs.getString("surname"));
                client.setPatronymic(rs.getString("patronymic"));
                client.setEmail(rs.getString("email"));
                client.setBirthday(rs.getTimestamp("birthday"));
                client.setPassportSeries(rs.getString("passport_series"));
                client.setPassportNumber(rs.getString("passport_number"));
                client.setPassportGivePlace(rs.getString("passport_given_place"));
                client.setPassportGivenDate(rs.getTimestamp("passport_given_date"));
                client.setIdentifyNumber(rs.getString("identify_number"));
                client.setBirthplace(rs.getString("birthplace"));
                client.setLivingPlace(City.valueOf(rs.getString("living_place").toUpperCase()));
                client.setLivingAddress(rs.getString("living_address"));
                client.setHomePhone(rs.getString("home_phone"));
                client.setMobilePhone(rs.getString("mobile_phone"));
                client.setWorkPlace(rs.getString("work_place"));
                client.setPost(rs.getString("work_post"));
                client.setMaritalStatuses(MaritalStatus.valueOf(rs.getString("marital_status").toUpperCase()));
                client.setNationality(Nationality.valueOf(rs.getString("nationality").toUpperCase()));
                client.setDisability(Disability.valueOf(rs.getString("disability").toUpperCase()));
                client.setRetiree(rs.getBoolean("retiree"));
                client.setLiableForMilitaryService(rs.getBoolean("military"));
                client.setMonthlyEarnings(rs.getBigDecimal("monthly_earnings"));
            }
        } catch (ConnectionPoolException e) {
        } catch (SQLException e) {
        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List findAll() throws IllegalResourceException {
        List<Client> clients = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("select.all"));
            rs = stmt.executeQuery();

            while (rs.next()) {
                Client client = new Client();

                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setSurname(rs.getString("surname"));
                client.setPatronymic(rs.getString("patronymic"));
                client.setEmail(rs.getString("email"));
                client.setBirthday(rs.getTimestamp("birthday"));
                client.setPassportSeries(rs.getString("passport_series"));
                client.setPassportNumber(rs.getString("passport_number"));
                client.setPassportGivePlace(rs.getString("passport_given_place"));
                client.setPassportGivenDate(rs.getTimestamp("passport_given_date"));
                client.setIdentifyNumber(rs.getString("identify_number"));
                client.setBirthplace(rs.getString("birthplace"));
                client.setLivingPlace(City.valueOf(rs.getString("living_place").toUpperCase()));
                client.setLivingAddress(rs.getString("living_address"));
                client.setHomePhone(rs.getString("home_phone"));
                client.setMobilePhone(rs.getString("mobile_phone"));
                client.setWorkPlace(rs.getString("work_place"));
                client.setPost(rs.getString("work_post"));
                client.setMaritalStatuses(MaritalStatus.valueOf(rs.getString("marital_status").toUpperCase()));
                client.setNationality(Nationality.valueOf(rs.getString("nationality").toUpperCase()));
                client.setDisability(Disability.valueOf(rs.getString("disability").toUpperCase()));
                client.setRetiree(rs.getBoolean("retiree"));
                client.setLiableForMilitaryService(rs.getBoolean("military"));
                client.setMonthlyEarnings(rs.getBigDecimal("monthly_earnings"));

                clients.add(client);
            }
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(conn, stmt, rs);
        }

        return clients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Object entity) {
        Client client = (Client) entity;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("insert.one"));

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getSurname());
            stmt.setString(3, client.getPatronymic());
            stmt.setString(4, client.getEmail());
            stmt.setTimestamp(5, client.getBirthday());
            stmt.setString(6, client.getPassportSeries());
            stmt.setString(7, client.getPassportNumber());
            stmt.setString(8, client.getPassportGivePlace());
            stmt.setTimestamp(9, client.getPassportGivenDate());
            stmt.setString(10, client.getIdentifyNumber());
            stmt.setString(11, client.getBirthplace());
            stmt.setString(12, client.getLivingPlace().toString().toLowerCase());
            stmt.setString(13, client.getLivingAddress());
            stmt.setString(14, client.getHomePhone());
            stmt.setString(15, client.getMobilePhone());
            stmt.setString(16, client.getWorkPlace());
            stmt.setString(17, client.getPost());
            stmt.setString(18, client.getMaritalStatuses().toString().toLowerCase());
            stmt.setString(19, client.getNationality().toString().toLowerCase());
            stmt.setString(20, client.getDisability().toString().toLowerCase());
            stmt.setBoolean(21, client.isRetiree());
            stmt.setBoolean(22, client.isLiableForMilitaryService());
            stmt.setBigDecimal(23, client.getMonthlyEarnings());

            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Object entity) throws IllegalResourceException {
        delete(((Client) entity).getId());
    }

    /**
     *
     */
    public void delete(long id) throws IllegalResourceException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("delete.one.by_id"));
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Object entity) throws IllegalResourceException {
        Client client = (Client) entity;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionPool.takeConnection();
            stmt = conn.prepareStatement(queryBundle.getQuery("update.one"));

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getSurname());
            stmt.setString(3, client.getPatronymic());
            stmt.setString(4, client.getEmail());
            stmt.setTimestamp(5, client.getBirthday());
            stmt.setString(6, client.getPassportSeries());
            stmt.setString(7, client.getPassportNumber());
            stmt.setString(8, client.getPassportGivePlace());
            stmt.setTimestamp(9, client.getPassportGivenDate());
            stmt.setString(10, client.getIdentifyNumber());
            stmt.setString(11, client.getBirthplace());
            stmt.setString(12, client.getLivingPlace().toString().toLowerCase());
            stmt.setString(13, client.getLivingAddress());
            stmt.setString(14, client.getHomePhone());
            stmt.setString(15, client.getMobilePhone());
            stmt.setString(16, client.getWorkPlace());
            stmt.setString(17, client.getPost());
            stmt.setString(18, client.getMaritalStatuses().toString().toLowerCase());
            stmt.setString(19, client.getNationality().toString().toLowerCase());
            stmt.setString(20, client.getDisability().toString().toLowerCase());
            stmt.setBoolean(21, client.isRetiree());
            stmt.setBoolean(22, client.isLiableForMilitaryService());
            stmt.setBigDecimal(23, client.getMonthlyEarnings());
            stmt.setLong(24, client.getId());

            stmt.executeUpdate();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(conn, stmt);
        }
    }

    public Client findByIdentifyNumber(String identifyNumber) {
        Client client = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(queryBundle.getQuery("select.one.by.identify.number"));
            statement.setString(1, identifyNumber);
            rs = statement.executeQuery();

            while (rs.next()) {
                client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setSurname(rs.getString("surname"));
                client.setPatronymic(rs.getString("patronymic"));
                client.setEmail(rs.getString("email"));
                client.setBirthday(rs.getTimestamp("birthday"));
                client.setPassportSeries(rs.getString("passport_series"));
                client.setPassportNumber(rs.getString("passport_number"));
                client.setPassportGivePlace(rs.getString("passport_given_place"));
                client.setPassportGivenDate(rs.getTimestamp("passport_given_date"));
                client.setIdentifyNumber(rs.getString("identify_number"));
                client.setBirthplace(rs.getString("birthplace"));
                client.setLivingPlace(City.valueOf(rs.getString("living_place").toUpperCase()));
                client.setLivingAddress(rs.getString("living_address"));
                client.setHomePhone(rs.getString("home_phone"));
                client.setMobilePhone(rs.getString("mobile_phone"));
                client.setWorkPlace(rs.getString("work_place"));
                client.setPost(rs.getString("work_post"));
                client.setMaritalStatuses(MaritalStatus.valueOf(rs.getString("marital_status").toUpperCase()));
                client.setNationality(Nationality.valueOf(rs.getString("nationality").toUpperCase()));
                client.setDisability(Disability.valueOf(rs.getString("disability").toUpperCase()));
                client.setRetiree(rs.getBoolean("retiree"));
                client.setLiableForMilitaryService(rs.getBoolean("military"));
                client.setMonthlyEarnings(rs.getBigDecimal("monthly_earnings"));
            }
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection, statement, rs);
        }

        return client;
    }
}
