package banking;

import java.sql.*;

public class ConnectionSQL {
    static Connection connection;
    static String url;
    ConnectionSQL(String fileName) {
        url = "jdbc:sqlite:" + fileName;
       // url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        connection = createNewConnection();
    }

    public String getURL(){
        return url;
    }
    public Connection getConnection(){
        return connection;
    }
    private  Connection createNewConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertData(Card card) {
        String insertSql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
            pstmt.setString(1, card.getAccountNumber());
            pstmt.setString(2, card.getPinCode());
            pstmt.setInt(3, 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS card(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "number TEXT," +
                            "pin TEXT," +
                            "balance INTEGER DEFAULT 0 )";

        try (PreparedStatement pstmt = connection.prepareStatement(createTable)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateData(Card card, int income) {
        String updateSql = "UPDATE card SET balance = ? WHERE number = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateSql)) {
            pstmt.setInt(1, (card.getBalance() + income));
            pstmt.setString(2, card.getAccountNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Card selectData(Card card) {
        String selectSql = "SELECT balance FROM card WHERE number = ? AND pin = ?";
        card.setBalance(-1);
        try (PreparedStatement pstmt = connection.prepareStatement(selectSql)) {
            pstmt.setString(1, card.getAccountNumber());
            pstmt.setString(2, card.getPinCode());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                int balance = rs.getInt(1);
                card.setAccountNumber(card.getAccountNumber());
                card.setPinCode(card.getPinCode());
                card.setBalance(balance);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return card;
    }

    public Card selectTransferData(Card card) {
        String selectSql = "SELECT balance FROM card WHERE number = ?";
        card.setBalance(-1);
        try (PreparedStatement pstmt = connection.prepareStatement(selectSql)) {
            pstmt.setString(1, card.getAccountNumber());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                int balance = rs.getInt(1);
                card.setAccountNumber(card.getAccountNumber());
                card.setPinCode(card.getPinCode());
                card.setBalance(balance);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return card;
    }


    public void deleteData (Card card) {
        String deleteSql = "DELETE FROM card WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, card.getAccountNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
