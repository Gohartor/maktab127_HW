package HW17.repository;

import HW17.entity.Card;
import HW17.entity.User;
import HW17.repository.base.CrudRepositoryImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepositoryImp extends CrudRepositoryImp<Card, Integer> implements CardRepository {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMM");

    public CardRepositoryImp(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "cards";
    }

    @Override
    protected List<String> getInsertColumns() {
        return List.of("card_number", "bank_name", "expire_date", "cvv2", "balance", "user_id");
    }

    @Override
    protected void fillInsertValues(PreparedStatement statement, Card card) {
        try {
            statement.setString(1, card.getCardNumber());
            statement.setString(2, card.getBankName());
            statement.setString(3, card.getExpireDate().format(FORMATTER));
            statement.setString(4, card.getCvv2());
            statement.setDouble(5, card.getBalance());
            statement.setInt(6, card.getUser().getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void setIdInNewEntity(ResultSet resultSet, Card entity) {
        try {
            entity.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<String> getUpdateColumns() {
        return List.of("card_number", "bank_name", "expire_date", "cvv2", "balance", "user_id");
    }

    @Override
    protected void fillUpdateValuesForUpdate(PreparedStatement statement, Card card) {
        try {
            statement.setString(1, card.getCardNumber());
            statement.setString(2, card.getBankName());
            statement.setString(3, card.getExpireDate().format(FORMATTER));
            statement.setString(4, card.getCvv2());
            statement.setDouble(5, card.getBalance());
            statement.setInt(6, card.getUser().getId());
            statement.setInt(7, card.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Card mapResultSetToEntity(ResultSet resultSet) {
        try {
            Card card = new Card();
            card.setId(resultSet.getInt("id"));
            card.setCardNumber(resultSet.getString("card_number"));
            card.setBankName(resultSet.getString("bank_name"));
            String expireDateString = resultSet.getString("expire_date");
            card.setExpireDate(YearMonth.parse(expireDateString, FORMATTER));
            card.setCvv2(resultSet.getString("cvv2"));
            card.setBalance(resultSet.getDouble("balance"));
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            card.setUser(user);
            return card;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Card> findByCardNumber(String cardNumber) {
        String sql = "SELECT * FROM cards WHERE card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cardNumber);
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
    public List<Card> findByBankName(String bankName) {
        String sql = "SELECT * FROM cards WHERE bank_name = ?";
        List<Card> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, bankName);
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
    public List<Card> findByUserId(Integer userId) {
        String sql = "SELECT * FROM cards WHERE user_id = ?";
        List<Card> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
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