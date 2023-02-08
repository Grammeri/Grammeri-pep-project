package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // Task 3 New message creation
    public Message createMessage(Message message) {

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?);";
            // PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getInt(1);

                return new Message(generated_message_id, message.posted_by, message.getMessage_text(),
                        message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Task 4 Getting all messages
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Task 5 Getting a message by its ID
    public Message getMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Task 6 Deleting a message by its ID
    public Message deleteMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        Message messageToDelete = null;

        try {
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messageToDelete = message;
            }
            if (messageToDelete != null) {
                try {
                    String deleteString = "delete from message where message_id = ?";
                    PreparedStatement preparedDeleteStatement = connection.prepareStatement(deleteString);

                    preparedDeleteStatement.setInt(1, message_id);

                    preparedDeleteStatement.executeUpdate();
                    return messageToDelete;
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            }
            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Task 7 Updating a message by its ID
    public void updateMessage(int message_id, Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "update message set message_text=? where message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.message_text);
            preparedStatement.setInt(2, message_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Task 8 Getting messages written by a particular user
    public List<Message> getAllMessagesByAccountId(int account_id) {
        Connection connection = ConnectionUtil.getConnection();

        List<Message> messages = new ArrayList<>();

        try {
            String sql = "select * from message where posted_by=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;

    }
}