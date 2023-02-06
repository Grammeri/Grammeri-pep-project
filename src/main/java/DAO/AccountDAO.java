package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account createAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account (username, password) values (?,?);";
            // PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1); //0 or 1???
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsernameAndPassword(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ? and password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
           Account account =  new Account (rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                    return account;
                };

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "select * from account;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                accounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
}

