package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account createAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account (account_id, username, password) values (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account.account_id);
            preparedStatement.setString(2, account.username);
            preparedStatement.setString(1, account.password);

            preparedStatement.executeUpdate();
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

     public Account getAccountById(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
