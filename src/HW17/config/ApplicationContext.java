package HW17.config;

import HW17.repository.*;

import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;

public class ApplicationContext {
    private static ApplicationContext applicationContext;

    private ApplicationContext() {
        Connection c = getConnection();
        runSqlScript(c);
    }

    public static ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }


    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        ApplicationProperties.JDBC_URL,
                        ApplicationProperties.JDBC_USER,
                        ApplicationProperties.JDBC_PASSWORD
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }


    public void runSqlScript(Connection connection) {
        try {
            if (!isTableExists(connection, "users") && !isTableExists(connection, "cards") && !isTableExists(connection, "transactions")) {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("HW17_CreateTable.SQL");
                if (inputStream != null) {
                    Scanner scanner = new Scanner(inputStream).useDelimiter(";");
                    Statement statement = connection.createStatement();
                    while (scanner.hasNext()) {
                        String sql = scanner.next().trim();
                        if (!sql.isEmpty()) {
                            statement.execute(sql);
                        }
                    }
                    statement.close();
                }
            } else {
                System.out.println("Tables already exist, skipping SQL execution.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize schema", e);
        }
    }


    private boolean isTableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet tables = metaData.getTables(null, null, tableName, null)) {
            return tables.next();
        }
    }



    private UserRepository ur;

    public UserRepository getUserRepository() {
        if (ur == null) {
            ur = new UserRepositoryImp(getConnection());
        }
        return ur;
    }

    private CardRepository cr;

    public CardRepository getCardRepository() {
        if (cr == null) {
            cr = new CardRepositoryImp(getConnection());
        }
        return cr;
    }


    private TransactionRepository tr;

    public TransactionRepository getTransactionRepository() {
        if (tr == null) {
            tr = new TransactionRepositoryImp(getConnection());
        }
        return tr;
    }

}
