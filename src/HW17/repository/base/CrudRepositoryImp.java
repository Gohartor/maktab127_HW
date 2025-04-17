package HW17.repository.base;

import HW17.entity.BaseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

public abstract class CrudRepositoryImp<T extends BaseEntity, ID> implements CrudRepository<T, ID> {

    protected final Connection connection;

    public CrudRepositoryImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T create(T t) {
        String insertQuery = prepareInsertQuery();
        try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            fillInsertValues(statement, t);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                setIdInNewEntity(resultSet, t);
            }
            return t;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void setIdInNewEntity(ResultSet resultSet, T entity);

    protected abstract void fillInsertValues(PreparedStatement statement, T t);

    protected String prepareInsertQuery() {
        String insertQueryTemplate = "insert into %s (%s) values (%s)";
        List<String> insertColumns = getInsertColumns();
        return String.format(
                insertQueryTemplate,
                getTableName(),
                String.join(
                        ", ",
                        insertColumns
                )
                ,getQuestionMarksForInsert()
        );
    }

    private String getQuestionMarksForInsert() {
        List<String> insertColumns = getInsertColumns();
        if (insertColumns == null || insertColumns.isEmpty()) {
            throw new RuntimeException("wrong implementation");
        }
        List<String> questionMarks = new ArrayList<>(Collections.nCopies(insertColumns.size(), "?"));
        return String.join(",", questionMarks);
    }

    protected abstract List<String> getInsertColumns();

    @Override
    public T update(T t) {
        String updateQuery = prepareUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            fillUpdateValuesForUpdate(statement, t);
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String prepareUpdateQuery() {
        String updateQueryTemplate = "update %s set %s where id = ?";
        List<String> updateColumns = getUpdateColumns();
        StringBuilder setClause = new StringBuilder();
        for (int i = 0; i < updateColumns.size(); i++) {
            setClause.append(updateColumns.get(i)).append(" = ?");
            if (i < updateColumns.size() - 1) {
                setClause.append(", ");
            }
        }
        return String.format(
                updateQueryTemplate,
                getTableName(),
                setClause.toString()
        );
    }

    protected abstract List<String> getUpdateColumns();

    protected abstract void fillUpdateValuesForUpdate(PreparedStatement statement, T t);

    @Override
    public List<T> findAll() {
        String findAllQuery = prepareFindAllQuery();
        List<T> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(findAllQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String prepareFindAllQuery() {
        return "select * from " + getTableName();
    }

    @Override
    public Optional<T> findById(ID id) {
        try (PreparedStatement statement = connection.prepareStatement(getFindByIdQuery())) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(
                        mapResultSetToEntity(resultSet)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    protected String getFindByIdQuery() {
        return "select * from " + getTableName() + " where id = ?";
    }

    protected abstract String getTableName();

    protected abstract T mapResultSetToEntity(ResultSet resultSet);

    @Override
    public int deleteById(ID id) {
        String deleteByIdQuery = prepareDeleteByIdQuery();
        try (PreparedStatement statement = connection.prepareStatement(deleteByIdQuery)) {
            statement.setObject(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String prepareDeleteByIdQuery() {
        return "delete from " + getTableName() + " where id = ?";
    }

    @Override
    public void deleteAll() {
        String deleteAllQuery = prepareDeleteAllQuery();
        try (PreparedStatement statement = connection.prepareStatement(deleteAllQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String prepareDeleteAllQuery() {
        return "delete from " + getTableName();
    }

    @Override
    public boolean existsById(ID id) {
        String existsByIdQuery = prepareExistsByIdQuery();
        try (PreparedStatement statement = connection.prepareStatement(existsByIdQuery)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String prepareExistsByIdQuery() {
        return "select exists(select 1 from " + getTableName() + " where id = ?)";
    }

    @Override
    public long countAll() {
        String countAllQuery = prepareCountAllQuery();
        try (PreparedStatement statement = connection.prepareStatement(countAllQuery)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String prepareCountAllQuery() {
        return "select count(*) from " + getTableName();
    }
}