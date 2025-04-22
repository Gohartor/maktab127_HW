package HW17.repository;

import HW17.entity.User;
import HW17.repository.base.CrudRepositoryImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImp extends CrudRepositoryImp<User, Integer> implements UserRepository {

    public UserRepositoryImp(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected List<String> getInsertColumns() {
        return List.of("username", "password");
    }

    @Override
    protected void fillInsertValues(PreparedStatement statement, User user) {
        try {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setIdInNewEntity(ResultSet resultSet, User entity) {
        try {
            entity.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<String> getUpdateColumns() {
        return List.of();
    }

    @Override
    protected void fillUpdateValuesForUpdate(PreparedStatement statement, User user) {
        throw new UnsupportedOperationException("change properties is invalid");
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
            //tip: custom exception -or- throw e;
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "select * from users where username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToEntity(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        String sql = "select * from users where username = ? and password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToEntity(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}