package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executeRequest(String request){
        executeRequest(request, PreparedStatement::execute);
    }

    public <T> T executeRequest(String request, RequestExecutor<T> requestExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(request)) {
            return requestExecutor.executeRequest(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("");
            } else {
                throw new StorageException(e);
            }
        }
    }

    @FunctionalInterface
    public interface RequestExecutor<T> {
        T executeRequest(PreparedStatement st) throws SQLException;
    }
}