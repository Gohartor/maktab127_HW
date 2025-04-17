package HW17.repository;

import HW17.config.ApplicationContext;
import HW17.entity.Transaction;
import HW17.entity.TransactionStatus;
import HW17.entity.TransactionType;
import HW17.repository.base.CrudRepositoryImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImp
        extends CrudRepositoryImp<Transaction, Integer>
        implements TransactionRepository {

    private static ApplicationContext applicationContext;

    public TransactionRepositoryImp(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "transactions";
    }

    @Override
    protected List<String> getInsertColumns() {
        return List.of("source_card_number", "destination_card_number", "amount", "transaction_fee",
                "transaction_timestamp", "transaction_type", "status");
    }

    @Override
    protected void fillInsertValues(PreparedStatement statement, Transaction transaction) {
        try {
            statement.setString(1, transaction.getSourceCardNumber());
            statement.setString(2, transaction.getDestinationCardNumber());
            statement.setDouble(3, transaction.getAmount());
            statement.setDouble(4, transaction.getTransactionFee());
            statement.setObject(5, transaction.getTransactionTimestamp());
            statement.setString(6, transaction.getTransactionType().toString());
            statement.setString(7, transaction.getStatus().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void setIdInNewEntity(ResultSet resultSet, Transaction entity) {
        try {
            entity.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<String> getUpdateColumns() {
        return List.of("source_card_number", "destination_card_number", "amount", "transaction_fee",
                "transaction_timestamp", "transaction_type", "status");
    }

    @Override
    protected void fillUpdateValuesForUpdate(PreparedStatement statement, Transaction transaction) {
        try {
            statement.setString(1, transaction.getSourceCardNumber());
            statement.setString(2, transaction.getDestinationCardNumber());
            statement.setDouble(3, transaction.getAmount());
            statement.setDouble(4, transaction.getTransactionFee());
            statement.setObject(5, transaction.getTransactionTimestamp());
            statement.setString(6, transaction.getTransactionType().toString());
            statement.setString(7, transaction.getStatus().toString());
            statement.setInt(8, transaction.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Transaction mapResultSetToEntity(ResultSet resultSet) {
        try {
            Transaction transaction = new Transaction();
            transaction.setId(resultSet.getInt("id"));
            transaction.setSourceCardNumber(resultSet.getString("source_card_number"));
            transaction.setDestinationCardNumber(resultSet.getString("destination_card_number"));
            transaction.setAmount(resultSet.getDouble("amount"));
            transaction.setTransactionFee(resultSet.getDouble("transaction_fee"));
            transaction.setTransactionTimestamp(resultSet.getObject("transaction_timestamp", LocalDateTime.class));
            transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
            transaction.setStatus(TransactionStatus.valueOf(resultSet.getString("status")));
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> findBySourceCardNumber(String sourceCardNumber) {
        String sql = "select * from transactions where source_card_number = ?";
        List<Transaction> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sourceCardNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> findByDestinationCardNumber(String destinationCardNumber) {
        String sql = "select * from transactions where destination_card_number = ?";
        List<Transaction> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, destinationCardNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Transaction> findByTransactionType(TransactionType transactionType) {
        String sql = "select * from transactions where transaction_type = ?";
        List<Transaction> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transactionType.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> findByStatus(TransactionStatus status) {
        String sql = "select * from transactions where status = ?";
        List<Transaction> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> findByTransactionTimestamp(LocalDateTime transactionTimestamp) {
        String sql = "select * from transactions where transaction_timestamp = ?";
        List<Transaction> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, transactionTimestamp);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(mapResultSetToEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}